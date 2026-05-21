package com.fitcoach.client.controller;

import java.util.List;
import com.fitcoach.client.model.member.Attendance;
import com.fitcoach.client.model.member.Member;
import com.fitcoach.client.model.order.Order;
import com.fitcoach.client.model.point.Point;
import com.fitcoach.client.model.point.PointHistory;
import com.fitcoach.client.model.product.AdditionalProduct;
import com.fitcoach.client.model.product.Membership;
import com.fitcoach.client.model.product.PT;

public class MemberController {
  private AuthController auth;
  private PurchaseController purchase;
  private ActivityController activity;
  private PTController pt;

  public MemberController(AuthController auth, PurchaseController purchase,
      ActivityController activity, PTController pt) {
    this.auth = auth;
    this.purchase = purchase;
    this.activity = activity;
    this.pt = pt;
  }

  public boolean init() {
    return auth != null && purchase != null && activity != null && pt != null;
  }

  // ─── UC14: 내정보 조회 ───

  public Membership getActiveMembership(String memberId) {
    return purchase.findActiveMembership(memberId);
  }

  public int getPointBalance(String memberId) {
    return activity.getPointBalance(memberId);
  }

  public Point getPoint(String memberId) {
    return activity.findPoint(memberId);
  }

  // ─── UC15: 내정보 수정 ───

  public boolean isDuplicatePhone(String phone, String excludeMemberId) {
    for (Member m : auth.getMembers()) {
      if (!m.getMemberId().equals(excludeMemberId) && m.getPhoneNumber().equals(phone)) {
        return true;
      }
    }
    return false;
  }

  public void updateMemberInfo(Member member, String nickname, String phone,
      String birthDate, String physicalInfo, String address,
      String profilePicture, String password) {
    if (!nickname.isEmpty())       member.setNickname(nickname);
    if (!phone.isEmpty())          member.setPhoneNumber(phone);
    if (!birthDate.isEmpty())      member.setBirthDate(birthDate);
    if (!physicalInfo.isEmpty())   member.setPhysicalInfo(physicalInfo);
    if (!address.isEmpty())        member.setAddress(address);
    if (!profilePicture.isEmpty()) member.setProfilePicture(profilePicture);
    if (!password.isEmpty())       member.setPassword(password);
  }

  // ─── UC16: 회원권 관리 ───

  public List<Attendance> getAttendances(String memberId) {
    return activity.getAttendancesByMember(memberId);
  }

  public int getActivePtRemainingCount(String memberId) {
    return purchase.getMemberPTs().stream()
        .filter(p -> p.getMemberId().equals(memberId) && "ACTIVE".equals(p.getStatus()))
        .mapToInt(PT::getRemainingCount).sum();
  }

  public long getAdditionalOrderCount(String memberId) {
    return purchase.getAdditionalOrdersByMember(memberId).size();
  }

  // ─── UC18: 부가상품 관리 ───

  public List<Order> getAdditionalOrders(String memberId) {
    return purchase.getAdditionalOrdersByMember(memberId);
  }

  public List<AdditionalProduct> getAdditionalProductCatalog() {
    return purchase.getAdditionalProductCatalog();
  }

  public AdditionalProduct findAdditionalProduct(String productId) {
    return purchase.findAdditionalProduct(productId);
  }

  public Order createAdditionalProductOrder(String memberId, AdditionalProduct product) {
    return purchase.createAdditionalProductOrder(memberId, product);
  }

  // ─── UC19: 포인트 내역 ───

  public List<PointHistory> getPointHistories(String memberId) {
    return activity.getPointHistoriesByMember(memberId);
  }

  // ─── 결제 위임 ───

  public boolean processPayment(String memberId, int amount, String productId,
      String productType, String paymentMethod, int usedPoints) {
    return purchase.processPayment(memberId, amount, productId, productType,
        paymentMethod, usedPoints);
  }

  public int getPaymentPointBalance(String memberId) {
    return purchase.getPointBalance(memberId);
  }

  // ─── 회원권 구매 위임 ───

  public List<Membership> getMembershipCatalog() {
    return purchase.getMembershipCatalog();
  }

  public int getMembershipDurationDays(String productName) {
    return purchase.getMembershipDurationDays(productName);
  }

  public Membership createMembership(String memberId, Membership product,
      java.time.LocalDate startDate, java.time.LocalDate endDate) {
    return purchase.createMembership(memberId, product, startDate, endDate);
  }
}
