package com.fitcoach.client.domain.purchase.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class MembershipPurchaseRequest {

  @NotNull(message = "상품 ID를 입력해주세요.")
  private Long productId;

  @NotNull(message = "시작일을 입력해주세요.")
  private LocalDate startDate;

  @NotNull(message = "종료일을 입력해주세요.")
  private LocalDate endDate;

  public Long getProductId() { return productId; }
  public LocalDate getStartDate() { return startDate; }
  public LocalDate getEndDate() { return endDate; }
}
