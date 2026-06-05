package com.fitcoach.client.model.product;

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
@Table(name = "membership")
public class Membership {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "member_id")
  private Long memberId;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "type")
  private String type;

  @Column(name = "price")
  private int price;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private MembershipStatus status;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "pause_date")
  private LocalDate pauseDate;

  @Column(name = "resume_date")
  private LocalDate resumeDate;

  public enum MembershipStatus {
    ACTIVE, PAUSED, CANCELLED
  }

  public Membership() {}

  public Membership(Long memberId, Long productId, String type, int price,
      MembershipStatus status, LocalDate startDate, LocalDate endDate) {
    this.memberId = memberId;
    this.productId = productId;
    this.type = type;
    this.price = price;
    this.status = status;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public Long getId() { return id; }
  public Long getMemberId() { return memberId; }
  public Long getProductId() { return productId; }
  public String getType() { return type; }
  public int getPrice() { return price; }
  public MembershipStatus getStatus() { return status; }
  public void setStatus(MembershipStatus status) { this.status = status; }
  public LocalDate getStartDate() { return startDate; }
  public LocalDate getEndDate() { return endDate; }
  public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
  public LocalDate getPauseDate() { return pauseDate; }
  public void setPauseDate(LocalDate pauseDate) { this.pauseDate = pauseDate; }
  public LocalDate getResumeDate() { return resumeDate; }
  public void setResumeDate(LocalDate resumeDate) { this.resumeDate = resumeDate; }
}
