package distbuted_programming.domain.order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {

  private String orderId;
  private String memberId;
  private String productId;
  private int quantity;
  private String deliveryAddress;
  private int totalAmount;
  private String orderDateTime;
  private String status;

  private static final List<Order> orders = new ArrayList<>();
  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public Order() {}

  public Order(String orderId, String memberId, String productId, int quantity,
      String deliveryAddress, int totalAmount, String orderDateTime, String status) {
    this.orderId = orderId;
    this.memberId = memberId;
    this.productId = productId;
    this.quantity = quantity;
    this.deliveryAddress = deliveryAddress;
    this.totalAmount = totalAmount;
    this.orderDateTime = orderDateTime;
    this.status = status;
  }

  public Order create(String productId, int quantity, String deliveryAddress) {
    Order o = new Order(UUID.randomUUID().toString(), this.memberId, productId,
        quantity, deliveryAddress, 0, LocalDateTime.now().format(FORMATTER), "주문완료");
    orders.add(o);
    return o;
  }

  public Order get() {
    return this;
  }

  public void setTotalAmount(int totalAmount) { this.totalAmount = totalAmount; }

  public String getOrderId() { return orderId; }
  public String getMemberId() { return memberId; }
  public String getProductId() { return productId; }
  public int getQuantity() { return quantity; }
  public String getDeliveryAddress() { return deliveryAddress; }
  public int getTotalAmount() { return totalAmount; }
  public String getOrderDateTime() { return orderDateTime; }
  public String getStatus() { return status; }
  public void setMemberId(String memberId) { this.memberId = memberId; }

  public static List<Order> getAll() { return orders; }
  public static void add(Order o) { orders.add(o); }

  public static List<Order> findByMemberId(String memberId) {
    List<Order> result = new ArrayList<>();
    for (Order o : orders) {
      if (o.memberId.equals(memberId)) {
        result.add(o);
      }
    }
    return result;
  }

  @Override
  public String toString() {
    return "주문번호: " + orderId.substring(0, 8) + " | 상품ID: " + productId
        + " | 수량: " + quantity + " | 금액: " + totalAmount + "원"
        + " | 배송지: " + deliveryAddress + " | 상태: " + status;
  }
}
