package com.fitcoach.client.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

@MappedSuperclass
public abstract class Product {

  @Id
  @Column(name = "product_id")
  private String productId;

  @Column(name = "product_name")
  private String productName;

  @Column(name = "price")
  private int price;

  @Column(name = "description")
  private String description;

  @Transient  // DB 컬럼 없음 — Java 내부 구분용
  private String type;

  protected Product() {}  // JPA 필수 no-arg 생성자

  public Product(String productId, String productName, int price, String description, String type) {
    this.productId = productId;
    this.productName = productName;
    this.price = price;
    this.description = description;
    this.type = type;
  }

  public boolean init() {
    return true;
  }

  public abstract void purchase();

  public abstract void getDetail();

  public abstract void search();

  // Getters & Setters
  public String getProductId() { return productId; }
  public String getProductName() { return productName; }
  public void setProductName(String productName) { this.productName = productName; }
  public int getPrice() { return price; }
  public void setPrice(int price) { this.price = price; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public String getType() { return type; }
  public void setType(String type) { this.type = type; }
}
