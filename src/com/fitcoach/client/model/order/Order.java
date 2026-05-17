package com.fitcoach.client.model.order;

import java.time.LocalDateTime;

public class Order {

    private String orderId;
    private String memberId;
    private String productId;
    private int quantity;
    private int totalAmount;
    private String shippingAddress;
    private String status;
    private LocalDateTime orderDateTime;
    private Payment payment;

    public Order(String orderId, String memberId, String productId, int quantity,
                 int totalAmount, String shippingAddress, String status, LocalDateTime orderDateTime) {
        this.orderId = orderId;
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.payment = null;
    }

    public boolean init() {
        return true;
    }

    public void createOrder() {}

    public void search() {}

    // Getters & Setters
    public String getOrderId() { return orderId; }
    public String getMemberId() { return memberId; }
    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public int getTotalAmount() { return totalAmount; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getOrderDateTime() { return orderDateTime; }
    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }
}
