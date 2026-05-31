package com.fitcoach.client.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "additional_product")
public class AdditionalProduct extends Product {

  @Column(name = "usage_period")
  private int usagePeriod;

  public AdditionalProduct() {}  // JPA 필수 no-arg 생성자

  public AdditionalProduct(String productId, String productName, int price,
      String description, int usagePeriod) {
    super(productId, productName, price, description, "ADDITIONAL");
    this.usagePeriod = usagePeriod;
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
  public int getUsagePeriod() { return usagePeriod; }
  public void setUsagePeriod(int usagePeriod) { this.usagePeriod = usagePeriod; }
}
