package distbuted_programming.domain.trainer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PTSchedule {

  private String scheduleId;
  private String ptId;
  private String trainerId;
  private String memberId;
  private LocalDate date;
  private String time;
  private String status;

  private static final List<PTSchedule> schedules = new ArrayList<>();

  public PTSchedule() {}

  public PTSchedule(String scheduleId, String ptId, String trainerId,
      String memberId, LocalDate date, String time, String status) {
    this.scheduleId = scheduleId;
    this.ptId = ptId;
    this.trainerId = trainerId;
    this.memberId = memberId;
    this.date = date;
    this.time = time;
    this.status = status;
  }

  public PTSchedule get() {
    return this;
  }

  public PTSchedule reserve(LocalDate date, String time) {
    PTSchedule s = new PTSchedule(UUID.randomUUID().toString(), this.ptId,
        this.trainerId, this.memberId, date, time, "예약");
    schedules.add(s);
    System.out.println("[예약 완료] " + date + " " + time + " PT 일정이 예약되었습니다.");
    return s;
  }

  public void cancel() {
    this.status = "취소";
    System.out.println("[취소] PT 일정이 취소되었습니다.");
  }

  public String getScheduleId() { return scheduleId; }
  public String getPtId() { return ptId; }
  public String getTrainerId() { return trainerId; }
  public String getMemberId() { return memberId; }
  public LocalDate getDate() { return date; }
  public String getTime() { return time; }
  public String getStatus() { return status; }
  public void setPtId(String ptId) { this.ptId = ptId; }
  public void setTrainerId(String trainerId) { this.trainerId = trainerId; }
  public void setMemberId(String memberId) { this.memberId = memberId; }

  public static List<PTSchedule> getAll() { return schedules; }
  public static void add(PTSchedule s) { schedules.add(s); }

  public static PTSchedule findByTrainerId(String trainerId) {
    for (PTSchedule s : schedules) {
      if (s.trainerId.equals(trainerId)) {
        return s;
      }
    }
    return null;
  }

  public static List<PTSchedule> findAllByTrainerId(String trainerId) {
    List<PTSchedule> result = new ArrayList<>();
    for (PTSchedule s : schedules) {
      if (s.trainerId.equals(trainerId)) {
        result.add(s);
      }
    }
    return result;
  }

  public static boolean isSlotTaken(String trainerId, LocalDate date, String time) {
    for (PTSchedule s : schedules) {
      if (s.trainerId.equals(trainerId) && s.date.equals(date)
          && s.time.equals(time) && !"취소".equals(s.status)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return "날짜: " + date + " " + time + " | 상태: " + status;
  }
}
