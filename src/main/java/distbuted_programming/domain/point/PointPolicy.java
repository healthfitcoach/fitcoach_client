package distbuted_programming.domain.point;

public class PointPolicy {

  private String policyId;
  private int baseAccumulationPoint;
  private int exerciseTimeCriteria;
  private int timeBonusPoint;
  private int consecutiveAttendanceBonusPoint;
  private int consecutiveAttendanceCriteriaDays;

  private static PointPolicy instance;

  public PointPolicy() {}

  public PointPolicy(String policyId, int baseAccumulationPoint, int exerciseTimeCriteria,
      int timeBonusPoint, int consecutiveAttendanceBonusPoint,
      int consecutiveAttendanceCriteriaDays) {
    this.policyId = policyId;
    this.baseAccumulationPoint = baseAccumulationPoint;
    this.exerciseTimeCriteria = exerciseTimeCriteria;
    this.timeBonusPoint = timeBonusPoint;
    this.consecutiveAttendanceBonusPoint = consecutiveAttendanceBonusPoint;
    this.consecutiveAttendanceCriteriaDays = consecutiveAttendanceCriteriaDays;
  }

  public PointPolicy get() {
    return instance;
  }

  public int calculatePoint(int exerciseTime) {
    int total = baseAccumulationPoint;
    if (exerciseTime >= exerciseTimeCriteria) {
      total += timeBonusPoint;
    }
    return total;
  }

  public int checkConsecutiveBonus(int consecutiveDays) {
    if (consecutiveDays >= consecutiveAttendanceCriteriaDays) {
      return consecutiveAttendanceBonusPoint;
    }
    return 0;
  }

  public String getPolicyId() { return policyId; }
  public int getBaseAccumulationPoint() { return baseAccumulationPoint; }
  public int getExerciseTimeCriteria() { return exerciseTimeCriteria; }
  public int getTimeBonusPoint() { return timeBonusPoint; }
  public int getConsecutiveAttendanceBonusPoint() { return consecutiveAttendanceBonusPoint; }
  public int getConsecutiveAttendanceCriteriaDays() { return consecutiveAttendanceCriteriaDays; }

  public static void setInstance(PointPolicy policy) { instance = policy; }
  public static PointPolicy getInstance() { return instance; }

  @Override
  public String toString() {
    return "기본적립: " + baseAccumulationPoint + "점 | 운동시간기준: " + exerciseTimeCriteria
        + "분 | 시간보너스: " + timeBonusPoint + "점 | 연속출석보너스: "
        + consecutiveAttendanceBonusPoint + "점 (" + consecutiveAttendanceCriteriaDays + "일)";
  }
}
