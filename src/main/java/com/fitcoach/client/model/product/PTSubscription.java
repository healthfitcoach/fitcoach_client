package com.fitcoach.client.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pt")
public class PTSubscription {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private PTStatus status;

  @Column(name = "total_count")
  private int totalCount;

  @Column(name = "remaining_count")
  private int remainingCount;

  @Column(name = "price")
  private int price;

  @Column(name = "trainer_id")
  private Long trainerId;

  @Column(name = "member_id")
  private Long memberId;

  @Column(name = "product_id")
  private Long productId;

  public enum PTStatus {
    ACTIVE, COMPLETED
  }

  public PTSubscription() {}

  public PTSubscription(PTStatus status, int totalCount, int remainingCount,
      int price, Long trainerId, Long memberId, Long productId) {
    this.status = status;
    this.totalCount = totalCount;
    this.remainingCount = remainingCount;
    this.price = price;
    this.trainerId = trainerId;
    this.memberId = memberId;
    this.productId = productId;
  }

  public Long getId() { return id; }
  public PTStatus getStatus() { return status; }
  public void setStatus(PTStatus status) { this.status = status; }
  public int getTotalCount() { return totalCount; }
  public int getRemainingCount() { return remainingCount; }
  public void setRemainingCount(int remainingCount) { this.remainingCount = remainingCount; }
  public int getPrice() { return price; }
  public Long getTrainerId() { return trainerId; }
  public Long getMemberId() { return memberId; }
  public Long getProductId() { return productId; }
}
