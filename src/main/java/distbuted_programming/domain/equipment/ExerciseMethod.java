package distbuted_programming.domain.equipment;

import java.util.ArrayList;
import java.util.List;

public class ExerciseMethod {

  private String methodId;
  private String equipmentId;
  private String exerciseName;
  private String targetMuscle;
  private String difficulty;
  private String startingPosition;
  private String stepByStep;
  private String image;
  private String videoUrl;

  private static final List<ExerciseMethod> methods = new ArrayList<>();

  public ExerciseMethod() {}

  public ExerciseMethod(String methodId, String equipmentId, String exerciseName,
      String targetMuscle, String difficulty, String startingPosition,
      String stepByStep, String image, String videoUrl) {
    this.methodId = methodId;
    this.equipmentId = equipmentId;
    this.exerciseName = exerciseName;
    this.targetMuscle = targetMuscle;
    this.difficulty = difficulty;
    this.startingPosition = startingPosition;
    this.stepByStep = stepByStep;
    this.image = image;
    this.videoUrl = videoUrl;
  }

  public ExerciseMethod get(String equipmentId) {
    for (ExerciseMethod m : methods) {
      if (m.equipmentId.equals(equipmentId)) {
        return m;
      }
    }
    return null;
  }

  public List<ExerciseMethod> search(String keyword, String muscle) {
    List<ExerciseMethod> result = new ArrayList<>();
    for (ExerciseMethod m : methods) {
      boolean matchKeyword = keyword == null || keyword.isEmpty()
          || m.exerciseName.contains(keyword);
      boolean matchMuscle = muscle == null || muscle.isEmpty()
          || m.targetMuscle.contains(muscle);
      if (matchKeyword && matchMuscle) {
        result.add(m);
      }
    }
    return result;
  }

  public ExerciseMethod getDetail(String methodId) {
    for (ExerciseMethod m : methods) {
      if (m.methodId.equals(methodId)) {
        return m;
      }
    }
    return null;
  }

  public String getMethodId() { return methodId; }
  public String getEquipmentId() { return equipmentId; }
  public String getExerciseName() { return exerciseName; }
  public String getTargetMuscle() { return targetMuscle; }
  public String getDifficulty() { return difficulty; }
  public String getStartingPosition() { return startingPosition; }
  public String getStepByStep() { return stepByStep; }
  public String getImage() { return image; }
  public String getVideoUrl() { return videoUrl; }

  public static List<ExerciseMethod> getAll() { return methods; }
  public static void add(ExerciseMethod m) { methods.add(m); }

  public static List<ExerciseMethod> findByEquipmentId(String equipmentId) {
    List<ExerciseMethod> result = new ArrayList<>();
    for (ExerciseMethod m : methods) {
      if (m.equipmentId.equals(equipmentId)) {
        result.add(m);
      }
    }
    return result;
  }

  @Override
  public String toString() {
    return exerciseName + " | 대상부위: " + targetMuscle + " | 난이도: " + difficulty;
  }
}
