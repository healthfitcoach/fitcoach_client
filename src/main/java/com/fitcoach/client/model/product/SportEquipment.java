package com.fitcoach.client.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "sport_equipment")
@PrimaryKeyJoinColumn(name = "product_id")
@DiscriminatorValue("SPORT_EQUIPMENT")
public class SportEquipment extends Product {

  @Column(name = "stock")
  private int stock;

  @Column(name = "category")
  private String category;

  public SportEquipment() {}

  public SportEquipment(String name, int price, String description,
      ProductStatus status, int stock, String category) {
    super(name, price, description, status);
    this.stock = stock;
    this.category = category;
  }

  @Override public void purchase() {}
  @Override public void getDetail() {}
  @Override public void search() {}

  public int getStock() { return stock; }
  public void setStock(int stock) { this.stock = stock; }
  public String getCategory() { return category; }
  public void setCategory(String category) { this.category = category; }
}
