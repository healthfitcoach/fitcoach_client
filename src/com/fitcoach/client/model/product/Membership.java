package com.fitcoach.client.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "membership")
public class Membership extends Product {

  // 카탈로그 판매 여부 — "ACTIVE" : 판매 중, 그 외 : 판매 종료
  @Column(name = "status")
  private String status;

  public Membership() {}  // JPA 필수 no-arg 생성자

  public Membership(String productId, String productName, int price,
      String description, String status) {
    super(productId, productName, price, description, "MEMBERSHIP");
    this.status = status;
  }

  @Override
  public boolean init() { return true; }

  @Override
  public void purchase() {}

  @Override
  public void getDetail() {}

  @Override
  public void search() {}

  // Getters & Setters
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
}
