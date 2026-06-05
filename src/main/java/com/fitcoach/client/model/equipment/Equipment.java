package com.fitcoach.client.model.equipment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipment")
public class Equipment {

  @Id
  @Column(name = "equipment_id")
  private String equipmentId;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "category")
  private String category;

  @Column(name = "status")
  private String status;

  @Transient  // Aggregation 필드 — DB 컬럼 아님
  private List<ExerciseMethod> exerciseMethods;

  public Equipment() {  // JPA 필수 no-arg 생성자
    this.exerciseMethods = new ArrayList<>();
  }

  public Equipment(String equipmentId, String name, String description,
      String category, String status) {
    this.equipmentId = equipmentId;
    this.name = name;
    this.description = description;
    this.category = category;
    this.status = status;
    this.exerciseMethods = new ArrayList<>();
  }

  public boolean init() { return true; }

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
