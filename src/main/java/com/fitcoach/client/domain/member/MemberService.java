package com.fitcoach.client.domain.member;

import com.fitcoach.client.domain.activity.ActivityService;
import com.fitcoach.client.domain.activity.repository.PointHistoryRepository;
import com.fitcoach.client.domain.activity.repository.PointRepository;
import com.fitcoach.client.domain.auth.repository.MemberRepository;
import com.fitcoach.client.domain.member.repository.AttendanceRepository;
import com.fitcoach.client.domain.purchase.repository.MembershipRepository;
import com.fitcoach.client.domain.purchase.repository.OrderRepository;
import com.fitcoach.client.domain.purchase.repository.PTSubscriptionRepository;
import com.fitcoach.client.global.exception.CustomException;
import com.fitcoach.client.global.exception.ErrorCode;
import com.fitcoach.client.model.member.Attendance;
import com.fitcoach.client.model.member.Member;
import com.fitcoach.client.model.order.Order;
import com.fitcoach.client.model.point.Point;
import com.fitcoach.client.model.point.PointHistory;
import com.fitcoach.client.model.product.Membership;
import com.fitcoach.client.model.product.Membership.MembershipStatus;
import com.fitcoach.client.model.product.PTSubscription.PTStatus;
import java.time.LocalDate;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final MembershipRepository membershipRepository;
  private final AttendanceRepository attendanceRepository;
  private final PTSubscriptionRepository ptSubscriptionRepository;
  private final OrderRepository orderRepository;
  private final PointRepository pointRepository;
  private final PointHistoryRepository pointHistoryRepository;
  private final ActivityService activityService;
  private final PasswordEncoder passwordEncoder;

  public MemberService(MemberRepository memberRepository,
      MembershipRepository membershipRepository,
      AttendanceRepository attendanceRepository,
      PTSubscriptionRepository ptSubscriptionRepository,
      OrderRepository orderRepository,
      PointRepository pointRepository,
      PointHistoryRepository pointHistoryRepository,
      ActivityService activityService,
      PasswordEncoder passwordEncoder) {
    this.memberRepository = memberRepository;
    this.membershipRepository = membershipRepository;
    this.attendanceRepository = attendanceRepository;
    this.ptSubscriptionRepository = ptSubscriptionRepository;
    this.orderRepository = orderRepository;
    this.pointRepository = pointRepository;
    this.pointHistoryRepository = pointHistoryRepository;
    this.activityService = activityService;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional(readOnly = true)
  public Member getMemberInfo(Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
  }

  @Transactional
  public void updateMemberInfo(Long memberId, String nickname, String phone,
      LocalDate birthDate, String bodyInfo, String address,
      String profileImage, String newPassword) {
    Member member = getMemberInfo(memberId);
    if (phone != null && !phone.isBlank()
        && memberRepository.existsByPhoneAndIdNot(phone, memberId)) {
      throw new CustomException(ErrorCode.DUPLICATE_PHONE);
    }
    if (nickname != null && !nickname.isBlank())       member.setNickname(nickname);
    if (phone != null && !phone.isBlank())             member.setPhone(phone);
    if (birthDate != null)                             member.setBirthDate(birthDate);
    if (bodyInfo != null && !bodyInfo.isBlank())       member.setBodyInfo(bodyInfo);
    if (address != null && !address.isBlank())         member.setAddress(address);
    if (profileImage != null && !profileImage.isBlank()) member.setProfileImage(profileImage);
    if (newPassword != null && !newPassword.isBlank())
      member.setPassword(passwordEncoder.encode(newPassword));
    memberRepository.save(member);
  }

  @Transactional(readOnly = true)
  public Membership getActiveMembership(Long memberId) {
    return membershipRepository
        .findFirstByMemberIdAndStatusOrderByStartDateDesc(memberId, MembershipStatus.ACTIVE)
        .orElse(null);
  }

  @Transactional(readOnly = true)
  public List<Membership> getMemberships(Long memberId) {
    return membershipRepository.findByMemberId(memberId);
  }

  @Transactional(readOnly = true)
  public List<Attendance> getAttendances(Long memberId) {
    return attendanceRepository.findByMemberIdOrderByAttendanceDateTimeDesc(memberId);
  }

  @Transactional(readOnly = true)
  public int getActivePtRemainingCount(Long memberId) {
    return ptSubscriptionRepository.sumRemainingCountByMemberIdAndStatus(memberId, PTStatus.ACTIVE);
  }

  @Transactional(readOnly = true)
  public List<Order> getOrders(Long memberId) {
    return orderRepository.findByMemberIdOrderByOrderDateTimeDesc(memberId.toString());
  }

  @Transactional(readOnly = true)
  public Point getPoint(Long memberId) {
    return pointRepository.findByMemberId(memberId.toString()).orElse(null);
  }

  @Transactional(readOnly = true)
  public int getPointBalance(Long memberId) {
    return pointRepository.findByMemberId(memberId.toString())
        .map(Point::getBalance).orElse(0);
  }

  @Transactional(readOnly = true)
  public List<PointHistory> getPointHistories(Long memberId) {
    return pointHistoryRepository.findByMemberIdOrderByDateDesc(memberId.toString());
  }
}
