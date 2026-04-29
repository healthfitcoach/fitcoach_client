package distbuted_programming.domain.product;

import java.util.ArrayList;
import java.util.List;

public class AdditionalProduct extends Product {

  private String additionalProductId;
  private String memberId;
  private String name;
  private int price;
  private int usagePeriod;
  private String status;

  private static final List<AdditionalProduct> additionalProducts = new ArrayList<>();

  public AdditionalProduct() {}

  public AdditionalProduct(String additionalProductId, String memberId, String name,
      int price, int usagePeriod, String status) {
    super(additionalProductId, name, price, name + " 부가상품", "부가상품");
    this.additionalProductId = additionalProductId;
    this.memberId = memberId;
    this.name = name;
    this.price = price;
    this.usagePeriod = usagePeriod;
    this.status = status;
  }

  @Override
  public void purchase() {
    System.out.println("[구매] " + name + " 부가상품 구매가 완료되었습니다.");
  }

  @Override
  public AdditionalProduct getDetail() {
    return this;
  }

  @Override
  public AdditionalProduct get() {
    return this;
  }

  public String getAdditionalProductId() { return additionalProductId; }
  public String getMemberId() { return memberId; }
  public String getName() { return name; }
  public int getPrice() { return price; }
  public int getUsagePeriod() { return usagePeriod; }
  public String getStatus() { return status; }
  public void setMemberId(String memberId) { this.memberId = memberId; }
  public void setStatus(String status) { this.status = status; }

  public static List<AdditionalProduct> getAll() { return additionalProducts; }
  public static void add(AdditionalProduct p) { additionalProducts.add(p); }

  public static List<AdditionalProduct> findByMemberId(String memberId) {
    List<AdditionalProduct> result = new ArrayList<>();
    for (AdditionalProduct p : additionalProducts) {
      if (p.memberId.equals(memberId)) {
        result.add(p);
      }
    }
    return result;
  }

  @Override
  public String toString() {
    return name + " | " + price + "원 | 이용기간: " + usagePeriod + "일 | 상태: " + status;
  }
}
