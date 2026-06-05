package com.fitcoach.client.domain.pt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class PTScheduleRequest {

  @NotNull(message = "PT 이용권 ID를 입력해주세요.")
  private Long ptId;

  @NotNull(message = "트레이너 ID를 입력해주세요.")
  private Long trainerId;

  @NotNull(message = "날짜를 입력해주세요.")
  private LocalDate date;

  @NotBlank(message = "시간을 입력해주세요.")
  private String time;

  public Long getPtId() { return ptId; }
  public Long getTrainerId() { return trainerId; }
  public LocalDate getDate() { return date; }
  public String getTime() { return time; }
}
