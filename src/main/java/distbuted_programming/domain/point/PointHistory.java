package distbuted_programming.domain.point;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PointHistory {

  private String historyId;
  private String memberId;
  private String date;
  private String type;
  private String reason;
  private int changeAmount;
  private int remainingPoint;

  private static final List<PointHistory> histories = new ArrayList<>();

  public PointHistory() {}

  public PointHistory(String historyId, String memberId, String date,
      String type, String reason, int changeAmount, int remainingPoint) {
    this.historyId = historyId;
    this.memberId = memberId;
    this.date = date;
    this.type = type;
    this.reason = reason;
    this.changeAmount = changeAmount;
    this.remainingPoint = remainingPoint;
  }

  public PointHistory get() {
    return this;
  }

  public List<PointHistory> getByPeriod(LocalDate startDate, LocalDate endDate) {
    List<PointHistory> result = new ArrayList<>();
    for (PointHistory h : histories) {
      if (h.memberId.equals(this.memberId)) {
        LocalDate hDate = LocalDate.parse(h.date.substring(0, 10));
        if (!hDate.isBefore(startDate) && !hDate.isAfter(endDate)) {
          result.add(h);
        }
      }
    }
    return result;
  }

  public PointHistory getDetail(String historyId) {
    for (PointHistory h : histories) {
      if (h.historyId.equals(historyId)) {
        return h;
      }
    }
    return null;
  }

  public String getHistoryId() { return historyId; }
  public String getMemberId() { return memberId; }
  public String getDate() { return date; }
  public String getType() { return type; }
  public String getReason() { return reason; }
  public int getChangeAmount() { return changeAmount; }
  public int getRemainingPoint() { return remainingPoint; }

  public static List<PointHistory> getAll() { return histories; }
  public static void add(PointHistory h) { histories.add(h); }

  public static List<PointHistory> findByMemberId(String memberId) {
    List<PointHistory> result = new ArrayList<>();
    for (PointHistory h : histories) {
      if (h.memberId.equals(memberId)) {
        result.add(h);
      }
    }
    return result;
  }

  public static boolean hasTodayHistory(String memberId) {
    String today = LocalDate.now().toString();
    for (PointHistory h : histories) {
      if (h.memberId.equals(memberId) && h.date.startsWith(today)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return date + " | " + type + " | " + reason
        + " | " + (changeAmount > 0 ? "+" : "") + changeAmount
        + "점 | 잔여: " + remainingPoint + "점";
  }
}
