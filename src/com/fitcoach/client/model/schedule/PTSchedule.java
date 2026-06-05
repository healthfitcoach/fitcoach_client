package com.fitcoach.client.model.schedule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "pt_schedule")
public class PTSchedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "pt_id")
  private Long ptId;

  @Column(name = "trainer_id")
  private Long trainerId;

  @Column(name = "member_id")
  private Long memberId;

  @Column(name = "schedule_date")
  private LocalDate scheduleDate;

  @Column(name = "schedule_time")
  private String scheduleTime;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private ScheduleStatus status;

  public enum ScheduleStatus {
    SCHEDULED, COMPLETED, CANCELLED
  }

  public PTSchedule() {}

  public PTSchedule(Long ptId, Long trainerId, Long memberId,
      LocalDate scheduleDate, String scheduleTime, ScheduleStatus status) {
    this.ptId = ptId;
    this.trainerId = trainerId;
    this.memberId = memberId;
    this.scheduleDate = scheduleDate;
    this.scheduleTime = scheduleTime;
    this.status = status;
  }

  public Long getId() { return id; }
  public Long getPtId() { return ptId; }
  public Long getTrainerId() { return trainerId; }
  public Long getMemberId() { return memberId; }
  public LocalDate getScheduleDate() { return scheduleDate; }
  public void setScheduleDate(LocalDate scheduleDate) { this.scheduleDate = scheduleDate; }
  public String getScheduleTime() { return scheduleTime; }
  public void setScheduleTime(String scheduleTime) { this.scheduleTime = scheduleTime; }
  public ScheduleStatus getStatus() { return status; }
  public void setStatus(ScheduleStatus status) { this.status = status; }
}
