import java.time.LocalDate;
import java.time.LocalTime;

public class PTSchedule {

    private String scheduleId;
    private String ptId;
    private String memberId;
    private String trainerId;
    private LocalDate date;
    private LocalTime time;
    private String status;

    public PTSchedule(String scheduleId, String ptId, String memberId, String trainerId,
                      LocalDate date, LocalTime time, String status) {
        this.scheduleId = scheduleId;
        this.ptId = ptId;
        this.memberId = memberId;
        this.trainerId = trainerId;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public boolean init() {
        return true;
    }

    public void reserve() {}

    public void search() {}

    public void cancel() {}

    // Getters & Setters
    public String getScheduleId() { return scheduleId; }
    public String getPtId() { return ptId; }
    public String getMemberId() { return memberId; }
    public String getTrainerId() { return trainerId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
