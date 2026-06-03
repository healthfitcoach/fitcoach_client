package com.fitcoach.client.model.product;

public class SportEquipment extends Product {

    private int stock;
    private String category;

    public SportEquipment(String productId, String productName, int price, String description,
                          int stock, String category) {
        super(productId, productName, price, description, "SPORT_EQUIPMENT");
        this.stock = stock;
        this.category = category;
    }

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public void search() {}

    @Override
    public void purchase() {}

    @Override
    public void getDetail() {}

    // Getters & Setters
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
