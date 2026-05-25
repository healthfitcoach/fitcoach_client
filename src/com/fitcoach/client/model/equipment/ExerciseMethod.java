package com.fitcoach.client.model.equipment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "exercise_method")
public class ExerciseMethod {

  @Id
  @Column(name = "method_id")
  private String methodId;

  @Column(name = "equipment_id")
  private String equipmentId;

  @Column(name = "exercise_name")
  private String exerciseName;

  @Column(name = "target_body_part")
  private String targetBodyPart;

  @Column(name = "difficulty")
  private String difficulty;

  @Column(name = "preparation_pose")
  private String preparationPose;

  @Column(name = "step_by_step_method")
  private String stepByStepMethod;

  @Column(name = "image")
  private String image;

  @Column(name = "video_url")
  private String videoUrl;

  public ExerciseMethod() {}  // JPA 필수 no-arg 생성자

  public ExerciseMethod(String methodId, String equipmentId, String exerciseName,
      String targetBodyPart, String difficulty, String preparationPose,
      String stepByStepMethod, String image, String videoUrl) {
    this.methodId = methodId;
    this.equipmentId = equipmentId;
    this.exerciseName = exerciseName;
    this.targetBodyPart = targetBodyPart;
    this.difficulty = difficulty;
    this.preparationPose = preparationPose;
    this.stepByStepMethod = stepByStepMethod;
    this.image = image;
    this.videoUrl = videoUrl;
  }

  public boolean init() { return true; }

  public void search() {}

  public void getDetail() {}

  public void searchList() {}

  // Getters
  public String getMethodId() { return methodId; }
  public String getEquipmentId() { return equipmentId; }
  public String getExerciseName() { return exerciseName; }
  public String getTargetBodyPart() { return targetBodyPart; }
  public String getDifficulty() { return difficulty; }
  public String getPreparationPose() { return preparationPose; }
  public String getStepByStepMethod() { return stepByStepMethod; }
  public String getImage() { return image; }
  public String getVideoUrl() { return videoUrl; }
}
