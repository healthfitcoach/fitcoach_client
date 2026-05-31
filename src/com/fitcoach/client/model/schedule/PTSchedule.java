package com.fitcoach.client.model.schedule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "pt_schedule")
public class PTSchedule {

  @Id
  @Column(name = "schedule_id")
  private String scheduleId;

  @Column(name = "member_product_id")
  private String ptId;  // member_product.member_product_id 참조

  @Column(name = "member_id")
  private String memberId;

  @Column(name = "trainer_id")
  private String trainerId;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "time")
  private LocalTime time;

  @Column(name = "status")
  private String status;

  public PTSchedule() {}  // JPA 필수 no-arg 생성자

  public PTSchedule(String scheduleId, String ptId, String memberId, String trainerId,
      LocalDate date, LocalTime time, String status) {
    this.scheduleId = scheduleId;
    this.ptId = ptId;
    this.memberId = memberId;
    this.trainerId = trainerId;
    this.date = date;
    this.time = time;
    this.status = status;
  }

  public boolean init() { return true; }

  public void reserve() {}

  public void search() {}

  public void cancel() {}

  // Getters & Setters
  public String getScheduleId() { return scheduleId; }
  public String getPtId() { return ptId; }
  public String getMemberId() { return memberId; }
  public String getTrainerId() { return trainerId; }
  public LocalDate getDate() { return date; }
  public void setDate(LocalDate date) { this.date = date; }
  public LocalTime getTime() { return time; }
  public void setTime(LocalTime time) { this.time = time; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
}
