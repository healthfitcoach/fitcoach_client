package com.fitcoach.client.model.order;

import java.time.LocalDateTime;

public class Payment {

    private String paymentId;
    private String memberId;
    private String productId;
    private String productType;
    private String paymentMethod;
    private int amount;
    private int usedPoints;
    private String status;
    private LocalDateTime paymentDateTime;
    private String pgAuthNumber;

    public Payment(String paymentId, String memberId, String productId, String productType,
                   String paymentMethod, int amount, int usedPoints,
                   String status, LocalDateTime paymentDateTime) {
        this.paymentId = paymentId;
        this.memberId = memberId;
        this.productId = productId;
        this.productType = productType;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.usedPoints = usedPoints;
        this.status = status;
        this.paymentDateTime = paymentDateTime;
        this.pgAuthNumber = null;
    }

    public boolean init() {
        return true;
    }

    public boolean processPayment() { return false; }

    public void cancelPayment() {}

    public void applyPoints() {}

    public void search() {}

    // Getters & Setters
    public String getPaymentId() { return paymentId; }
    public String getMemberId() { return memberId; }
    public String getProductId() { return productId; }
    public String getProductType() { return productType; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public int getAmount() { return amount; }
    public int getUsedPoints() { return usedPoints; }
    public void setUsedPoints(int usedPoints) { this.usedPoints = usedPoints; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getPaymentDateTime() { return paymentDateTime; }
    public String getPgAuthNumber() { return pgAuthNumber; }
    public void setPgAuthNumber(String pgAuthNumber) { this.pgAuthNumber = pgAuthNumber; }
}
