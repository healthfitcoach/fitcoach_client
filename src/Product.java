public abstract class Product {

    private String productId;
    private String productName;
    private int price;
    private String description;
    private String type;

    public Product(String productId, String productName, int price, String description, String type) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.type = type;
    }

    public boolean init() {
        return true;
    }

    public abstract void purchase();

    public abstract void getDetail();

    public abstract void search();

    // Getters & Setters
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
