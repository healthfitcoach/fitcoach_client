package distbuted_programming.domain.point;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Point {

  private String pointId;
  private String memberId;
  private int balance;
  private LocalDate expiryDate;

  private static final List<Point> points = new ArrayList<>();
  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public Point() {}

  public Point(String pointId, String memberId, int balance, LocalDate expiryDate) {
    this.pointId = pointId;
    this.memberId = memberId;
    this.balance = balance;
    this.expiryDate = expiryDate;
  }

  public Point get() {
    return this;
  }

  public void accumulate(int amount) {
    this.balance += amount;
    String now = LocalDateTime.now().format(FORMATTER);
    PointHistory history = new PointHistory(
        UUID.randomUUID().toString(), memberId, now, "적립", "포인트 적립",
        amount, balance);
    PointHistory.add(history);
  }

  public void use(int amount) {
    if (amount > balance) {
      System.out.println("[오류] 보유 포인트가 부족합니다. 보유: " + balance + "점");
      return;
    }
    this.balance -= amount;
    String now = LocalDateTime.now().format(FORMATTER);
    PointHistory history = new PointHistory(
        UUID.randomUUID().toString(), memberId, now, "사용", "포인트 사용",
        -amount, balance);
    PointHistory.add(history);
  }

  public String getPointId() { return pointId; }
  public String getMemberId() { return memberId; }
  public int getBalance() { return balance; }
  public LocalDate getExpiryDate() { return expiryDate; }

  public static List<Point> getAll() { return points; }
  public static void add(Point p) { points.add(p); }

  public static Point findByMemberId(String memberId) {
    for (Point p : points) {
      if (p.memberId.equals(memberId)) {
        return p;
      }
    }
    return null;
  }

  public static Point getOrCreate(String memberId) {
    Point p = findByMemberId(memberId);
    if (p == null) {
      p = new Point(UUID.randomUUID().toString(), memberId, 0,
          LocalDate.now().plusYears(1));
      points.add(p);
    }
    return p;
  }

  @Override
  public String toString() {
    return "포인트 잔액: " + balance + "점 | 유효기간: " + expiryDate;
  }
}
