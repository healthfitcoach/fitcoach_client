package com.fitcoach.client.controller;

import java.time.LocalDate;
import java.util.List;
import com.fitcoach.client.model.member.Attendance;
import com.fitcoach.client.model.member.Member;
import com.fitcoach.client.model.order.Order;
import com.fitcoach.client.model.point.Point;
import com.fitcoach.client.model.point.PointHistory;
import com.fitcoach.client.model.product.AdditionalProduct;
import com.fitcoach.client.model.product.MemberProduct;
import com.fitcoach.client.model.product.Membership;
import db.DBA;
import db.dao.MemberDao;

public class MemberController {
  private MemberDao dao;
  private PurchaseController purchase;
  private ActivityController activity;
  private PTController pt;

  public MemberController(DBA dba, PurchaseController purchase,
      ActivityController activity, PTController pt) {
    this.dao = new MemberDao(dba);
    this.purchase = purchase;
    this.activity = activity;
    this.pt = pt;
  }

  public boolean init() {
    return dao.init();
  }

  // ─── UC14: 내정보 조회 ───

  public MemberProduct getActiveMembership(String memberId) {
    return dao.findMembershipsByMemberId(memberId).stream()
        .filter(mp -> "ACTIVE".equals(mp.getStatus()))
        .findFirst().orElse(null);
  }

  public int getPointBalance(String memberId) {
    Point p = dao.findPointByMemberId(memberId);
    return p != null ? p.getBalance() : 0;
  }

  public Point getPoint(String memberId) {
    return dao.findPointByMemberId(memberId);
  }

  // ─── UC15: 내정보 수정 ───

  public boolean isDuplicatePhone(String phone, String excludeMemberId) {
    Member found = dao.findByPhone(phone);
    return found != null && !found.getMemberId().equals(excludeMemberId);
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
    dao.updateMember(member);
  }

  // ─── UC16: 회원권 관리 ───

  public List<Attendance> getAttendances(String memberId) {
    return activity.getAttendancesByMember(memberId);
  }

  public List<MemberProduct> getMembershipsByMemberId(String memberId) {
    return dao.findMembershipsByMemberId(memberId);
  }

  public int getActivePtRemainingCount(String memberId) {
    return pt.getActivePTsByMember(memberId).stream()
        .mapToInt(MemberProduct::getRemainingCount).sum();
  }

  // ─── UC18: 부가상품 관리 ───

  public List<Order> getOrders(String memberId) {
    return dao.findOrdersByMemberId(memberId);
  }

  public long getAdditionalOrderCount(String memberId) {
    return getOrders(memberId).size();
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
    return dao.findPointHistoryByMemberId(memberId);
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

  public MemberProduct createMembership(String memberId, Membership product,
      LocalDate startDate, LocalDate endDate) {
    return purchase.createMembership(memberId, product, startDate, endDate);
  }
}
