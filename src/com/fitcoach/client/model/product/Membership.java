package com.fitcoach.client.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;

@Entity
@Table(name = "membership")
public class Membership extends Product {

  @Column(name = "membership_id")
  private String membershipId;

  @Column(name = "member_id")
  private String memberId;

  @Column(name = "status")
  private String status;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Transient  // DB 컬럼 없음
  private LocalDate pauseDate;

  @Transient  // DB 컬럼 없음
  private LocalDate resumeDate;

  public Membership() {}  // JPA 필수 no-arg 생성자

  public Membership(String productId, String productName, int price, String description,
      String membershipId, String memberId, String status,
      LocalDate startDate, LocalDate endDate) {
    super(productId, productName, price, description, "MEMBERSHIP");
    this.membershipId = membershipId;
    this.memberId = memberId;
    this.status = status;
    this.startDate = startDate;
    this.endDate = endDate;
    this.pauseDate = null;
    this.resumeDate = null;
  }

  @Override
  public boolean init() { return true; }

  @Override
  public void purchase() {}

  @Override
  public void getDetail() {}

  @Override
  public void search() {}

  public void getUsageHistory() {}

  public int checkRemainingPeriod() { return 0; }

  public void requestRenewal() {}

  public void refund() {}

  // Getters & Setters
  public String getMembershipId() { return membershipId; }
  public String getMemberId() { return memberId; }
  public void setMemberId(String memberId) { this.memberId = memberId; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public LocalDate getStartDate() { return startDate; }
  public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
  public LocalDate getEndDate() { return endDate; }
  public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
  public LocalDate getPauseDate() { return pauseDate; }
  public void setPauseDate(LocalDate pauseDate) { this.pauseDate = pauseDate; }
  public LocalDate getResumeDate() { return resumeDate; }
  public void setResumeDate(LocalDate resumeDate) { this.resumeDate = resumeDate; }
}
