package com.fitcoach.client.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "pt_product")
@PrimaryKeyJoinColumn(name = "product_id")
@DiscriminatorValue("PT_PRODUCT")
public class PTProduct extends Product {

  @Column(name = "session_count")
  private int sessionCount;

  @Column(name = "pr_message")
  private String prMessage;

  @Column(name = "trainer_id")
  private Long trainerId;

  public PTProduct() {}

  public PTProduct(String name, int price, String description, ProductStatus status,
      int sessionCount, String prMessage, Long trainerId) {
    super(name, price, description, status);
    this.sessionCount = sessionCount;
    this.prMessage = prMessage;
    this.trainerId = trainerId;
  }

  @Override public void purchase() {}
  @Override public void getDetail() {}
  @Override public void search() {}

  public int getSessionCount() { return sessionCount; }
  public void setSessionCount(int sessionCount) { this.sessionCount = sessionCount; }
  public String getPrMessage() { return prMessage; }
  public void setPrMessage(String prMessage) { this.prMessage = prMessage; }
  public Long getTrainerId() { return trainerId; }
  public void setTrainerId(Long trainerId) { this.trainerId = trainerId; }
}
