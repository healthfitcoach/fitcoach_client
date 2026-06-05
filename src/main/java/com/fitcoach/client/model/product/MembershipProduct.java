package com.fitcoach.client.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "membership_product")
@PrimaryKeyJoinColumn(name = "product_id")
@DiscriminatorValue("MEMBERSHIP")
public class MembershipProduct extends Product {

  @Column(name = "month_count")
  private int monthCount;

  public MembershipProduct() {}

  public MembershipProduct(String name, int price, String description,
      ProductStatus status, int monthCount) {
    super(name, price, description, status);
    this.monthCount = monthCount;
  }

  @Override public void purchase() {}
  @Override public void getDetail() {}
  @Override public void search() {}

  public int getMonthCount() { return monthCount; }
  public void setMonthCount(int monthCount) { this.monthCount = monthCount; }
}
