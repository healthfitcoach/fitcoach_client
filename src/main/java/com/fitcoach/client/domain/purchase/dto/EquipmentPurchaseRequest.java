package com.fitcoach.client.domain.purchase.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EquipmentPurchaseRequest {

  @NotNull(message = "상품 ID를 입력해주세요.")
  private Long productId;

  @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
  private int quantity;

  @NotBlank(message = "배송지를 입력해주세요.")
  private String shippingAddress;

  public Long getProductId() { return productId; }
  public int getQuantity() { return quantity; }
  public String getShippingAddress() { return shippingAddress; }
}
