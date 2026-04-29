package distbuted_programming.domain.product;

import java.util.ArrayList;
import java.util.List;

public class SportEquipment extends Product {

  private String productId;
  private String name;
  private String category;
  private int price;
  private int stock;
  private String description;

  private static final List<SportEquipment> sportEquipments = new ArrayList<>();

  public SportEquipment() {}

  public SportEquipment(String productId, String name, String category,
      int price, int stock, String description) {
    super(productId, name, price, description, "운동용품");
    this.productId = productId;
    this.name = name;
    this.category = category;
    this.price = price;
    this.stock = stock;
    this.description = description;
  }

  public List<SportEquipment> getList(String category) {
    if (category == null || category.isEmpty()) {
      return sportEquipments;
    }
    List<SportEquipment> result = new ArrayList<>();
    for (SportEquipment e : sportEquipments) {
      if (e.category.equals(category)) {
        result.add(e);
      }
    }
    return result;
  }

  public List<SportEquipment> search(String keyword) {
    List<SportEquipment> result = new ArrayList<>();
    for (SportEquipment e : sportEquipments) {
      if (e.name.contains(keyword) || e.category.contains(keyword)
          || e.description.contains(keyword)) {
        result.add(e);
      }
    }
    return result;
  }

  @Override
  public SportEquipment get() {
    return this;
  }

  public String getProductId() { return productId; }
  public String getName() { return name; }
  public String getCategory() { return category; }
  public int getPrice() { return price; }
  public int getStock() { return stock; }
  public String getDescription() { return description; }

  public void decreaseStock(int quantity) {
    this.stock -= quantity;
  }

  public static List<SportEquipment> getAll() { return sportEquipments; }
  public static void add(SportEquipment e) { sportEquipments.add(e); }

  public static SportEquipment findById(String productId) {
    for (SportEquipment e : sportEquipments) {
      if (e.productId.equals(productId)) {
        return e;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return "[" + category + "] " + name + " | " + price + "원 | 재고: " + stock
        + " | " + description;
  }
}
