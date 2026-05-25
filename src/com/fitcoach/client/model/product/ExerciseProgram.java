package com.fitcoach.client.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;

@Entity
@Table(name = "exercise_program")
public class ExerciseProgram extends Product {

  @Column(name = "program_id")
  private String programId;

  @Column(name = "instructor_id")
  private String instructorId;

  @Column(name = "status")
  private String status;

  @Transient  // DB 컬럼 없음
  private LocalDate startDate;

  @Transient  // DB 컬럼 없음
  private LocalDate endDate;

  @Column(name = "capacity")
  private int capacity;

  @Column(name = "remaining_capacity")
  private int remainingCapacity;

  public ExerciseProgram() {}  // JPA 필수 no-arg 생성자

  public ExerciseProgram(String productId, String productName, int price, String description,
      String programId, String instructorId, String status,
      int capacity, int remainingCapacity) {
    super(productId, productName, price, description, "PROGRAM");
    this.programId = programId;
    this.instructorId = instructorId;
    this.status = status;
    this.startDate = null;
    this.endDate = null;
    this.capacity = capacity;
    this.remainingCapacity = remainingCapacity;
  }

  @Override
  public boolean init() { return true; }

  @Override
  public void purchase() {}

  @Override
  public void getDetail() {}

  @Override
  public void search() {}

  public void cancel() {}

  // Getters & Setters
  public String getProgramId() { return programId; }
  public String getInstructorId() { return instructorId; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public LocalDate getStartDate() { return startDate; }
  public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
  public LocalDate getEndDate() { return endDate; }
  public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
  public int getCapacity() { return capacity; }
  public int getRemainingCapacity() { return remainingCapacity; }
  public void setRemainingCapacity(int remainingCapacity) { this.remainingCapacity = remainingCapacity; }
}
