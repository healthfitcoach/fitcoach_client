package com.fitcoach.client.model.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")  // 'order'는 SQL 예약어 → orders
public class Order {

  @Id
  @Column(name = "order_id")
  private String orderId;

  @Column(name = "member_id")
  private String memberId;

  @Column(name = "product_id")
  private String productId;

  @Column(name = "quantity")
  private int quantity;

  @Column(name = "total_amount")
  private int totalAmount;

  @Column(name = "shipping_address")
  private String shippingAddress;

  @Column(name = "status")
  private String status;

  @Column(name = "order_date_time")
  private LocalDateTime orderDateTime;

  @Transient  // Aggregation 필드 — DB 컬럼 아님
  private Payment payment;

  public Order() {}  // JPA 필수 no-arg 생성자

  public Order(String orderId, String memberId, String productId, int quantity,
      int totalAmount, String shippingAddress, String status, LocalDateTime orderDateTime) {
    this.orderId = orderId;
    this.memberId = memberId;
    this.productId = productId;
    this.quantity = quantity;
    this.totalAmount = totalAmount;
    this.shippingAddress = shippingAddress;
    this.status = status;
    this.orderDateTime = orderDateTime;
    this.payment = null;
  }

  public boolean init() { return true; }

  public void createOrder() {}

  public void search() {}

  // Getters & Setters
  public String getOrderId() { return orderId; }
  public String getMemberId() { return memberId; }
  public String getProductId() { return productId; }
  public int getQuantity() { return quantity; }
  public int getTotalAmount() { return totalAmount; }
  public String getShippingAddress() { return shippingAddress; }
  public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public LocalDateTime getOrderDateTime() { return orderDateTime; }
  public Payment getPayment() { return payment; }
  public void setPayment(Payment payment) { this.payment = payment; }
}
