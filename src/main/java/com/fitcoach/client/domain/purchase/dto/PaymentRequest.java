package com.fitcoach.client.domain.purchase.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PaymentRequest {

  @NotNull(message = "금액을 입력해주세요.")
  private Integer amount;

  @NotBlank(message = "상품 ID를 입력해주세요.")
  private String productId;

  @NotBlank(message = "상품 유형을 입력해주세요.")
  private String productType;

  @NotBlank(message = "결제 수단을 입력해주세요.")
  private String paymentMethod;

  @Min(0)
  private int usedPoints;

  public Integer getAmount() { return amount; }
  public String getProductId() { return productId; }
  public String getProductType() { return productType; }
  public String getPaymentMethod() { return paymentMethod; }
  public int getUsedPoints() { return usedPoints; }
}
