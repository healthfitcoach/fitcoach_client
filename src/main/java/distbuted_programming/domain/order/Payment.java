package distbuted_programming.domain.order;

import distbuted_programming.domain.point.Point;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Payment {

  private String paymentId;
  private String memberId;
  private String productType;
  private String productId;
  private int amount;
  private int usedPoint;
  private String paymentMethod;
  private String paymentDateTime;
  private String status;
  private String pgApprovalNumber;

  private static final List<Payment> payments = new ArrayList<>();
  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public Payment() {}

  public Payment(String paymentId, String memberId, String productType, String productId,
      int amount, int usedPoint, String paymentMethod, String paymentDateTime,
      String status, String pgApprovalNumber) {
    this.paymentId = paymentId;
    this.memberId = memberId;
    this.productType = productType;
    this.productId = productId;
    this.amount = amount;
    this.usedPoint = usedPoint;
    this.paymentMethod = paymentMethod;
    this.paymentDateTime = paymentDateTime;
    this.status = status;
    this.pgApprovalNumber = pgApprovalNumber;
  }

  public Payment pay(int amount, String paymentMethod, int usedPoint) {
    int finalAmount = amount - usedPoint;
    if (finalAmount < 0) finalAmount = 0;
    Payment p = new Payment(UUID.randomUUID().toString(), this.memberId,
        this.productType, this.productId, finalAmount, usedPoint, paymentMethod,
        LocalDateTime.now().format(FORMATTER), "완료",
        "PG" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
    payments.add(p);
    if (usedPoint > 0) {
      Point point = Point.findByMemberId(this.memberId);
      if (point != null) {
        point.use(usedPoint);
      }
    }
    System.out.println("[결제 완료] " + finalAmount + "원 결제되었습니다. (수단: "
        + paymentMethod + ", 포인트사용: " + usedPoint + "점)");
    return p;
  }

  public int applyPoint(int point) {
    return Math.max(0, amount - point);
  }

  public void cancelPayment() {
    this.status = "취소";
    System.out.println("[결제취소] 결제가 취소되었습니다.");
  }

  public Payment get() {
    return this;
  }

  public String getPaymentId() { return paymentId; }
  public String getMemberId() { return memberId; }
  public String getProductType() { return productType; }
  public String getProductId() { return productId; }
  public int getAmount() { return amount; }
  public int getUsedPoint() { return usedPoint; }
  public String getPaymentMethod() { return paymentMethod; }
  public String getPaymentDateTime() { return paymentDateTime; }
  public String getStatus() { return status; }
  public String getPgApprovalNumber() { return pgApprovalNumber; }
  public void setMemberId(String memberId) { this.memberId = memberId; }
  public void setProductType(String productType) { this.productType = productType; }
  public void setProductId(String productId) { this.productId = productId; }
  public void setAmount(int amount) { this.amount = amount; }

  public static List<Payment> getAll() { return payments; }

  @Override
  public String toString() {
    return paymentDateTime + " | " + productType + " | " + amount + "원"
        + " | " + paymentMethod + " | 상태: " + status;
  }
}
