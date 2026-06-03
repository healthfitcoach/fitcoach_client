package com.fitcoach.client.model.product;

public class AdditionalProduct extends Product {

    private String additionalProductId;
    private String memberId;
    private String status;
    private int usagePeriod;

    public AdditionalProduct(String productId, String productName, int price, String description,
                             String additionalProductId, String memberId, String status, int usagePeriod) {
        super(productId, productName, price, description, "ADDITIONAL");
        this.additionalProductId = additionalProductId;
        this.memberId = memberId;
        this.status = status;
        this.usagePeriod = usagePeriod;
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

    // Getters & Setters
    public String getAdditionalProductId() { return additionalProductId; }
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getUsagePeriod() { return usagePeriod; }
    public void setUsagePeriod(int usagePeriod) { this.usagePeriod = usagePeriod; }
}
