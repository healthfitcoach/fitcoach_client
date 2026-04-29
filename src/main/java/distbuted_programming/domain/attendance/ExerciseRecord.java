package distbuted_programming.domain.attendance;

import distbuted_programming.domain.point.Point;
import distbuted_programming.domain.point.PointHistory;
import distbuted_programming.domain.point.PointPolicy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExerciseRecord {

  private String recordId;
  private String memberId;
  private LocalDate exerciseDate;
  private String exerciseType;
  private int exerciseTime;
  private int sets;
  private int reps;
  private String memo;
  private String photo;

  private static final List<ExerciseRecord> records = new ArrayList<>();

  public ExerciseRecord() {}

  public ExerciseRecord(String recordId, String memberId, LocalDate exerciseDate,
      String exerciseType, int exerciseTime, int sets, int reps,
      String memo, String photo) {
    this.recordId = recordId;
    this.memberId = memberId;
    this.exerciseDate = exerciseDate;
    this.exerciseType = exerciseType;
    this.exerciseTime = exerciseTime;
    this.sets = sets;
    this.reps = reps;
    this.memo = memo;
    this.photo = photo;
  }

  public ExerciseRecord record(LocalDate exerciseDate, String exerciseType,
      int exerciseTime, int sets, int reps, String memo, String photo) {
    ExerciseRecord r = new ExerciseRecord(UUID.randomUUID().toString(), this.memberId,
        exerciseDate, exerciseType, exerciseTime, sets, reps, memo, photo);
    records.add(r);
    return r;
  }

  public ExerciseRecord get() {
    return this;
  }

  public void receivePoint() {
    PointPolicy policy = PointPolicy.getInstance();
    if (policy == null) {
      System.out.println("[오류] 포인트 정책을 불러올 수 없습니다.");
      return;
    }
    if (PointHistory.hasTodayHistory(memberId)) {
      System.out.println("[안내] 오늘은 이미 포인트가 적립되었습니다.");
      return;
    }
    int earned = policy.calculatePoint(exerciseTime);
    Point point = Point.getOrCreate(memberId);
    point.accumulate(earned);
    System.out.println("[포인트] " + earned + "점이 적립되었습니다. 누적: " + point.getBalance() + "점");

    int consecutiveDays = countConsecutiveDays(memberId);
    int bonus = policy.checkConsecutiveBonus(consecutiveDays);
    if (bonus > 0) {
      point.accumulate(bonus);
      System.out.println("[보너스] " + consecutiveDays + "일 연속 출석! 보너스 "
          + bonus + "점 추가 적립. 누적: " + point.getBalance() + "점");
    }
  }

  private int countConsecutiveDays(String memberId) {
    List<Attendance> attendances = Attendance.findAllByMemberId(memberId);
    return attendances.size();
  }

  public String getRecordId() { return recordId; }
  public String getMemberId() { return memberId; }
  public LocalDate getExerciseDate() { return exerciseDate; }
  public String getExerciseType() { return exerciseType; }
  public int getExerciseTime() { return exerciseTime; }
  public int getSets() { return sets; }
  public int getReps() { return reps; }
  public String getMemo() { return memo; }
  public String getPhoto() { return photo; }
  public void setMemberId(String memberId) { this.memberId = memberId; }

  public static List<ExerciseRecord> getAll() { return records; }

  public static List<ExerciseRecord> findByMemberId(String memberId) {
    List<ExerciseRecord> result = new ArrayList<>();
    for (ExerciseRecord r : records) {
      if (r.memberId.equals(memberId)) {
        result.add(r);
      }
    }
    return result;
  }

  @Override
  public String toString() {
    return exerciseDate + " | " + exerciseType + " | " + exerciseTime + "분"
        + " | " + sets + "세트 x " + reps + "회";
  }
}
