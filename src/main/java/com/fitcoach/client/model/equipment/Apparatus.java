package com.fitcoach.client.model.equipment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "apparatus")
public class Apparatus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "category")
  private String category;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private ApparatusStatus status;

  @Column(name = "quantity")
  private int quantity;

  @Column(name = "manufacturer")
  private String manufacturer;

  @Column(name = "purchase_date")
  private LocalDate purchaseDate;

  @Column(name = "last_inspection_date")
  private LocalDate lastInspectionDate;

  @Transient
  private List<ExerciseMethod> exerciseMethods;

  public enum ApparatusStatus {
    ACTIVE, INACTIVE
  }

  public Apparatus() {
    this.exerciseMethods = new ArrayList<>();
  }

  public Apparatus(String name, String category, ApparatusStatus status, int quantity,
      String manufacturer, LocalDate purchaseDate, LocalDate lastInspectionDate) {
    this.name = name;
    this.category = category;
    this.status = status;
    this.quantity = quantity;
    this.manufacturer = manufacturer;
    this.purchaseDate = purchaseDate;
    this.lastInspectionDate = lastInspectionDate;
    this.exerciseMethods = new ArrayList<>();
  }

  public Long getId() { return id; }
  public String getName() { return name; }
  public String getCategory() { return category; }
  public ApparatusStatus getStatus() { return status; }
  public int getQuantity() { return quantity; }
  public String getManufacturer() { return manufacturer; }
  public LocalDate getPurchaseDate() { return purchaseDate; }
  public LocalDate getLastInspectionDate() { return lastInspectionDate; }
  public List<ExerciseMethod> getExerciseMethods() { return exerciseMethods; }
  public void addExerciseMethod(ExerciseMethod method) { this.exerciseMethods.add(method); }
}
