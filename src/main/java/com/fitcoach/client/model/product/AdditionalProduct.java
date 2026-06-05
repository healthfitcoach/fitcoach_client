package com.fitcoach.client.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "additional_product")
@PrimaryKeyJoinColumn(name = "product_id")
@DiscriminatorValue("ADDITIONAL")
public class AdditionalProduct extends Product {

  @Column(name = "usage_period_days")
  private int usagePeriodDays;

  public AdditionalProduct() {}

  public AdditionalProduct(String name, int price, String description,
      ProductStatus status, int usagePeriodDays) {
    super(name, price, description, status);
    this.usagePeriodDays = usagePeriodDays;
  }

  @Override public void purchase() {}
  @Override public void getDetail() {}
  @Override public void search() {}

  public int getUsagePeriodDays() { return usagePeriodDays; }
  public void setUsagePeriodDays(int usagePeriodDays) { this.usagePeriodDays = usagePeriodDays; }
}
