package com.fitcoach.client.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.member.Attendance;
import com.fitcoach.client.model.member.ExerciseRecord;
import com.fitcoach.client.model.point.Point;
import com.fitcoach.client.model.point.PointHistory;
import com.fitcoach.client.model.point.PointPolicy;
import com.fitcoach.client.model.product.Membership;

public class ActivityController {
  private List<Attendance> attendances;
  private List<ExerciseRecord> exerciseRecords;
  private List<Point> points;
  private List<PointHistory> pointHistories;
  private PointPolicy pointPolicy;
  private List<Membership> memberMemberships;

  public ActivityController() {
    this.attendances = new ArrayList<>();
    this.exerciseRecords = new ArrayList<>();
    this.points = new ArrayList<>();
    this.pointHistories = new ArrayList<>();
    this.pointPolicy = null;
    this.memberMemberships = null;
  }

  public void setMemberMemberships(List<Membership> memberMemberships) {
    this.memberMemberships = memberMemberships;
  }

  public void setPointPolicy(PointPolicy pointPolicy) {
    this.pointPolicy = pointPolicy;
  }

  public boolean init() {
    return memberMemberships != null && pointPolicy != null;
  }

  public boolean hasCheckedInToday(String memberId) {
    LocalDate today = LocalDate.now();
    for (Attendance a : attendances) {
      if (a.getMemberId().equals(memberId)
          && a.getAttendanceDateTime().toLocalDate().equals(today)) {
        return true;
      }
    }
    return false;
  }

  public boolean checkIn(String memberId) {
    String id = "att-" + String.format("%03d", attendances.size() + 1);
    Attendance attendance = new Attendance(id, memberId, LocalDateTime.now(), "QR");
    if (!attendance.init()) return false;
    attendances.add(attendance);
    return true;
  }

  public boolean hasActiveMembership(String memberId) {
    for (Membership ms : memberMemberships) {
      if (ms.getMemberId().equals(memberId) && "ACTIVE".equals(ms.getStatus())) {
        return true;
      }
    }
    return false;
  }

  public boolean addExerciseRecord(String memberId, LocalDate date, String exerciseType,
      int exerciseTime, int sets, int reps, String memo, String photo) {
    String id = "rec-" + String.format("%03d", exerciseRecords.size() + 1);
    ExerciseRecord record = new ExerciseRecord(id, memberId, date,
        exerciseType, exerciseTime, sets, reps, memo, photo);
    if (!record.init()) return false;
    exerciseRecords.add(record);
    return true;
  }

  public boolean hasEarnedPointsToday(String memberId) {
    LocalDate today = LocalDate.now();
    for (PointHistory ph : pointHistories) {
      if (ph.getMemberId().equals(memberId)
          && ph.getDate().equals(today)
          && "운동적립".equals(ph.getType())) {
        return true;
      }
    }
    return false;
  }

  public int earnPoints(String memberId, int exerciseTime) {
    if (pointPolicy == null) return -1;

    int earned = pointPolicy.getBasePoints();
    boolean timeBonusApplied = exerciseTime >= pointPolicy.getExerciseTimeStandard();
    if (timeBonusApplied) {
      earned += pointPolicy.getTimeBonusPoints();
    }

    Point memberPoint = findOrCreatePoint(memberId);
    if (memberPoint == null) return -1;

    memberPoint.setBalance(memberPoint.getBalance() + earned);
    String histId = "ph-" + String.format("%03d", pointHistories.size() + 1);
    pointHistories.add(new PointHistory(histId, memberId, "운동적립", earned,
        "운동 기록 포인트 적립", LocalDate.now(), memberPoint.getBalance()));

    int consecutive = countConsecutiveAttendance(memberId);
    if (consecutive > 0 && consecutive % pointPolicy.getConsecutiveAttendanceDays() == 0) {
      int bonus = pointPolicy.getConsecutiveAttendanceBonus();
      memberPoint.setBalance(memberPoint.getBalance() + bonus);
      String bonusHistId = "ph-" + String.format("%03d", pointHistories.size() + 1);
      pointHistories.add(new PointHistory(bonusHistId, memberId, "연속출석보너스", bonus,
          consecutive + "일 연속 출석 달성 보너스", LocalDate.now(), memberPoint.getBalance()));
      earned += bonus;
    }
    return earned;
  }

  public boolean isTimeBonusApplicable(int exerciseTime) {
    return pointPolicy != null && exerciseTime >= pointPolicy.getExerciseTimeStandard();
  }

  public int getTimeBonusStandard() {
    return pointPolicy != null ? pointPolicy.getExerciseTimeStandard() : 30;
  }

  public int getConsecutiveCount(String memberId) {
    return countConsecutiveAttendance(memberId);
  }

  public int getConsecutiveBonusDays() {
    return pointPolicy != null ? pointPolicy.getConsecutiveAttendanceDays() : 7;
  }

  public int getConsecutiveBonusPoints() {
    return pointPolicy != null ? pointPolicy.getConsecutiveAttendanceBonus() : 50;
  }

  private Point findOrCreatePoint(String memberId) {
    for (Point p : points) {
      if (p.getMemberId().equals(memberId)) return p;
    }
    Point newPoint = new Point("point-" + String.format("%03d", points.size() + 1),
        memberId, 0, LocalDate.now().plusYears(1));
    if (!newPoint.init()) return null;
    points.add(newPoint);
    return newPoint;
  }

  private int countConsecutiveAttendance(String memberId) {
    LocalDate check = LocalDate.now();
    int count = 0;
    while (true) {
      final LocalDate d = check;
      boolean found = false;
      for (Attendance a : attendances) {
        if (a.getMemberId().equals(memberId)
            && a.getAttendanceDateTime().toLocalDate().equals(d)) {
          found = true;
          break;
        }
      }
      if (!found) break;
      count++;
      check = check.minusDays(1);
    }
    return count;
  }

  public int getPointBalance(String memberId) {
    for (Point p : points) {
      if (p.getMemberId().equals(memberId)) return p.getBalance();
    }
    return 0;
  }

  public Point findPoint(String memberId) {
    for (Point p : points) {
      if (p.getMemberId().equals(memberId)) return p;
    }
    return null;
  }

  public void deductPoints(String memberId, int amount, String reason) {
    Point memberPoint = findPoint(memberId);
    if (memberPoint == null || amount <= 0) return;
    int deduct = Math.min(amount, memberPoint.getBalance());
    memberPoint.setBalance(memberPoint.getBalance() - deduct);
    String histId = "ph-" + String.format("%03d", pointHistories.size() + 1);
    pointHistories.add(new PointHistory(histId, memberId, "사용", -deduct,
        reason, LocalDate.now(), memberPoint.getBalance()));
  }

  public List<Attendance> getAttendancesByMember(String memberId) {
    List<Attendance> result = new ArrayList<>();
    for (Attendance a : attendances) {
      if (a.getMemberId().equals(memberId)) result.add(a);
    }
    return result;
  }

  public List<PointHistory> getPointHistoriesByMember(String memberId) {
    List<PointHistory> result = new ArrayList<>();
    for (PointHistory ph : pointHistories) {
      if (ph.getMemberId().equals(memberId)) result.add(ph);
    }
    return result;
  }

  public List<Point> getPoints() { return points; }
  public List<PointHistory> getPointHistories() { return pointHistories; }
  public List<Attendance> getAttendances() { return attendances; }
  public List<ExerciseRecord> getExerciseRecords() { return exerciseRecords; }
}
