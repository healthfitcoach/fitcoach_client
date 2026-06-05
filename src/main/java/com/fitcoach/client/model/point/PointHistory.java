package com.fitcoach.client.model.point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "point_history")
public class PointHistory {

  @Id
  @Column(name = "history_id")
  private String historyId;

  @Column(name = "member_id")
  private String memberId;

  @Column(name = "`type`")  // type은 일부 SQL 방언에서 예약어 — 백틱으로 안전하게 처리
  private String type;

  @Column(name = "amount")
  private int amount;

  @Column(name = "reason")
  private String reason;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "balance_after")
  private int balanceAfter;

  public PointHistory() {}  // JPA 필수 no-arg 생성자

  public PointHistory(String historyId, String memberId, String type, int amount,
      String reason, LocalDate date, int balanceAfter) {
    this.historyId = historyId;
    this.memberId = memberId;
    this.type = type;
    this.amount = amount;
    this.reason = reason;
    this.date = date;
    this.balanceAfter = balanceAfter;
  }

  public boolean init() { return true; }

  public void listByPeriod(LocalDate from, LocalDate to) {}

  public void getDetail() {}

  public void search() {}

  // Getters
  public String getHistoryId() { return historyId; }
  public String getMemberId() { return memberId; }
  public String getType() { return type; }
  public int getAmount() { return amount; }
  public String getReason() { return reason; }
  public LocalDate getDate() { return date; }
  public int getBalanceAfter() { return balanceAfter; }
}
