package com.fitcoach.client.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.fitcoach.client.model.order.Order;
import com.fitcoach.client.model.order.Payment;
import com.fitcoach.client.model.point.Point;
import com.fitcoach.client.model.point.PointHistory;
import com.fitcoach.client.model.product.AdditionalProduct;
import com.fitcoach.client.model.product.ExerciseProgram;
import com.fitcoach.client.model.product.Membership;
import com.fitcoach.client.model.product.PT;
import com.fitcoach.client.model.product.SportEquipment;
import com.fitcoach.client.model.schedule.PTSchedule;
import com.fitcoach.client.model.schedule.Trainer;
import db.DBA;
import db.dao.PurchaseDao;

public class PurchaseController {
  private PurchaseDao dao;

  public PurchaseController(DBA dba) {
    this.dao = new PurchaseDao(dba);
  }

  public boolean init() {
    return dao.init();
  }

  // ─── 회원권 ───

  public Membership findActiveMembership(String memberId) {
    return dao.findActiveMembershipByMemberId(memberId);
  }

  public int getMembershipDurationDays(String productName) {
    return switch (productName) {
      case "1개월권" -> 30;
      case "3개월권" -> 90;
      case "6개월권" -> 180;
      case "1년권"   -> 365;
      default        -> 30;
    };
  }

  public List<Membership> getMembershipCatalog() {
    return dao.findAllMemberships();
  }

  public Membership createMembership(String memberId, Membership product,
      LocalDate startDate, LocalDate endDate) {
    String newId = "ms-" + UUID.randomUUID().toString().substring(0, 8);
    Membership newMs = new Membership(product.getProductId(), product.getProductName(),
        product.getPrice(), product.getDescription(),
        newId, memberId, "ACTIVE", startDate, endDate);
    if (!newMs.init()) return null;
    if (!dao.saveMemberMembership(newMs)) return null;
    return newMs;
  }

  // ─── 프로그램 ───

  public List<ExerciseProgram> getProgramCatalog() {
    return dao.findAllPrograms();
  }

  public List<Trainer> getAllTrainersList() {
    return dao.findAllTrainers();
  }

  public Trainer findTrainer(String trainerId) {
    return dao.findTrainerById(trainerId);
  }

  public Order createProgramOrder(String memberId, ExerciseProgram program, LocalDate startDate) {
    String orderId = "order-" + UUID.randomUUID().toString().substring(0, 8);
    Order order = new Order(orderId, memberId, program.getProductId(),
        1, program.getPrice(), "", "COMPLETED", LocalDateTime.now());
    if (!order.init()) return null;
    if (!dao.saveOrder(order)) return null;
    return order;
  }

  // ─── 운동용품 ───

  public List<SportEquipment> getSportEquipmentCatalog() {
    return dao.findAllSportEquipments();
  }

  public List<SportEquipment> searchSportEquipment(String keyword) {
    List<SportEquipment> result = new ArrayList<>();
    for (SportEquipment se : dao.findAllSportEquipments()) {
      if (se.getProductName().contains(keyword) || se.getCategory().contains(keyword)) {
        result.add(se);
      }
    }
    return result;
  }

  public Order createEquipmentOrder(String memberId, SportEquipment product,
      int quantity, String shippingAddress) {
    int totalAmount = product.getPrice() * quantity;
    String orderId = "order-" + UUID.randomUUID().toString().substring(0, 8);
    Order order = new Order(orderId, memberId, product.getProductId(),
        quantity, totalAmount, shippingAddress, "COMPLETED", LocalDateTime.now());
    if (!order.init()) return null;
    if (!dao.saveOrder(order)) return null;
    return order;
  }

  // ─── PT ───

  public List<PT> getPtCatalog() {
    return dao.findAllPTs();
  }

  public PT createMemberPT(String memberId, PT product, String trainerId) {
    String newPtId = "pt-" + UUID.randomUUID().toString().substring(0, 8);
    PT newPT = new PT(product.getProductId(), product.getProductName(),
        product.getPrice(), product.getDescription(),
        newPtId, memberId, trainerId,
        product.getTotalCount(), product.getRemainingCount(), "ACTIVE");
    if (!newPT.init()) return null;
    if (!dao.saveMemberPT(newPT)) return null;
    return newPT;
  }

  public boolean addFirstPTSchedule(String ptId, String memberId, String trainerId,
      LocalDate date, LocalTime time) {
    String scheduleId = "sched-" + UUID.randomUUID().toString().substring(0, 8);
    PTSchedule schedule = new PTSchedule(scheduleId, ptId, memberId, trainerId,
        date, time, "RESERVED");
    if (!schedule.init()) return false;
    return dao.savePTSchedule(schedule);
  }

  public boolean isSlotBooked(String trainerId, LocalDate date, LocalTime time) {
    return dao.isSlotBooked(trainerId, LocalDateTime.of(date, time));
  }

  // ─── 부가상품 ───

  public List<AdditionalProduct> getAdditionalProductCatalog() {
    return dao.findAllAdditionalProducts();
  }

  public AdditionalProduct findAdditionalProduct(String productId) {
    for (AdditionalProduct ap : dao.findAllAdditionalProducts()) {
      if (ap.getProductId().equals(productId)) return ap;
    }
    return null;
  }

  public Order createAdditionalProductOrder(String memberId, AdditionalProduct product) {
    String orderId = "order-" + UUID.randomUUID().toString().substring(0, 8);
    Order order = new Order(orderId, memberId, product.getProductId(),
        1, product.getPrice(), "", "COMPLETED", LocalDateTime.now());
    if (!order.init()) return null;
    if (!dao.saveOrder(order)) return null;
    return order;
  }

  // ─── 결제 ───

  public int getPointBalance(String memberId) {
    Point p = dao.findPointByMemberId(memberId);
    return p != null ? p.getBalance() : 0;
  }

  public boolean processPayment(String memberId, int amount, String productId,
      String productType, String paymentMethod, int usedPoints) {
    int finalAmount = amount - usedPoints;
    String paymentId = "pay-" + UUID.randomUUID().toString().substring(0, 8);
    Payment payment = new Payment(paymentId, memberId, productId, productType,
        paymentMethod, finalAmount, usedPoints, "COMPLETED", LocalDateTime.now());
    if (!payment.init()) return false;
    if (!dao.savePayment(payment)) return false;

    if (usedPoints > 0) {
      Point memberPoint = dao.findPointByMemberId(memberId);
      if (memberPoint != null) {
        memberPoint.setBalance(memberPoint.getBalance() - usedPoints);
        dao.updatePoint(memberPoint);
        String histId = "ph-" + UUID.randomUUID().toString().substring(0, 8);
        dao.savePointHistory(new PointHistory(histId, memberId, "사용", -usedPoints,
            productType + " 결제 포인트 사용", LocalDate.now(), memberPoint.getBalance()));
      }
    }
    return true;
  }
}
