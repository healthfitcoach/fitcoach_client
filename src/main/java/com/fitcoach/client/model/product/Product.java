package com.fitcoach.client.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "price")
  private int price;

  @Column(name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private ProductStatus status;

  public enum ProductStatus {
    ON_SALE, OFF_SALE
  }

  protected Product() {}

  public Product(String name, int price, String description, ProductStatus status) {
    this.name = name;
    this.price = price;
    this.description = description;
    this.status = status;
  }

  public boolean init() { return true; }

  public abstract void purchase();
  public abstract void getDetail();
  public abstract void search();

  public Long getId() { return id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public int getPrice() { return price; }
  public void setPrice(int price) { this.price = price; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public ProductStatus getStatus() { return status; }
  public void setStatus(ProductStatus status) { this.status = status; }
}
