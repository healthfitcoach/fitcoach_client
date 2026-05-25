package com.fitcoach.client.model.product;

import com.fitcoach.client.model.schedule.PTSchedule;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pt")
public class PT extends Product {

  @Column(name = "pt_id")
  private String ptId;

  @Column(name = "member_id")
  private String memberId;

  @Column(name = "trainer_id")
  private String trainerId;

  @Column(name = "total_count")
  private int totalCount;

  @Column(name = "remaining_count")
  private int remainingCount;

  @Column(name = "status")
  private String status;

  @Transient  // Aggregation 필드 — DB 컬럼 아님
  private List<PTSchedule> schedules;

  public PT() {  // JPA 필수 no-arg 생성자
    this.schedules = new ArrayList<>();
  }

  public PT(String productId, String productName, int price, String description,
      String ptId, String memberId, String trainerId,
      int totalCount, int remainingCount, String status) {
    super(productId, productName, price, description, "PT");
    this.ptId = ptId;
    this.memberId = memberId;
    this.trainerId = trainerId;
    this.totalCount = totalCount;
    this.remainingCount = remainingCount;
    this.status = status;
    this.schedules = new ArrayList<>();
  }

  @Override
  public boolean init() { return true; }

  @Override
  public void purchase() {}

  @Override
  public void getDetail() {}

  @Override
  public void search() {}

  public void checkStatus() {}

  public void cancel() {}

  // Getters & Setters
  public String getPtId() { return ptId; }
  public String getMemberId() { return memberId; }
  public void setMemberId(String memberId) { this.memberId = memberId; }
  public String getTrainerId() { return trainerId; }
  public void setTrainerId(String trainerId) { this.trainerId = trainerId; }
  public int getTotalCount() { return totalCount; }
  public int getRemainingCount() { return remainingCount; }
  public void setRemainingCount(int remainingCount) { this.remainingCount = remainingCount; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public List<PTSchedule> getSchedules() { return schedules; }
  public void addSchedule(PTSchedule schedule) { this.schedules.add(schedule); }
}
