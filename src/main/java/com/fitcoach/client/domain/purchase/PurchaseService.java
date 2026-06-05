package com.fitcoach.client.domain.purchase;

import com.fitcoach.client.domain.activity.repository.PointHistoryRepository;
import com.fitcoach.client.domain.activity.repository.PointRepository;
import com.fitcoach.client.domain.purchase.repository.AdditionalProductRepository;
import com.fitcoach.client.domain.purchase.repository.ExerciseProgramRepository;
import com.fitcoach.client.domain.purchase.repository.MembershipProductRepository;
import com.fitcoach.client.domain.purchase.repository.MembershipRepository;
import com.fitcoach.client.domain.purchase.repository.OrderRepository;
import com.fitcoach.client.domain.purchase.repository.PTProductRepository;
import com.fitcoach.client.domain.pt.repository.PTScheduleRepository;
import com.fitcoach.client.domain.purchase.repository.PTSubscriptionRepository;
import com.fitcoach.client.domain.purchase.repository.PaymentRepository;
import com.fitcoach.client.domain.purchase.repository.SportEquipmentRepository;
import com.fitcoach.client.domain.pt.repository.TrainerRepository;
import com.fitcoach.client.global.exception.CustomException;
import com.fitcoach.client.global.exception.ErrorCode;
import com.fitcoach.client.model.order.Order;
import com.fitcoach.client.model.order.Payment;
import com.fitcoach.client.model.point.Point;
import com.fitcoach.client.model.point.PointHistory;
import com.fitcoach.client.model.product.AdditionalProduct;
import com.fitcoach.client.model.product.ExerciseProgram;
import com.fitcoach.client.model.product.Membership;
import com.fitcoach.client.model.product.Membership.MembershipStatus;
import com.fitcoach.client.model.product.MembershipProduct;
import com.fitcoach.client.model.product.PTProduct;
import com.fitcoach.client.model.product.PTSubscription;
import com.fitcoach.client.model.product.PTSubscription.PTStatus;
import com.fitcoach.client.model.product.Product.ProductStatus;
import com.fitcoach.client.model.product.SportEquipment;
import com.fitcoach.client.model.schedule.PTSchedule;
import com.fitcoach.client.model.schedule.PTSchedule.ScheduleStatus;
import com.fitcoach.client.model.schedule.Trainer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseService {

  private final MembershipProductRepository membershipProductRepository;
  private final MembershipRepository membershipRepository;
  private final ExerciseProgramRepository exerciseProgramRepository;
  private final SportEquipmentRepository sportEquipmentRepository;
  private final PTProductRepository ptProductRepository;
  private final PTSubscriptionRepository ptSubscriptionRepository;
  private final AdditionalProductRepository additionalProductRepository;
  private final OrderRepository orderRepository;
  private final PaymentRepository paymentRepository;
  private final PointRepository pointRepository;
  private final PointHistoryRepository pointHistoryRepository;
  private final TrainerRepository trainerRepository;
  private final PTScheduleRepository ptScheduleRepository;

  public PurchaseService(MembershipProductRepository membershipProductRepository,
      MembershipRepository membershipRepository,
      ExerciseProgramRepository exerciseProgramRepository,
      SportEquipmentRepository sportEquipmentRepository,
      PTProductRepository ptProductRepository,
      PTSubscriptionRepository ptSubscriptionRepository,
      AdditionalProductRepository additionalProductRepository,
      OrderRepository orderRepository,
      PaymentRepository paymentRepository,
      PointRepository pointRepository,
      PointHistoryRepository pointHistoryRepository,
      TrainerRepository trainerRepository,
      PTScheduleRepository ptScheduleRepository) {
    this.membershipProductRepository = membershipProductRepository;
    this.membershipRepository = membershipRepository;
    this.exerciseProgramRepository = exerciseProgramRepository;
    this.sportEquipmentRepository = sportEquipmentRepository;
    this.ptProductRepository = ptProductRepository;
    this.ptSubscriptionRepository = ptSubscriptionRepository;
    this.additionalProductRepository = additionalProductRepository;
    this.orderRepository = orderRepository;
    this.paymentRepository = paymentRepository;
    this.pointRepository = pointRepository;
    this.pointHistoryRepository = pointHistoryRepository;
    this.trainerRepository = trainerRepository;
    this.ptScheduleRepository = ptScheduleRepository;
  }

  // ─── 카탈로그 조회 ───

  @Transactional(readOnly = true)
  public List<MembershipProduct> getMembershipCatalog() {
    return membershipProductRepository.findByStatus(ProductStatus.ON_SALE);
  }

  @Transactional(readOnly = true)
  public List<ExerciseProgram> getProgramCatalog() {
    return exerciseProgramRepository.findByStatus(ProductStatus.ON_SALE);
  }

  @Transactional(readOnly = true)
  public List<SportEquipment> getSportEquipmentCatalog() {
    return sportEquipmentRepository.findAll();
  }

  @Transactional(readOnly = true)
  public List<SportEquipment> searchSportEquipment(String keyword) {
    return sportEquipmentRepository.searchByKeyword(keyword);
  }

  @Transactional(readOnly = true)
  public List<PTProduct> getPtCatalog() {
    return ptProductRepository.findByStatus(ProductStatus.ON_SALE);
  }

  @Transactional(readOnly = true)
  public List<AdditionalProduct> getAdditionalProductCatalog() {
    return additionalProductRepository.findByStatus(ProductStatus.ON_SALE);
  }

  @Transactional(readOnly = true)
  public AdditionalProduct findAdditionalProduct(Long productId) {
    return additionalProductRepository.findById(productId)
        .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public List<Trainer> getAllTrainers() {
    return trainerRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Trainer findTrainer(Long trainerId) {
    return trainerRepository.findById(trainerId)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
  }

  // ─── 구매 ───

  @Transactional
  public Membership createMembership(Long memberId, Long productId,
      LocalDate startDate, LocalDate endDate) {
    MembershipProduct product = membershipProductRepository.findById(productId)
        .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    Membership membership = new Membership(memberId, productId,
        product.getName(), product.getPrice(), MembershipStatus.ACTIVE, startDate, endDate);
    return membershipRepository.save(membership);
  }

  @Transactional
  public Order createProgramOrder(Long memberId, Long productId) {
    ExerciseProgram program = exerciseProgramRepository.findById(productId)
        .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    String orderId = "order-" + UUID.randomUUID().toString().substring(0, 8);
    return orderRepository.save(new Order(orderId, memberId.toString(),
        productId.toString(), 1, program.getPrice(), "", "COMPLETED", LocalDateTime.now()));
  }

  @Transactional
  public Order createEquipmentOrder(Long memberId, Long productId,
      int quantity, String shippingAddress) {
    SportEquipment product = sportEquipmentRepository.findById(productId)
        .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    int totalAmount = product.getPrice() * quantity;
    String orderId = "order-" + UUID.randomUUID().toString().substring(0, 8);
    return orderRepository.save(new Order(orderId, memberId.toString(),
        productId.toString(), quantity, totalAmount, shippingAddress, "COMPLETED", LocalDateTime.now()));
  }

  @Transactional
  public PTSubscription createMemberPT(Long memberId, Long productId, Long trainerId) {
    PTProduct product = ptProductRepository.findById(productId)
        .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    PTSubscription pt = new PTSubscription(PTStatus.ACTIVE,
        product.getSessionCount(), product.getSessionCount(),
        product.getPrice(), trainerId, memberId, productId);
    return ptSubscriptionRepository.save(pt);
  }

  @Transactional
  public PTSchedule addFirstPTSchedule(Long ptId, Long memberId, Long trainerId,
      LocalDate date, String time) {
    if (ptScheduleRepository.existsByTrainerIdAndScheduleDateAndScheduleTimeAndStatusNot(
        trainerId, date, time, ScheduleStatus.CANCELLED)) {
      throw new CustomException(ErrorCode.SLOT_ALREADY_BOOKED);
    }
    PTSchedule schedule = new PTSchedule(ptId, trainerId, memberId, date, time, ScheduleStatus.SCHEDULED);
    return ptScheduleRepository.save(schedule);
  }

  @Transactional
  public Order createAdditionalProductOrder(Long memberId, Long productId) {
    AdditionalProduct product = findAdditionalProduct(productId);
    String orderId = "order-" + UUID.randomUUID().toString().substring(0, 8);
    return orderRepository.save(new Order(orderId, memberId.toString(),
        productId.toString(), 1, product.getPrice(), "", "COMPLETED", LocalDateTime.now()));
  }

  // ─── 결제 ───

  @Transactional(readOnly = true)
  public int getPointBalance(Long memberId) {
    return pointRepository.findByMemberId(memberId.toString())
        .map(Point::getBalance).orElse(0);
  }

  @Transactional
  public Payment processPayment(Long memberId, int amount, String productId,
      String productType, String paymentMethod, int usedPoints) {
    String memberIdStr = memberId.toString();
    int finalAmount = amount - usedPoints;
    String paymentId = "pay-" + UUID.randomUUID().toString().substring(0, 8);
    Payment payment = new Payment(paymentId, memberIdStr, productId, productType,
        paymentMethod, finalAmount, usedPoints, "COMPLETED", LocalDateTime.now());
    paymentRepository.save(payment);

    if (usedPoints > 0) {
      pointRepository.findByMemberId(memberIdStr).ifPresent(point -> {
        point.setBalance(point.getBalance() - usedPoints);
        pointRepository.save(point);
        String histId = "ph-" + UUID.randomUUID().toString().substring(0, 8);
        pointHistoryRepository.save(new PointHistory(histId, memberIdStr, "사용",
            -usedPoints, productType + " 결제 포인트 사용", LocalDate.now(), point.getBalance()));
      });
    }
    return payment;
  }
}
