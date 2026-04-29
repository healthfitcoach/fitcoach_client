package distbuted_programming.domain.product;

import distbuted_programming.domain.trainer.PTSchedule;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PT extends Product {

  private String ptId;
  private String memberId;
  private String trainerId;
  private int count;
  private int remainingCount;
  private int price;
  private String status;

  private static final List<PT> ptList = new ArrayList<>();

  public PT() {}

  public PT(String ptId, String memberId, String trainerId, int count,
      int remainingCount, int price, String status) {
    super(ptId, count + "회 PT", price, count + "회 PT 이용권", "PT");
    this.ptId = ptId;
    this.memberId = memberId;
    this.trainerId = trainerId;
    this.count = count;
    this.remainingCount = remainingCount;
    this.price = price;
    this.status = status;
  }

  @Override
  public PT get() {
    return this;
  }

  public PT purchase(String trainerId, int count) {
    int unitPrice = count <= 10 ? 50000 : count <= 20 ? 45000 : 40000;
    PT pt = new PT(UUID.randomUUID().toString(), this.memberId, trainerId,
        count, count, count * unitPrice, "활성");
    ptList.add(pt);
    System.out.println("[구매] " + count + "회 PT 이용권 구매가 완료되었습니다.");
    return pt;
  }

  public void cancel() {
    this.status = "취소";
    System.out.println("[취소] PT 이용권이 취소되었습니다.");
  }

  public PT getStatus() {
    System.out.println("PT 상태: " + status + " | 잔여횟수: " + remainingCount + "/" + count);
    return this;
  }

  public void useOneSession() {
    if (remainingCount > 0) {
      remainingCount--;
    }
  }

  public String getPtId() { return ptId; }
  public String getMemberId() { return memberId; }
  public String getTrainerId() { return trainerId; }
  public int getCount() { return count; }
  public int getRemainingCount() { return remainingCount; }
  public int getPrice() { return price; }
  public String getStatus2() { return status; }
  public void setMemberId(String memberId) { this.memberId = memberId; }
  public void setStatus(String status) { this.status = status; }

  public static List<PT> getAll() { return ptList; }
  public static void add(PT pt) { ptList.add(pt); }

  public static PT findActiveByMemberId(String memberId) {
    for (PT pt : ptList) {
      if (pt.memberId.equals(memberId) && "활성".equals(pt.status)
          && pt.remainingCount > 0) {
        return pt;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return count + "회 PT | " + price + "원 | 잔여: " + remainingCount + "회 | 상태: " + status;
  }
}
