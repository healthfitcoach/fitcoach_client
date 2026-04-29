package distbuted_programming.domain.product;

public class Product {

  private String productId;
  private String productName;
  private int price;
  private String description;
  private String type;

  public Product() {}

  public Product(String productId, String productName, int price,
      String description, String type) {
    this.productId = productId;
    this.productName = productName;
    this.price = price;
    this.description = description;
    this.type = type;
  }

  public void purchase() {
    System.out.println(productName + " 구매가 완료되었습니다.");
  }

  public Product getDetail() {
    return this;
  }

  public Product get() {
    return this;
  }

  public String getProductId() { return productId; }
  public String getProductName() { return productName; }
  public int getPrice() { return price; }
  public String getDescription() { return description; }
  public String getType() { return type; }

  @Override
  public String toString() {
    return productName + " - " + price + "원 | " + description;
  }
}
