package com.fitcoach.client.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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

public class PurchaseController {
  private List<Membership> membershipCatalog;
  private List<ExerciseProgram> programCatalog;
  private List<SportEquipment> sportEquipmentCatalog;
  private List<PT> ptCatalog;
  private List<AdditionalProduct> additionalProductCatalog;
  private List<Membership> memberMemberships;
  private List<Order> orders;
  private List<Payment> payments;

  // 크로스 레퍼런스 (setter 주입)
  private List<Point> points;
  private List<PointHistory> pointHistories;
  private List<Trainer> trainers;
  private List<PT> memberPTs;
  private List<PTSchedule> ptSchedules;

  public PurchaseController() {
    this.membershipCatalog = new ArrayList<>();
    this.programCatalog = new ArrayList<>();
    this.sportEquipmentCatalog = new ArrayList<>();
    this.ptCatalog = new ArrayList<>();
    this.additionalProductCatalog = new ArrayList<>();
    this.memberMemberships = new ArrayList<>();
    this.orders = new ArrayList<>();
    this.payments = new ArrayList<>();
    this.points = null;
    this.pointHistories = null;
    this.trainers = null;
    this.memberPTs = null;
    this.ptSchedules = null;
  }

  public void setPointLists(List<Point> points, List<PointHistory> pointHistories) {
    this.points = points;
    this.pointHistories = pointHistories;
  }

  public void setPTData(List<Trainer> trainers, List<PT> memberPTs, List<PTSchedule> ptSchedules) {
    this.trainers = trainers;
    this.memberPTs = memberPTs;
    this.ptSchedules = ptSchedules;
  }

  public boolean init() {
    if (points == null || pointHistories == null) return false;
    return trainers != null && memberPTs != null && ptSchedules != null;
  }

  // ─── 회원권 관련 ───

  public Membership findActiveMembership(String memberId) {
    for (Membership ms : memberMemberships) {
      if (ms.getMemberId().equals(memberId) && "ACTIVE".equals(ms.getStatus())) return ms;
    }
    return null;
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

  public Membership createMembership(String memberId, Membership product,
      LocalDate startDate, LocalDate endDate) {
    String newId = "ms-" + String.format("%03d", memberMemberships.size() + 1);
    Membership newMs = new Membership(product.getProductId(), product.getProductName(),
        product.getPrice(), product.getDescription(),
        newId, memberId, "ACTIVE", startDate, endDate);
    if (!newMs.init()) return null;
    memberMemberships.add(newMs);
    return newMs;
  }

  // ─── 프로그램 관련 ───

  public Trainer findTrainer(String trainerId) {
    for (Trainer t : trainers) {
      if (t.getTrainerId().equals(trainerId)) return t;
    }
    return null;
  }

  public Order createProgramOrder(String memberId, ExerciseProgram program, LocalDate startDate) {
    String orderId = "order-" + String.format("%03d", orders.size() + 1);
    Order order = new Order(orderId, memberId, program.getProductId(),
        1, program.getPrice(), "", "COMPLETED", LocalDateTime.now());
    if (!order.init()) return null;
    orders.add(order);
    program.setRemainingCapacity(program.getRemainingCapacity() - 1);
    return order;
  }

  // ─── 운동용품 관련 ───

  public List<SportEquipment> searchSportEquipment(String keyword) {
    List<SportEquipment> result = new ArrayList<>();
    for (SportEquipment se : sportEquipmentCatalog) {
      if (se.getProductName().contains(keyword) || se.getCategory().contains(keyword)) {
        result.add(se);
      }
    }
    return result;
  }

  public Order createEquipmentOrder(String memberId, SportEquipment product,
      int quantity, String shippingAddress) {
    int totalAmount = product.getPrice() * quantity;
    String orderId = "order-" + String.format("%03d", orders.size() + 1);
    Order order = new Order(orderId, memberId, product.getProductId(),
        quantity, totalAmount, shippingAddress, "COMPLETED", LocalDateTime.now());
    if (!order.init()) return null;
    orders.add(order);
    product.setStock(product.getStock() - quantity);
    return order;
  }

  // ─── PT 관련 ───

  public PT createMemberPT(String memberId, PT product, String trainerId) {
    String newPtId = "pt-" + String.format("%03d", memberPTs.size() + 1);
    PT newPT = new PT(product.getProductId(), product.getProductName(),
        product.getPrice(), product.getDescription(),
        newPtId, memberId, trainerId,
        product.getTotalCount(), product.getRemainingCount(), "ACTIVE");
    if (!newPT.init()) return null;
    memberPTs.add(newPT);
    return newPT;
  }

  public boolean addFirstPTSchedule(String ptId, String memberId, String trainerId,
      LocalDate date, LocalTime time) {
    String scheduleId = "sched-" + String.format("%03d", ptSchedules.size() + 1);
    PTSchedule schedule = new PTSchedule(scheduleId, ptId, memberId, trainerId,
        date, time, "RESERVED");
    if (!schedule.init()) return false;
    ptSchedules.add(schedule);
    return true;
  }

  public boolean isSlotBooked(String trainerId, LocalDate date, LocalTime time) {
    for (PTSchedule s : ptSchedules) {
      if (s.getTrainerId().equals(trainerId)
          && s.getDate().equals(date)
          && s.getTime().equals(time)) {
        return true;
      }
    }
    return false;
  }

  // ─── 결제 관련 ───

  public int getPointBalance(String memberId) {
    for (Point p : points) {
      if (p.getMemberId().equals(memberId)) return p.getBalance();
    }
    return 0;
  }

  public boolean processPayment(String memberId, int amount, String productId,
      String productType, String paymentMethod, int usedPoints) {
    int finalAmount = amount - usedPoints;
    String paymentId = "pay-" + String.format("%03d", payments.size() + 1);
    Payment payment = new Payment(paymentId, memberId, productId, productType,
        paymentMethod, finalAmount, usedPoints, "COMPLETED", LocalDateTime.now());
    if (!payment.init()) return false;
    payments.add(payment);

    if (usedPoints > 0) {
      Point memberPoint = null;
      for (Point p : points) {
        if (p.getMemberId().equals(memberId)) { memberPoint = p; break; }
      }
      if (memberPoint != null) {
        memberPoint.setBalance(memberPoint.getBalance() - usedPoints);
        String histId = "ph-" + String.format("%03d", pointHistories.size() + 1);
        pointHistories.add(new PointHistory(histId, memberId, "사용", -usedPoints,
            productType + " 결제 포인트 사용", LocalDate.now(), memberPoint.getBalance()));
      }
    }
    return true;
  }

  // ─── 부가상품 관련 ───

  public List<Order> getAdditionalOrdersByMember(String memberId) {
    List<String> additionalIds = new ArrayList<>();
    for (AdditionalProduct ap : additionalProductCatalog) {
      additionalIds.add(ap.getProductId());
    }
    List<Order> result = new ArrayList<>();
    for (Order o : orders) {
      if (o.getMemberId().equals(memberId) && additionalIds.contains(o.getProductId())) {
        result.add(o);
      }
    }
    return result;
  }

  public AdditionalProduct findAdditionalProduct(String productId) {
    for (AdditionalProduct ap : additionalProductCatalog) {
      if (ap.getProductId().equals(productId)) return ap;
    }
    return null;
  }

  public Order createAdditionalProductOrder(String memberId, AdditionalProduct product) {
    String orderId = "order-" + String.format("%03d", orders.size() + 1);
    Order order = new Order(orderId, memberId, product.getProductId(),
        1, product.getPrice(), "", "COMPLETED", LocalDateTime.now());
    if (!order.init()) return null;
    orders.add(order);
    return order;
  }

  // ─── Getters ───

  public List<Membership> getMembershipCatalog() { return membershipCatalog; }
  public List<ExerciseProgram> getProgramCatalog() { return programCatalog; }
  public List<SportEquipment> getSportEquipmentCatalog() { return sportEquipmentCatalog; }
  public List<PT> getPtCatalog() { return ptCatalog; }
  public List<AdditionalProduct> getAdditionalProductCatalog() { return additionalProductCatalog; }
  public List<Membership> getMemberMemberships() { return memberMemberships; }
  public List<Order> getOrders() { return orders; }
  public List<Payment> getPayments() { return payments; }
  public List<PT> getMemberPTs() { return memberPTs; }
  public List<PTSchedule> getPtSchedules() { return ptSchedules; }
  public List<Trainer> getTrainers() { return trainers; }
}
