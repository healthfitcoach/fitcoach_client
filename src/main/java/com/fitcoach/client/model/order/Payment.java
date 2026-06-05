package com.fitcoach.client.model.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {

  @Id
  @Column(name = "payment_id")
  private String paymentId;

  @Column(name = "member_id")
  private String memberId;

  @Column(name = "product_id")
  private String productId;

  @Column(name = "product_type")
  private String productType;

  @Column(name = "payment_method")
  private String paymentMethod;

  @Column(name = "amount")
  private int amount;

  @Column(name = "used_points")
  private int usedPoints;

  @Column(name = "status")
  private String status;

  @Column(name = "payment_date_time")
  private LocalDateTime paymentDateTime;

  @Transient  // DB 컬럼 없음
  private String pgAuthNumber;

  public Payment() {}  // JPA 필수 no-arg 생성자

  public Payment(String paymentId, String memberId, String productId, String productType,
      String paymentMethod, int amount, int usedPoints,
      String status, LocalDateTime paymentDateTime) {
    this.paymentId = paymentId;
    this.memberId = memberId;
    this.productId = productId;
    this.productType = productType;
    this.paymentMethod = paymentMethod;
    this.amount = amount;
    this.usedPoints = usedPoints;
    this.status = status;
    this.paymentDateTime = paymentDateTime;
    this.pgAuthNumber = null;
  }

  public boolean init() { return true; }

  public boolean processPayment() { return false; }

  public void cancelPayment() {}

  public void applyPoints() {}

  public void search() {}

  // Getters & Setters
  public String getPaymentId() { return paymentId; }
  public String getMemberId() { return memberId; }
  public String getProductId() { return productId; }
  public String getProductType() { return productType; }
  public String getPaymentMethod() { return paymentMethod; }
  public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
  public int getAmount() { return amount; }
  public int getUsedPoints() { return usedPoints; }
  public void setUsedPoints(int usedPoints) { this.usedPoints = usedPoints; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public LocalDateTime getPaymentDateTime() { return paymentDateTime; }
  public String getPgAuthNumber() { return pgAuthNumber; }
  public void setPgAuthNumber(String pgAuthNumber) { this.pgAuthNumber = pgAuthNumber; }
}
