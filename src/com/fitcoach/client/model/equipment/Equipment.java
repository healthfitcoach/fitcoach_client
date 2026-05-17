package com.fitcoach.client.model.equipment;

import java.util.ArrayList;
import java.util.List;

public class Equipment {

    private String equipmentId;
    private String name;
    private String description;
    private String category;
    private String status;
    private List<ExerciseMethod> exerciseMethods;

    public Equipment(String equipmentId, String name, String description,
                     String category, String status) {
        this.equipmentId = equipmentId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.status = status;
        this.exerciseMethods = new ArrayList<>();
    }

    public boolean init() {
        return true;
    }

    public void search() {}

    public void listAll() {}

    public void getDetail() {}

    // Getters & Setters
    public String getEquipmentId() { return equipmentId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<ExerciseMethod> getExerciseMethods() { return exerciseMethods; }
    public void addExerciseMethod(ExerciseMethod method) { this.exerciseMethods.add(method); }
}
