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
import com.fitcoach.client.model.product.MemberProduct;
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

  public MemberProduct findActiveMembership(String memberId) {
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

  public MemberProduct createMembership(String memberId, Membership product,
      LocalDate startDate, LocalDate endDate) {
    String newId = "mp-" + UUID.randomUUID().toString().substring(0, 8);
    MemberProduct mp = new MemberProduct(newId, memberId, product.getProductId(),
        "MEMBERSHIP", product.getProductName(), product.getDescription(), "ACTIVE",
        startDate, endDate, null, 0, 0, 0, LocalDateTime.now());
    if (!mp.init()) return null;
    if (!dao.saveMemberProduct(mp)) return null;
    return mp;
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

  public MemberProduct createMemberPT(String memberId, PT product, String trainerId) {
    String newId = "mp-" + UUID.randomUUID().toString().substring(0, 8);
    MemberProduct mp = new MemberProduct(newId, memberId, product.getProductId(),
        "PT", product.getProductName(), product.getDescription(), "ACTIVE",
        null, null, trainerId,
        product.getTotalCount(), product.getTotalCount(), 0, LocalDateTime.now());
    if (!mp.init()) return null;
    if (!dao.saveMemberProduct(mp)) return null;
    return mp;
  }

  public boolean addFirstPTSchedule(String memberProductId, String memberId, String trainerId,
      LocalDate date, LocalTime time) {
    String scheduleId = "sched-" + UUID.randomUUID().toString().substring(0, 8);
    PTSchedule schedule = new PTSchedule(scheduleId, memberProductId, memberId, trainerId,
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

    String newId = "mp-" + UUID.randomUUID().toString().substring(0, 8);
    MemberProduct mp = new MemberProduct(newId, memberId, product.getProductId(),
        "ADDITIONAL", product.getProductName(), product.getDescription(), "ACTIVE",
        null, null, null, 0, 0, product.getUsagePeriod(), LocalDateTime.now());
    dao.saveMemberProduct(mp);
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
