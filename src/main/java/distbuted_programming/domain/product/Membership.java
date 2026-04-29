package distbuted_programming.domain.product;

import distbuted_programming.domain.attendance.Attendance;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Membership extends Product {

  private String membershipId;
  private String memberId;
  private String type;
  private LocalDate startDate;
  private LocalDate endDate;
  private int price;
  private String status;
  private LocalDate pauseDate;
  private LocalDate resumeDate;

  private static final List<Membership> memberships = new ArrayList<>();

  public Membership() {}

  public Membership(String membershipId, String memberId, String type,
      LocalDate startDate, LocalDate endDate, int price, String status) {
    super(membershipId, type + " 회원권", price, type + " 이용 회원권", "회원권");
    this.membershipId = membershipId;
    this.memberId = memberId;
    this.type = type;
    this.startDate = startDate;
    this.endDate = endDate;
    this.price = price;
    this.status = status;
  }

  @Override
  public Membership get() {
    return this;
  }

  public int getRemainingDays() {
    if (endDate == null) return 0;
    long days = ChronoUnit.DAYS.between(LocalDate.now(), endDate);
    return days > 0 ? (int) days : 0;
  }

  public void applyRenewal(int period) {
    if (endDate != null) {
      endDate = endDate.plusDays(period);
    }
    System.out.println("[갱신] " + period + "일 연장되었습니다. 새 종료일: " + endDate);
  }

  public Attendance getUsageHistory() {
    return Attendance.findLatestByMemberId(memberId);
  }

  public void refund() {
    this.status = "환불";
    System.out.println("[환불] 회원권 환불이 처리되었습니다.");
  }

  public String getMembershipId() { return membershipId; }
  public String getMemberId() { return memberId; }
  public String getType() { return type; }
  public LocalDate getStartDate() { return startDate; }
  public LocalDate getEndDate() { return endDate; }
  public int getPrice() { return price; }
  public String getStatus() { return status; }
  public LocalDate getPauseDate() { return pauseDate; }
  public LocalDate getResumeDate() { return resumeDate; }

  public void setStatus(String status) { this.status = status; }
  public void setPauseDate(LocalDate pauseDate) { this.pauseDate = pauseDate; }
  public void setResumeDate(LocalDate resumeDate) { this.resumeDate = resumeDate; }

  public static List<Membership> getAll() { return memberships; }
  public static void add(Membership m) { memberships.add(m); }

  public static Membership findActiveByMemberId(String memberId) {
    for (Membership m : memberships) {
      if (m.memberId.equals(memberId) && "활성".equals(m.status)) {
        return m;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return type + " 회원권 | " + price + "원 | " + startDate + " ~ " + endDate
        + " | 잔여: " + getRemainingDays() + "일 | 상태: " + status;
  }
}
