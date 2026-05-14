public class PointPolicy {

    private String policyId;
    private int basePoints;
    private int timeBonusPoints;
    private int exerciseTimeStandard;
    private int consecutiveAttendanceDays;
    private int consecutiveAttendanceBonus;

    public PointPolicy(String policyId, int basePoints, int timeBonusPoints,
                       int exerciseTimeStandard, int consecutiveAttendanceDays,
                       int consecutiveAttendanceBonus) {
        this.policyId = policyId;
        this.basePoints = basePoints;
        this.timeBonusPoints = timeBonusPoints;
        this.exerciseTimeStandard = exerciseTimeStandard;
        this.consecutiveAttendanceDays = consecutiveAttendanceDays;
        this.consecutiveAttendanceBonus = consecutiveAttendanceBonus;
    }

    public boolean init() {
        return true;
    }

    public int calculatePoints(int exerciseTime) { return 0; }

    public boolean checkConsecutiveAttendanceBonus(String memberId) { return false; }

    public void search() {}

    // Getters
    public String getPolicyId() { return policyId; }
    public int getBasePoints() { return basePoints; }
    public int getTimeBonusPoints() { return timeBonusPoints; }
    public int getExerciseTimeStandard() { return exerciseTimeStandard; }
    public int getConsecutiveAttendanceDays() { return consecutiveAttendanceDays; }
    public int getConsecutiveAttendanceBonus() { return consecutiveAttendanceBonus; }
}
