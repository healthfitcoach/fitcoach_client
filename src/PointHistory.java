import java.time.LocalDate;
import java.util.List;

public class PointHistory {

    private String historyId;
    private String memberId;
    private String type;
    private int amount;
    private String reason;
    private LocalDate date;
    private int balanceAfter;

    public PointHistory(String historyId, String memberId, String type, int amount,
                        String reason, LocalDate date, int balanceAfter) {
        this.historyId = historyId;
        this.memberId = memberId;
        this.type = type;
        this.amount = amount;
        this.reason = reason;
        this.date = date;
        this.balanceAfter = balanceAfter;
    }

    public boolean init() {
        return true;
    }

    public void listByPeriod(LocalDate from, LocalDate to) {}

    public void getDetail() {}

    public void search() {}

    // Getters
    public String getHistoryId() { return historyId; }
    public String getMemberId() { return memberId; }
    public String getType() { return type; }
    public int getAmount() { return amount; }
    public String getReason() { return reason; }
    public LocalDate getDate() { return date; }
    public int getBalanceAfter() { return balanceAfter; }
}
