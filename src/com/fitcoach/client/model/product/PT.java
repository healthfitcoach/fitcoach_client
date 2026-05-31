package com.fitcoach.client.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "pt")
public class PT extends Product {

  // 카탈로그 기본 횟수 — 구매 시 MemberProduct.totalCount / remainingCount 초기값으로 사용
  @Column(name = "total_count")
  private int totalCount;

  public PT() {}  // JPA 필수 no-arg 생성자

  public PT(String productId, String productName, int price,
      String description, int totalCount) {
    super(productId, productName, price, description, "PT");
    this.totalCount = totalCount;
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
  public int getTotalCount() { return totalCount; }
  public void setTotalCount(int totalCount) { this.totalCount = totalCount; }
}
