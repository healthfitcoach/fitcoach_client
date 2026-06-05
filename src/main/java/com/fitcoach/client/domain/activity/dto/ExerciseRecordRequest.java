package com.fitcoach.client.domain.activity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ExerciseRecordRequest {

  @NotNull(message = "운동 날짜를 입력해주세요.")
  private LocalDate date;

  @NotBlank(message = "운동 종류를 입력해주세요.")
  private String exerciseType;

  @Min(value = 1, message = "운동 시간은 1분 이상이어야 합니다.")
  private int exerciseTime;

  private int sets;
  private int reps;
  private String memo;
  private String photo;

  public LocalDate getDate() { return date; }
  public String getExerciseType() { return exerciseType; }
  public int getExerciseTime() { return exerciseTime; }
  public int getSets() { return sets; }
  public int getReps() { return reps; }
  public String getMemo() { return memo; }
  public String getPhoto() { return photo; }
}
