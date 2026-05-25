package com.fitcoach.client.model.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
  @Column(name = "attendance_id")
  private String attendanceId;

  @Column(name = "member_id")
  private String memberId;

  @Column(name = "attendance_date_time")
  private LocalDateTime attendanceDateTime;

  @Column(name = "auth_method")
  private String authMethod;

  @Transient  // Aggregation 필드 — DB 컬럼 아님
  private List<ExerciseRecord> exerciseRecords;

  public Attendance() {  // JPA 필수 no-arg 생성자
    this.exerciseRecords = new ArrayList<>();
  }

  public Attendance(String attendanceId, String memberId,
      LocalDateTime attendanceDateTime, String authMethod) {
    this.attendanceId = attendanceId;
    this.memberId = memberId;
    this.attendanceDateTime = attendanceDateTime;
    this.authMethod = authMethod;
    this.exerciseRecords = new ArrayList<>();
  }

  public boolean init() { return true; }

  public void checkAttendance() {}

  public void search() {}

  // Getters & Setters
  public String getAttendanceId() { return attendanceId; }
  public String getMemberId() { return memberId; }
  public LocalDateTime getAttendanceDateTime() { return attendanceDateTime; }
  public String getAuthMethod() { return authMethod; }
  public List<ExerciseRecord> getExerciseRecords() { return exerciseRecords; }
  public void addExerciseRecord(ExerciseRecord record) { this.exerciseRecords.add(record); }
}
