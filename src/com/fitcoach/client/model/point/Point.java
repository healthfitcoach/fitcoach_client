package com.fitcoach.client.model.point;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Point {

    private String pointId;
    private String memberId;
    private int balance;
    private LocalDate expiryDate;
    private List<PointHistory> histories;

    public Point(String pointId, String memberId, int balance, LocalDate expiryDate) {
        this.pointId = pointId;
        this.memberId = memberId;
        this.balance = balance;
        this.expiryDate = expiryDate;
        this.histories = new ArrayList<>();
    }

    public boolean init() {
        return true;
    }

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
