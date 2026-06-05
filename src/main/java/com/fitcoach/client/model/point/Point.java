package com.fitcoach.client.model.point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "point")
public class Point {

  @Id
  @Column(name = "point_id")
  private String pointId;

  @Column(name = "member_id")
  private String memberId;

  @Column(name = "balance")
  private int balance;

  @Column(name = "expiry_date")
  private LocalDate expiryDate;

  @Transient  // Aggregation 필드 — DB 컬럼 아님
  private List<PointHistory> histories;

  public Point() {  // JPA 필수 no-arg 생성자
    this.histories = new ArrayList<>();
  }

  public Point(String pointId, String memberId, int balance, LocalDate expiryDate) {
    this.pointId = pointId;
    this.memberId = memberId;
    this.balance = balance;
    this.expiryDate = expiryDate;
    this.histories = new ArrayList<>();
  }

  public boolean init() { return true; }

  public void earnPoints(int amount) {}

  public boolean usePoints(int amount) { return false; }

  public void search() {}

  // Getters & Setters
  public String getPointId() { return pointId; }
  public String getMemberId() { return memberId; }
  public int getBalance() { return balance; }
  public void setBalance(int balance) { this.balance = balance; }
  public LocalDate getExpiryDate() { return expiryDate; }
  public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
  public List<PointHistory> getHistories() { return histories; }
  public void addHistory(PointHistory history) { this.histories.add(history); }
}
