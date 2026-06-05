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
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PurchaseRestController implements PurchaseControllerDocs {

  private final PurchaseService purchaseService;

  public PurchaseRestController(PurchaseService purchaseService) {
    this.purchaseService = purchaseService;
  }

  @GetMapping("/products/memberships")
  public ResponseEntity<ApiResponse<List<MembershipProduct>>> getMembershipCatalog() {
    return ResponseEntity.ok(ApiResponse.ok(purchaseService.getMembershipCatalog()));
  }

  @GetMapping("/products/programs")
  public ResponseEntity<ApiResponse<List<ExerciseProgram>>> getProgramCatalog() {
    return ResponseEntity.ok(ApiResponse.ok(purchaseService.getProgramCatalog()));
  }

  @GetMapping("/products/sport-equipment")
  public ResponseEntity<ApiResponse<List<SportEquipment>>> getSportEquipment(
      @RequestParam(required = false) String keyword) {
    List<SportEquipment> result = (keyword != null && !keyword.isBlank())
        ? purchaseService.searchSportEquipment(keyword)
        : purchaseService.getSportEquipmentCatalog();
    return ResponseEntity.ok(ApiResponse.ok(result));
  }

  @GetMapping("/products/pt")
  public ResponseEntity<ApiResponse<List<PTProduct>>> getPtCatalog() {
    return ResponseEntity.ok(ApiResponse.ok(purchaseService.getPtCatalog()));
  }

  @GetMapping("/products/additional")
  public ResponseEntity<ApiResponse<List<AdditionalProduct>>> getAdditionalProducts() {
    return ResponseEntity.ok(ApiResponse.ok(purchaseService.getAdditionalProductCatalog()));
  }

  @GetMapping("/trainers")
  public ResponseEntity<ApiResponse<List<Trainer>>> getTrainers(
      @RequestParam(required = false) String keyword) {
    List<Trainer> result = (keyword != null && !keyword.isBlank())
        ? purchaseService.getAllTrainers().stream()
            .filter(t -> t.getSpecialty().contains(keyword)).toList()
        : purchaseService.getAllTrainers();
    return ResponseEntity.ok(ApiResponse.ok(result));
  }

  @PostMapping("/purchases/membership")
  public ResponseEntity<ApiResponse<Membership>> purchaseMembership(
      @AuthenticationPrincipal Long memberId,
      @Valid @RequestBody MembershipPurchaseRequest request) {
    return ResponseEntity.ok(ApiResponse.ok(purchaseService.createMembership(
        memberId, request.getProductId(), request.getStartDate(), request.getEndDate())));
  }

  @PostMapping("/purchases/sport-equipment")
  public ResponseEntity<ApiResponse<Order>> purchaseEquipment(
      @AuthenticationPrincipal Long memberId,
      @Valid @RequestBody EquipmentPurchaseRequest request) {
    return ResponseEntity.ok(ApiResponse.ok(purchaseService.createEquipmentOrder(
        memberId, request.getProductId(), request.getQuantity(), request.getShippingAddress())));
  }

  @PostMapping("/purchases/pt")
  public ResponseEntity<ApiResponse<PTSubscription>> purchasePT(
      @AuthenticationPrincipal Long memberId,
      @Valid @RequestBody PTPurchaseRequest request) {
    PTSubscription pt = purchaseService.createMemberPT(
        memberId, request.getProductId(), request.getTrainerId());
    purchaseService.addFirstPTSchedule(
        pt.getId(), memberId, request.getTrainerId(),
        request.getFirstDate(), request.getFirstTime());
    return ResponseEntity.ok(ApiResponse.ok(pt));
  }

  @PostMapping("/purchases/additional/{productId}")
  public ResponseEntity<ApiResponse<Order>> purchaseAdditional(
      @AuthenticationPrincipal Long memberId,
      @PathVariable Long productId) {
    return ResponseEntity.ok(ApiResponse.ok(
        purchaseService.createAdditionalProductOrder(memberId, productId)));
  }

  @PostMapping("/payments")
  public ResponseEntity<ApiResponse<Payment>> processPayment(
      @AuthenticationPrincipal Long memberId,
      @Valid @RequestBody PaymentRequest request) {
    return ResponseEntity.ok(ApiResponse.ok(purchaseService.processPayment(
        memberId, request.getAmount(), request.getProductId(),
        request.getProductType(), request.getPaymentMethod(), request.getUsedPoints())));
  }
}
