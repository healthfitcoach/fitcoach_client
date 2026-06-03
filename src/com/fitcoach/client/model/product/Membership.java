package com.fitcoach.client.model.product;

import java.time.LocalDate;

public class Membership extends Product {

    private String membershipId;
    private String memberId;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate pauseDate;
    private LocalDate resumeDate;

    public Membership(String productId, String productName, int price, String description,
                      String membershipId, String memberId, String status,
                      LocalDate startDate, LocalDate endDate) {
        super(productId, productName, price, description, "MEMBERSHIP");
        this.membershipId = membershipId;
        this.memberId = memberId;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pauseDate = null;
        this.resumeDate = null;
    }

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public void purchase() {}

    @Override
    public void getDetail() {}

    @Override
    public void search() {}

    public void getUsageHistory() {}

    public int checkRemainingPeriod() { return 0; }

    public void requestRenewal() {}

    public void refund() {}

    // Getters & Setters
    public String getMembershipId() { return membershipId; }
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public LocalDate getPauseDate() { return pauseDate; }
    public void setPauseDate(LocalDate pauseDate) { this.pauseDate = pauseDate; }
    public LocalDate getResumeDate() { return resumeDate; }
    public void setResumeDate(LocalDate resumeDate) { this.resumeDate = resumeDate; }
}
