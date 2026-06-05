package com.fitcoach.client.model.point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "point_policy")
public class PointPolicy {

  @Id
  @Column(name = "policy_id")
  private String policyId;

  @Column(name = "base_points")
  private int basePoints;

  @Column(name = "time_bonus_points")
  private int timeBonusPoints;

  @Column(name = "exercise_time_standard")
  private int exerciseTimeStandard;

  @Column(name = "consecutive_attendance_days")
  private int consecutiveAttendanceDays;

  @Column(name = "consecutive_attendance_bonus")
  private int consecutiveAttendanceBonus;

  public PointPolicy() {}  // JPA 필수 no-arg 생성자

  public PointPolicy(String policyId, int basePoints, int timeBonusPoints,
      int exerciseTimeStandard, int consecutiveAttendanceDays,
      int consecutiveAttendanceBonus) {
    this.policyId = policyId;
    this.basePoints = basePoints;
    this.timeBonusPoints = timeBonusPoints;
    this.exerciseTimeStandard = exerciseTimeStandard;
    this.consecutiveAttendanceDays = consecutiveAttendanceDays;
    this.consecutiveAttendanceBonus = consecutiveAttendanceBonus;
  }

  public boolean init() { return true; }

  public int calculatePoints(int exerciseTime) { return 0; }

  public boolean checkConsecutiveAttendanceBonus(String memberId) { return false; }

  public void search() {}

  // Getters
  public String getPolicyId() { return policyId; }
  public int getBasePoints() { return basePoints; }
  public int getTimeBonusPoints() { return timeBonusPoints; }
  public int getExerciseTimeStandard() { return exerciseTimeStandard; }
  public int getConsecutiveAttendanceDays() { return consecutiveAttendanceDays; }
  public int getConsecutiveAttendanceBonus() { return consecutiveAttendanceBonus; }
}
