package com.fitcoach.client.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "exercise_program")
@PrimaryKeyJoinColumn(name = "product_id")
@DiscriminatorValue("EXERCISE_PROGRAM")
public class ExerciseProgram extends Product {

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "capacity")
  private int capacity;

  @Column(name = "remaining_capacity")
  private int remainingCapacity;

  @Column(name = "instructor_id")
  private Long instructorId;

  public ExerciseProgram() {}

  public ExerciseProgram(String name, int price, String description,
      ProductStatus status, LocalDate startDate, LocalDate endDate,
      int capacity, int remainingCapacity, Long instructorId) {
    super(name, price, description, status);
    this.startDate = startDate;
    this.endDate = endDate;
    this.capacity = capacity;
    this.remainingCapacity = remainingCapacity;
    this.instructorId = instructorId;
  }

  @Override public void purchase() {}
  @Override public void getDetail() {}
  @Override public void search() {}
  public void cancel() {}

  public LocalDate getStartDate() { return startDate; }
  public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
  public LocalDate getEndDate() { return endDate; }
  public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
  public int getCapacity() { return capacity; }
  public int getRemainingCapacity() { return remainingCapacity; }
  public void setRemainingCapacity(int remainingCapacity) { this.remainingCapacity = remainingCapacity; }
  public Long getInstructorId() { return instructorId; }
}
