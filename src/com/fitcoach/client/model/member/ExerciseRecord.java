package com.fitcoach.client.model.member;

import java.time.LocalDate;

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

    public boolean init() {
        return true;
    }

    public void recordExercise() {}

    public void search() {}

    public void receivePoints() {}

    // Getters & Setters
    public String getRecordId() { return recordId; }
    public String getMemberId() { return memberId; }
    public LocalDate getExerciseDate() { return exerciseDate; }
    public void setExerciseDate(LocalDate exerciseDate) { this.exerciseDate = exerciseDate; }
    public String getExerciseType() { return exerciseType; }
    public void setExerciseType(String exerciseType) { this.exerciseType = exerciseType; }
    public int getExerciseTime() { return exerciseTime; }
    public void setExerciseTime(int exerciseTime) { this.exerciseTime = exerciseTime; }
    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }
    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }
    public String getMemo() { return memo; }
    public void setMemo(String memo) { this.memo = memo; }
    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
}
