package com.fitcoach.client.domain.activity;

import com.fitcoach.client.domain.activity.repository.PointHistoryRepository;
import com.fitcoach.client.domain.activity.repository.PointPolicyRepository;
import com.fitcoach.client.domain.activity.repository.PointRepository;
import com.fitcoach.client.domain.member.repository.AttendanceRepository;
import com.fitcoach.client.domain.member.repository.ExerciseRecordRepository;
import com.fitcoach.client.domain.purchase.repository.MembershipRepository;
import com.fitcoach.client.model.member.Attendance;
import com.fitcoach.client.model.member.ExerciseRecord;
import com.fitcoach.client.model.point.Point;
import com.fitcoach.client.model.point.PointHistory;
import com.fitcoach.client.model.point.PointPolicy;
import com.fitcoach.client.model.product.Membership.MembershipStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActivityService {

  private final AttendanceRepository attendanceRepository;
  private final ExerciseRecordRepository exerciseRecordRepository;
  private final PointRepository pointRepository;
  private final PointHistoryRepository pointHistoryRepository;
  private final PointPolicyRepository pointPolicyRepository;
  private final MembershipRepository membershipRepository;

  public ActivityService(AttendanceRepository attendanceRepository,
      ExerciseRecordRepository exerciseRecordRepository,
      PointRepository pointRepository,
      PointHistoryRepository pointHistoryRepository,
      PointPolicyRepository pointPolicyRepository,
      MembershipRepository membershipRepository) {
    this.attendanceRepository = attendanceRepository;
    this.exerciseRecordRepository = exerciseRecordRepository;
    this.pointRepository = pointRepository;
    this.pointHistoryRepository = pointHistoryRepository;
    this.pointPolicyRepository = pointPolicyRepository;
    this.membershipRepository = membershipRepository;
  }

  // ─── 출석 ───

  @Transactional(readOnly = true)
  public boolean hasCheckedInToday(Long memberId) {
    LocalDateTime start = LocalDate.now().atStartOfDay();
    LocalDateTime end = LocalDate.now().atTime(23, 59, 59);
    return attendanceRepository.existsByMemberIdAndAttendanceDateTimeBetween(memberId, start, end);
  }

  @Transactional
  public Attendance checkIn(Long memberId) {
    return attendanceRepository.save(new Attendance(memberId, LocalDateTime.now(), "QR"));
  }

  @Transactional(readOnly = true)
  public boolean hasActiveMembership(Long memberId) {
    return membershipRepository
        .findFirstByMemberIdAndStatusOrderByStartDateDesc(memberId, MembershipStatus.ACTIVE)
        .isPresent();
  }

  @Transactional(readOnly = true)
  public List<Attendance> getAttendancesByMember(Long memberId) {
    return attendanceRepository.findByMemberIdOrderByAttendanceDateTimeDesc(memberId);
  }

  // ─── 운동 기록 ───

  @Transactional
  public ExerciseRecord addExerciseRecord(Long memberId, LocalDate date, String exerciseType,
      int exerciseTime, int sets, int reps, String memo, String photo) {
    String id = "rec-" + UUID.randomUUID().toString().substring(0, 8);
    ExerciseRecord record = new ExerciseRecord(
        id, memberId.toString(), date, exerciseType, exerciseTime, sets, reps, memo, photo);
    return exerciseRecordRepository.save(record);
  }

  // ─── 포인트 ───

  @Transactional(readOnly = true)
  public boolean hasEarnedPointsToday(Long memberId) {
    return exerciseRecordRepository.existsByMemberIdAndExerciseDate(
        memberId.toString(), LocalDate.now());
  }

  @Transactional
  public int earnPoints(Long memberId, int exerciseTime) {
    String memberIdStr = memberId.toString();
    PointPolicy policy = pointPolicyRepository.findFirstByOrderByPolicyIdDesc().orElse(null);
    if (policy == null) return -1;

    int earned = policy.getBasePoints();
    if (exerciseTime >= policy.getExerciseTimeStandard()) {
      earned += policy.getTimeBonusPoints();
    }

    Point point = findOrCreatePoint(memberIdStr);
    point.setBalance(point.getBalance() + earned);
    pointRepository.save(point);

    savePointHistory(memberIdStr, "운동적립", earned, "운동 기록 포인트 적립", point.getBalance());

    int consecutive = countConsecutiveAttendance(memberId);
    if (consecutive > 0 && consecutive % policy.getConsecutiveAttendanceDays() == 0) {
      int bonus = policy.getConsecutiveAttendanceBonus();
      point.setBalance(point.getBalance() + bonus);
      pointRepository.save(point);
      savePointHistory(memberIdStr, "연속출석보너스", bonus,
          consecutive + "일 연속 출석 달성 보너스", point.getBalance());
      earned += bonus;
    }
    return earned;
  }

  @Transactional(readOnly = true)
  public int getPointBalance(Long memberId) {
    return pointRepository.findByMemberId(memberId.toString())
        .map(Point::getBalance)
        .orElse(0);
  }

  @Transactional(readOnly = true)
  public Point findPoint(Long memberId) {
    return pointRepository.findByMemberId(memberId.toString()).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<PointHistory> getPointHistories(Long memberId) {
    return pointHistoryRepository.findByMemberIdOrderByDateDesc(memberId.toString());
  }

  @Transactional
  public void deductPoints(Long memberId, int amount, String reason) {
    String memberIdStr = memberId.toString();
    Point point = pointRepository.findByMemberId(memberIdStr).orElse(null);
    if (point == null || amount <= 0) return;
    int deduct = Math.min(amount, point.getBalance());
    point.setBalance(point.getBalance() - deduct);
    pointRepository.save(point);
    savePointHistory(memberIdStr, "사용", -deduct, reason, point.getBalance());
  }

  // ─── private 헬퍼 ───

  private Point findOrCreatePoint(String memberIdStr) {
    return pointRepository.findByMemberId(memberIdStr).orElseGet(() -> {
      Point newPoint = new Point(
          "point-" + UUID.randomUUID().toString().substring(0, 8),
          memberIdStr, 0, LocalDate.now().plusYears(1));
      return pointRepository.save(newPoint);
    });
  }

  private void savePointHistory(String memberIdStr, String type, int amount,
      String reason, int balanceAfter) {
    String histId = "ph-" + UUID.randomUUID().toString().substring(0, 8);
    pointHistoryRepository.save(new PointHistory(
        histId, memberIdStr, type, amount, reason, LocalDate.now(), balanceAfter));
  }

  private int countConsecutiveAttendance(Long memberId) {
    List<Attendance> all = attendanceRepository.findByMemberIdOrderByAttendanceDateTimeDesc(memberId);
    LocalDate check = LocalDate.now();
    int count = 0;
    while (true) {
      final LocalDate d = check;
      boolean found = all.stream()
          .anyMatch(a -> a.getAttendanceDateTime().toLocalDate().equals(d));
      if (!found) break;
      count++;
      check = check.minusDays(1);
    }
    return count;
  }
}
