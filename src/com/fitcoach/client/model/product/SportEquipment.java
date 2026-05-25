package com.fitcoach.client.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "sport_equipment")
public class SportEquipment extends Product {

  // product_id (부모 @Id) 가 이 테이블의 PK — 별도 ID 필드 없음

  @Column(name = "stock")
  private int stock;

  @Column(name = "category")
  private String category;

  public SportEquipment() {}  // JPA 필수 no-arg 생성자

  public SportEquipment(String productId, String productName, int price, String description,
      int stock, String category) {
    super(productId, productName, price, description, "SPORT_EQUIPMENT");
    this.stock = stock;
    this.category = category;
  }

  @Override
  public boolean init() { return true; }

  @Override
  public void search() {}

  @Override
  public void purchase() {}

  @Override
  public void getDetail() {}

  // Getters & Setters
  public int getStock() { return stock; }
  public void setStock(int stock) { this.stock = stock; }
  public String getCategory() { return category; }
  public void setCategory(String category) { this.category = category; }
}
