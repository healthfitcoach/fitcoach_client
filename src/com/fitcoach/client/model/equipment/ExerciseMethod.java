package com.fitcoach.client.model.equipment;

public class ExerciseMethod {

    private String methodId;
    private String equipmentId;
    private String exerciseName;
    private String targetBodyPart;
    private String difficulty;
    private String preparationPose;
    private String stepByStepMethod;
    private String image;
    private String videoUrl;

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

    public boolean init() {
        return true;
    }

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
