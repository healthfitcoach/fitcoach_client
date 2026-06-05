package com.fitcoach.client.model.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "attendance")
public class Attendance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "member_id")
  private Long memberId;

  @Column(name = "attendance_date_time")
  private LocalDateTime attendanceDateTime;

  @Column(name = "auth_method")
  private String authMethod;

  @Transient
  private List<ExerciseRecord> exerciseRecords;

  public Attendance() {
    this.exerciseRecords = new ArrayList<>();
  }

  public Attendance(Long memberId, LocalDateTime attendanceDateTime, String authMethod) {
    this.memberId = memberId;
    this.attendanceDateTime = attendanceDateTime;
    this.authMethod = authMethod;
    this.exerciseRecords = new ArrayList<>();
  }

  public Long getId() { return id; }
  public Long getMemberId() { return memberId; }
  public LocalDateTime getAttendanceDateTime() { return attendanceDateTime; }
  public String getAuthMethod() { return authMethod; }
  public List<ExerciseRecord> getExerciseRecords() { return exerciseRecords; }
  public void addExerciseRecord(ExerciseRecord record) { this.exerciseRecords.add(record); }
}
