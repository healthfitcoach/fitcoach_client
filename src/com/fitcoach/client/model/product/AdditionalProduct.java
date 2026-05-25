package com.fitcoach.client.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "additional_product")
public class AdditionalProduct extends Product {

  @Column(name = "additional_product_id")
  private String additionalProductId;

  @Column(name = "member_id")
  private String memberId;

  @Column(name = "status")
  private String status;

  @Column(name = "usage_period")
  private int usagePeriod;

  public AdditionalProduct() {}  // JPA 필수 no-arg 생성자

  public AdditionalProduct(String productId, String productName, int price, String description,
      String additionalProductId, String memberId, String status, int usagePeriod) {
    super(productId, productName, price, description, "ADDITIONAL");
    this.additionalProductId = additionalProductId;
    this.memberId = memberId;
    this.status = status;
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
  public String getAdditionalProductId() { return additionalProductId; }
  public String getMemberId() { return memberId; }
  public void setMemberId(String memberId) { this.memberId = memberId; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public int getUsagePeriod() { return usagePeriod; }
  public void setUsagePeriod(int usagePeriod) { this.usagePeriod = usagePeriod; }
}
