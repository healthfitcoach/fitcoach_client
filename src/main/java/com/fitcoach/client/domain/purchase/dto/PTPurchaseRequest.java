package com.fitcoach.client.domain.purchase.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class PTPurchaseRequest {

  @NotNull(message = "상품 ID를 입력해주세요.")
  private Long productId;

  @NotNull(message = "트레이너 ID를 입력해주세요.")
  private Long trainerId;

  @NotNull(message = "첫 일정 날짜를 입력해주세요.")
  private LocalDate firstDate;

  @NotNull(message = "첫 일정 시간을 입력해주세요.")
  private String firstTime;

  public Long getProductId() { return productId; }
  public Long getTrainerId() { return trainerId; }
  public LocalDate getFirstDate() { return firstDate; }
  public String getFirstTime() { return firstTime; }
}
