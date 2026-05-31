package com.fitcoach.client.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "member_product")
public class MemberProduct {

  @Id
  @Column(name = "member_product_id")
  private String memberProductId;

  @Column(name = "member_id")
  private String memberId;

  @Column(name = "product_id")
  private String productId;         // 카탈로그 FK (String ID만 보관 — Association)

  @Column(name = "product_type")
  private String productType;       // "MEMBERSHIP" | "PT" | "ADDITIONAL"

  @Column(name = "product_name")
  private String productName;       // 구매 시점 스냅샷

  @Column(name = "description")
  private String description;       // 구매 시점 스냅샷

  @Column(name = "status")
  private String status;            // "ACTIVE" | "EXPIRED" | "CANCELLED"

  @Column(name = "start_date")
  private LocalDate startDate;      // MEMBERSHIP 전용 (nullable)

  @Column(name = "end_date")
  private LocalDate endDate;        // MEMBERSHIP 전용 (nullable)

  @Column(name = "trainer_id")
  private String trainerId;         // PT 전용 (nullable)

  @Column(name = "total_count")
  private int totalCount;           // PT 전용 (기본값 0)

  @Column(name = "remaining_count")
  private int remainingCount;       // PT 전용 (기본값 0)

  @Column(name = "usage_period")
  private int usagePeriod;          // ADDITIONAL 전용 (기본값 0)

  @Column(name = "purchased_at")
  private LocalDateTime purchasedAt;

  protected MemberProduct() {}      // JPA 필수 no-arg 생성자

  public MemberProduct(String memberProductId, String memberId, String productId,
      String productType, String productName, String description, String status,
      LocalDate startDate, LocalDate endDate, String trainerId,
      int totalCount, int remainingCount, int usagePeriod, LocalDateTime purchasedAt) {
    this.memberProductId = memberProductId;
    this.memberId = memberId;
    this.productId = productId;
    this.productType = productType;
    this.productName = productName;
    this.description = description;
    this.status = status;
    this.startDate = startDate;
    this.endDate = endDate;
    this.trainerId = trainerId;
    this.totalCount = totalCount;
    this.remainingCount = remainingCount;
    this.usagePeriod = usagePeriod;
    this.purchasedAt = purchasedAt;
  }

  public boolean init() {
    if (memberProductId == null || memberProductId.isEmpty()) return false;
    if (memberId == null || memberId.isEmpty()) return false;
    if (productId == null || productId.isEmpty()) return false;
    if (!productType.equals("MEMBERSHIP") && !productType.equals("PT")
        && !productType.equals("ADDITIONAL")) return false;
    return true;
  }

  // Getters
  public String getMemberProductId() { return memberProductId; }
  public String getMemberId() { return memberId; }
  public String getProductId() { return productId; }
  public String getProductType() { return productType; }
  public String getProductName() { return productName; }
  public String getDescription() { return description; }
  public String getStatus() { return status; }
  public LocalDate getStartDate() { return startDate; }
  public LocalDate getEndDate() { return endDate; }
  public String getTrainerId() { return trainerId; }
  public int getTotalCount() { return totalCount; }
  public int getRemainingCount() { return remainingCount; }
  public int getUsagePeriod() { return usagePeriod; }
  public LocalDateTime getPurchasedAt() { return purchasedAt; }

  // Setters
  public void setStatus(String status) { this.status = status; }
  public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
  public void setRemainingCount(int remainingCount) { this.remainingCount = remainingCount; }
}
