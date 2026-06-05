package com.fitcoach.client.domain.purchase;

import com.fitcoach.client.domain.purchase.dto.EquipmentPurchaseRequest;
import com.fitcoach.client.domain.purchase.dto.MembershipPurchaseRequest;
import com.fitcoach.client.domain.purchase.dto.PTPurchaseRequest;
import com.fitcoach.client.domain.purchase.dto.PaymentRequest;
import com.fitcoach.client.global.response.ApiResponse;
import com.fitcoach.client.model.order.Order;
import com.fitcoach.client.model.order.Payment;
import com.fitcoach.client.model.product.AdditionalProduct;
import com.fitcoach.client.model.product.ExerciseProgram;
import com.fitcoach.client.model.product.Membership;
import com.fitcoach.client.model.product.MembershipProduct;
import com.fitcoach.client.model.product.PTProduct;
import com.fitcoach.client.model.product.PTSubscription;
import com.fitcoach.client.model.product.SportEquipment;
import com.fitcoach.client.model.schedule.Trainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Purchase", description = "상품 조회 및 구매 API")
public interface PurchaseControllerDocs {

  @Operation(summary = "회원권 상품 목록")
  ResponseEntity<ApiResponse<List<MembershipProduct>>> getMembershipCatalog();

  @Operation(summary = "프로그램 상품 목록")
  ResponseEntity<ApiResponse<List<ExerciseProgram>>> getProgramCatalog();

  @Operation(summary = "운동용품 목록 / 검색")
  ResponseEntity<ApiResponse<List<SportEquipment>>> getSportEquipment(@RequestParam(required = false) String keyword);

  @Operation(summary = "PT 상품 목록")
  ResponseEntity<ApiResponse<List<PTProduct>>> getPtCatalog();

  @Operation(summary = "부가상품 목록")
  ResponseEntity<ApiResponse<List<AdditionalProduct>>> getAdditionalProducts();

  @Operation(summary = "트레이너 목록 / 검색")
  ResponseEntity<ApiResponse<List<Trainer>>> getTrainers(@RequestParam(required = false) String keyword);

  @Operation(summary = "회원권 구매")
  ResponseEntity<ApiResponse<Membership>> purchaseMembership(
      @AuthenticationPrincipal Long memberId, @RequestBody MembershipPurchaseRequest request);

  @Operation(summary = "운동용품 구매")
  ResponseEntity<ApiResponse<Order>> purchaseEquipment(
      @AuthenticationPrincipal Long memberId, @RequestBody EquipmentPurchaseRequest request);

  @Operation(summary = "PT 구매")
  ResponseEntity<ApiResponse<PTSubscription>> purchasePT(
      @AuthenticationPrincipal Long memberId, @RequestBody PTPurchaseRequest request);

  @Operation(summary = "부가상품 구매")
  ResponseEntity<ApiResponse<Order>> purchaseAdditional(
      @AuthenticationPrincipal Long memberId, @PathVariable Long productId);

  @Operation(summary = "결제 처리")
  ResponseEntity<ApiResponse<Payment>> processPayment(
      @AuthenticationPrincipal Long memberId, @RequestBody PaymentRequest request);
}
