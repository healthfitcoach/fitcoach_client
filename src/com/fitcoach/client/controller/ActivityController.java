package com.fitcoach.client.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import com.fitcoach.client.model.member.Attendance;
import com.fitcoach.client.model.member.ExerciseRecord;
import com.fitcoach.client.model.point.Point;
import com.fitcoach.client.model.point.PointHistory;
import com.fitcoach.client.model.point.PointPolicy;
import db.DBA;
import db.dao.ActivityDao;

public class ActivityController {
  private ActivityDao dao;

  public ActivityController(DBA dba) {
    this.dao = new ActivityDao(dba);
  }

  public boolean init() {
    return dao.init();
  }

  // ─── 출석 ───

  public boolean hasCheckedInToday(String memberId) {
    return dao.hasCheckedInToday(memberId);
  }

  public boolean checkIn(String memberId) {
    String id = "att-" + UUID.randomUUID().toString().substring(0, 8);
    Attendance attendance = new Attendance(id, memberId, LocalDateTime.now(), "QR");
    if (!attendance.init()) return false;
    return dao.saveAttendance(attendance);
  }

  public boolean hasActiveMembership(String memberId) {
    return !dao.findActiveMembershipsByMemberId(memberId).isEmpty();
  }

  public List<Attendance> getAttendancesByMember(String memberId) {
    return dao.findAttendancesByMemberId(memberId);
  }

  // ─── 운동 기록 ───

  public boolean addExerciseRecord(String memberId, LocalDate date, String exerciseType,
      int exerciseTime, int sets, int reps, String memo, String photo) {
    String id = "rec-" + UUID.randomUUID().toString().substring(0, 8);
    ExerciseRecord record = new ExerciseRecord(id, memberId, date,
        exerciseType, exerciseTime, sets, reps, memo, photo);
    if (!record.init()) return false;
    return dao.saveExerciseRecord(record);
  }

  // ─── 포인트 적립/차감 ───

  public boolean hasEarnedPointsToday(String memberId) {
    return dao.hasEarnedPointsToday(memberId);
  }

  public int earnPoints(String memberId, int exerciseTime) {
    PointPolicy policy = dao.findCurrentPolicy();
    if (policy == null) return -1;

    int earned = policy.getBasePoints();
    if (exerciseTime >= policy.getExerciseTimeStandard()) {
      earned += policy.getTimeBonusPoints();
    }

    Point memberPoint = findOrCreatePoint(memberId);
    if (memberPoint == null) return -1;

    memberPoint.setBalance(memberPoint.getBalance() + earned);
    dao.updatePoint(memberPoint);

    String histId = "ph-" + UUID.randomUUID().toString().substring(0, 8);
    dao.savePointHistory(new PointHistory(histId, memberId, "운동적립", earned,
        "운동 기록 포인트 적립", LocalDate.now(), memberPoint.getBalance()));

    int consecutive = countConsecutiveAttendance(memberId, policy);
    if (consecutive > 0 && consecutive % policy.getConsecutiveAttendanceDays() == 0) {
      int bonus = policy.getConsecutiveAttendanceBonus();
      memberPoint.setBalance(memberPoint.getBalance() + bonus);
      dao.updatePoint(memberPoint);
      String bonusHistId = "ph-" + UUID.randomUUID().toString().substring(0, 8);
      dao.savePointHistory(new PointHistory(bonusHistId, memberId, "연속출석보너스", bonus,
          consecutive + "일 연속 출석 달성 보너스", LocalDate.now(), memberPoint.getBalance()));
      earned += bonus;
    }
    return earned;
  }

  public boolean isTimeBonusApplicable(int exerciseTime) {
    PointPolicy policy = dao.findCurrentPolicy();
    return policy != null && exerciseTime >= policy.getExerciseTimeStandard();
  }

  public int getTimeBonusStandard() {
    PointPolicy policy = dao.findCurrentPolicy();
    return policy != null ? policy.getExerciseTimeStandard() : 30;
  }

  public int getConsecutiveCount(String memberId) {
    PointPolicy policy = dao.findCurrentPolicy();
    return countConsecutiveAttendance(memberId, policy);
  }

  public int getConsecutiveBonusDays() {
    PointPolicy policy = dao.findCurrentPolicy();
    return policy != null ? policy.getConsecutiveAttendanceDays() : 7;
  }

  public int getConsecutiveBonusPoints() {
    PointPolicy policy = dao.findCurrentPolicy();
    return policy != null ? policy.getConsecutiveAttendanceBonus() : 50;
  }

  public int getPointBalance(String memberId) {
    Point p = dao.findPointByMemberId(memberId);
    return p != null ? p.getBalance() : 0;
  }

  public Point findPoint(String memberId) {
    return dao.findPointByMemberId(memberId);
  }

  public void deductPoints(String memberId, int amount, String reason) {
    Point memberPoint = dao.findPointByMemberId(memberId);
    if (memberPoint == null || amount <= 0) return;
    int deduct = Math.min(amount, memberPoint.getBalance());
    memberPoint.setBalance(memberPoint.getBalance() - deduct);
    dao.updatePoint(memberPoint);
    String histId = "ph-" + UUID.randomUUID().toString().substring(0, 8);
    dao.savePointHistory(new PointHistory(histId, memberId, "사용", -deduct,
        reason, LocalDate.now(), memberPoint.getBalance()));
  }

  // ─── private 헬퍼 ───

  private Point findOrCreatePoint(String memberId) {
    Point existing = dao.findPointByMemberId(memberId);
    if (existing != null) return existing;
    Point newPoint = new Point("point-" + UUID.randomUUID().toString().substring(0, 8),
        memberId, 0, LocalDate.now().plusYears(1));
    if (!newPoint.init()) return null;
    dao.savePoint(newPoint);
    return newPoint;
  }

  private int countConsecutiveAttendance(String memberId, PointPolicy policy) {
    if (policy == null) return 0;
    List<Attendance> all = dao.findAttendancesByMemberId(memberId);
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
