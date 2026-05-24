package com.fitcoach.client.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.controller.AuthController;
import com.fitcoach.client.controller.PurchaseController;
import com.fitcoach.client.model.order.Order;
import com.fitcoach.client.model.product.AdditionalProduct;
import com.fitcoach.client.model.product.ExerciseProgram;
import com.fitcoach.client.model.product.Membership;
import com.fitcoach.client.model.product.PT;
import com.fitcoach.client.model.product.SportEquipment;
import com.fitcoach.client.model.schedule.Trainer;
import com.fitcoach.client.util.ConsoleUtil;
import com.fitcoach.client.util.InputUtil;

public class PurchaseView {
  private InputUtil iu;
  private ConsoleUtil cu;
  private AuthController auth;
  private PurchaseController purchase;

  public PurchaseView(InputUtil iu, ConsoleUtil cu, AuthController auth,
      PurchaseController purchase) {
    this.iu = iu;
    this.cu = cu;
    this.auth = auth;
    this.purchase = purchase;
  }

  public void showPurchase() {
    cu.showStep(1, "구매 가능한 카테고리 목록을 출력합니다.");
    while (true) {
      System.out.println("\n[구매 카테고리]");
      System.out.println("1. 회원권");
      System.out.println("2. 헬스 프로그램");
      System.out.println("3. 운동용품");
      System.out.println("4. PT");
      System.out.println("5. 이벤트 중인 상품");
      System.out.println("0. 돌아가기");
      cu.showPrompt();
      String input = iu.readLine();
      switch (input) {
        case "1" -> {
          if (purchase.getMembershipCatalog().isEmpty()) {
            System.out.println("현재 해당 카테고리에 판매 중인 상품이 없습니다.");
          } else {
            cu.showStep(2, "선택한 카테고리의 구매 유스케이스로 이동합니다.");
            showPurchaseMembership();
            return;
          }
        }
        case "2" -> {
          if (purchase.getProgramCatalog().isEmpty()) {
            System.out.println("현재 해당 카테고리에 판매 중인 상품이 없습니다.");
          } else {
            cu.showStep(2, "선택한 카테고리의 구매 유스케이스로 이동합니다.");
            showPurchaseProgram();
            return;
          }
        }
        case "3" -> {
          if (purchase.getSportEquipmentCatalog().isEmpty()) {
            System.out.println("현재 해당 카테고리에 판매 중인 상품이 없습니다.");
          } else {
            cu.showStep(2, "선택한 카테고리의 구매 유스케이스로 이동합니다.");
            showPurchaseEquipment();
            return;
          }
        }
        case "4" -> {
          if (purchase.getPtCatalog().isEmpty()) {
            System.out.println("현재 해당 카테고리에 판매 중인 상품이 없습니다.");
          } else {
            cu.showStep(2, "선택한 카테고리의 구매 유스케이스로 이동합니다.");
            showPurchasePT();
            return;
          }
        }
        case "5" -> System.out.println("현재 해당 카테고리에 판매 중인 상품이 없습니다.");
        case "0" -> { return; }
        default  -> System.out.println("올바른 메뉴를 선택해주세요.");
      }
    }
  }

  public void showPurchaseMembership() {
    String memberId = auth.getCurrentMember().getMemberId();
    List<Membership> catalog = purchase.getMembershipCatalog();

    cu.showStep(1, "이용 가능한 회원권 목록을 출력합니다.");
    for (int i = 0; i < catalog.size(); i++) {
      Membership m = catalog.get(i);
      System.out.printf("%d. %-8s %,6d원  |  %s%n",
          i + 1, m.getProductName(), m.getPrice(), m.getDescription());
    }
    System.out.println("0. 돌아가기");

    Membership selected = null;
    while (selected == null) {
      cu.showPrompt();
      String input = iu.readLine();
      if (input.equals("0")) return;
      try {
        int idx = Integer.parseInt(input) - 1;
        if (idx >= 0 && idx < catalog.size()) selected = catalog.get(idx);
        else System.out.println("올바른 번호를 입력해주세요.");
      } catch (NumberFormatException e) {
        System.out.println("올바른 번호를 입력해주세요.");
      }
    }

    if (!"ACTIVE".equals(selected.getStatus())) {
      System.out.println("해당 회원권은 현재 판매되지 않습니다.");
      return;
    }

    cu.showStep(2, "선택한 회원권의 상세 정보를 출력합니다.");
    cu.showSeparator();
    System.out.println("상품명    : " + selected.getProductName());
    System.out.printf("가격      : %,d원%n", selected.getPrice());
    System.out.println("설명      : " + selected.getDescription());
    System.out.println("이용 시설 : 헬스장 전체 시설");
    cu.showSeparator();
    System.out.println("구매하시겠습니까? (Y/N)");
    cu.showPrompt();
    if (!iu.readLine().equalsIgnoreCase("Y")) return;

    cu.showStep(3, "현재 활성화된 회원권 보유 여부를 확인합니다.");
    Membership active = purchase.findActiveMembership(memberId);
    boolean isExtension = false;
    if (active != null) {
      System.out.println("[현재 보유 회원권]");
      System.out.println("종류   : " + active.getProductName());
      System.out.println("만료일 : " + active.getEndDate());
      System.out.println("기존 회원권 만료 후 적용됩니다.");
      System.out.println("연장 구매하시겠습니까? (Y/N)");
      cu.showPrompt();
      if (!iu.readLine().equalsIgnoreCase("Y")) return;
      isExtension = true;
    }

    cu.showStep(4, "회원권 시작일을 선택합니다.");
    System.out.println("1. 즉시 시작 (오늘: " + LocalDate.now() + ")");
    System.out.println("2. 날짜 직접 입력 (yyyy-MM-dd)");
    cu.showPrompt();

    LocalDate startDate;
    if (iu.readLine().equals("2")) {
      startDate = iu.readDate("시작일 (yyyy-MM-dd): ");
    } else {
      startDate = isExtension ? active.getEndDate().plusDays(1) : LocalDate.now();
    }

    int duration = purchase.getMembershipDurationDays(selected.getProductName());
    LocalDate endDate = startDate.plusDays(duration - 1);

    cu.showStep(5, "주문 확인 화면을 출력합니다.");
    cu.showSeparator();
    System.out.println("[주문 확인]");
    System.out.println("상품명    : " + selected.getProductName());
    System.out.println("시작일    : " + startDate);
    System.out.println("만료일    : " + endDate);
    System.out.printf("결제 금액  : %,d원%n", selected.getPrice());
    cu.showSeparator();
    System.out.println("결제를 진행하시겠습니까? (Y/N)");
    cu.showPrompt();
    if (!iu.readLine().equalsIgnoreCase("Y")) return;

    cu.showStep(6, "결제 유스케이스(UC07)를 실행합니다.");
    boolean paid = showPayment(memberId, selected.getPrice(),
        selected.getProductId(), "MEMBERSHIP");
    if (!paid) return;

    cu.showStep(7, "회원권을 계정에 등록합니다.");
    Membership newMs = purchase.createMembership(memberId, selected, startDate, endDate);
    if (newMs == null) {
      System.out.println("회원권 등록에 실패하였습니다. 고객센터에 문의해주세요.");
      return;
    }
    System.out.println("회원권 구매가 완료되었습니다.");
  }

  private void showPurchaseProgram() {
    String memberId = auth.getCurrentMember().getMemberId();
    List<ExerciseProgram> catalog = purchase.getProgramCatalog();

    cu.showStep(1, "이용 가능한 헬스 프로그램 목록을 출력합니다.");
    for (int i = 0; i < catalog.size(); i++) {
      ExerciseProgram p = catalog.get(i);
      Trainer trainer = purchase.findTrainer(p.getInstructorId());
      String trainerName = (trainer != null) ? trainer.getName() : "미정";
      System.out.printf("%d. %-14s %,6d원 | 담당: %-6s | 잔여: %d/%d명%n",
          i + 1, p.getProductName(), p.getPrice(),
          trainerName, p.getRemainingCapacity(), p.getCapacity());
    }
    System.out.println("0. 돌아가기");

    ExerciseProgram selected = null;
    while (selected == null) {
      cu.showPrompt();
      String input = iu.readLine();
      if (input.equals("0")) return;
      try {
        int idx = Integer.parseInt(input) - 1;
        if (idx >= 0 && idx < catalog.size()) selected = catalog.get(idx);
        else System.out.println("올바른 번호를 입력해주세요.");
      } catch (NumberFormatException e) {
        System.out.println("올바른 번호를 입력해주세요.");
      }
    }

    Trainer trainer = purchase.findTrainer(selected.getInstructorId());
    String trainerName = (trainer != null) ? trainer.getName() : "미정";

    cu.showStep(2, "선택한 프로그램의 상세 정보를 출력합니다.");
    cu.showSeparator();
    System.out.println("프로그램명 : " + selected.getProductName());
    System.out.printf("가격       : %,d원%n", selected.getPrice());
    System.out.println("설명       : " + selected.getDescription());
    System.out.println("담당 강사  : " + trainerName);
    System.out.println("정원       : " + selected.getCapacity() + "명");
    System.out.println("잔여 정원  : " + selected.getRemainingCapacity() + "명");
    cu.showSeparator();
    System.out.println("구매하시겠습니까? (Y/N)");
    cu.showPrompt();
    if (!iu.readLine().equalsIgnoreCase("Y")) return;

    cu.showStep(3, "해당 프로그램의 잔여 정원을 확인합니다.");
    if (selected.getRemainingCapacity() <= 0) {
      System.out.println("해당 프로그램은 현재 정원이 마감되었습니다.");
      System.out.println("대기자 등록은 카운터에 문의해주세요.");
      return;
    }

    cu.showStep(4, "프로그램 시작 희망일을 선택합니다.");
    System.out.println("1. 즉시 시작 (오늘: " + LocalDate.now() + ")");
    System.out.println("2. 날짜 직접 입력 (yyyy-MM-dd)");
    cu.showPrompt();
    LocalDate startDate = iu.readLine().equals("2")
        ? iu.readDate("시작 희망일 (yyyy-MM-dd): ")
        : LocalDate.now();

    cu.showStep(5, "주문 확인 화면을 출력합니다.");
    cu.showSeparator();
    System.out.println("[주문 확인]");
    System.out.println("프로그램명 : " + selected.getProductName());
    System.out.println("담당 강사  : " + trainerName);
    System.out.println("시작 희망일: " + startDate);
    System.out.printf("결제 금액  : %,d원%n", selected.getPrice());
    cu.showSeparator();
    System.out.println("결제를 진행하시겠습니까? (Y/N)");
    cu.showPrompt();
    if (!iu.readLine().equalsIgnoreCase("Y")) return;

    cu.showStep(6, "결제 유스케이스(UC07)를 실행합니다.");
    boolean paid = showPayment(memberId, selected.getPrice(),
        selected.getProductId(), "PROGRAM");
    if (!paid) return;

    cu.showStep(7, "프로그램을 계정에 등록합니다.");
    Order order = purchase.createProgramOrder(memberId, selected, startDate);
    if (order == null) {
      System.out.println("프로그램 등록에 실패하였습니다. 고객센터에 문의해주세요.");
      return;
    }
    System.out.println("헬스 프로그램 구매가 완료되었습니다.");
  }

  private void showPurchaseEquipment() {
    String memberId = auth.getCurrentMember().getMemberId();
    List<SportEquipment> displayList = new ArrayList<>(purchase.getSportEquipmentCatalog());

    cu.showStep(1, "판매 중인 운동용품 목록을 출력합니다.");
    System.out.println("F. 카테고리 필터 / 검색어 입력   0. 돌아가기");
    printSportEquipmentList(displayList);

    while (true) {
      cu.showPrompt();
      String input = iu.readLine();
      if (input.equals("0")) return;

      if (input.equalsIgnoreCase("F")) {
        System.out.println("검색어 또는 카테고리(헬스용품/의류/보충제)를 입력하세요.");
        cu.showPrompt();
        String keyword = iu.readLine();
        displayList = purchase.searchSportEquipment(keyword);
        if (displayList.isEmpty()) {
          System.out.println("검색 결과가 없습니다. 전체 목록을 표시합니다.");
          displayList = new ArrayList<>(purchase.getSportEquipmentCatalog());
        }
        printSportEquipmentList(displayList);
        continue;
      }

      try {
        int idx = Integer.parseInt(input) - 1;
        if (idx < 0 || idx >= displayList.size()) {
          System.out.println("올바른 번호를 입력해주세요.");
          continue;
        }
        SportEquipment selected = displayList.get(idx);

        cu.showStep(2, "선택한 상품의 상세 정보를 출력합니다.");
        cu.showSeparator();
        System.out.println("상품명   : " + selected.getProductName());
        System.out.println("카테고리 : " + selected.getCategory());
        System.out.printf("가격     : %,d원%n", selected.getPrice());
        System.out.println("설명     : " + selected.getDescription());
        System.out.println("재고     : " + selected.getStock() + "개");
        cu.showSeparator();

        cu.showStep(3, "구매 수량을 입력합니다.");
        int quantity = 0;
        while (quantity <= 0) {
          System.out.print("수량: ");
          try {
            quantity = Integer.parseInt(iu.readLine());
            if (quantity <= 0) System.out.println("1 이상의 수량을 입력해주세요.");
            else if (quantity > selected.getStock()) {
              System.out.println("재고가 부족합니다. (현재 재고: " + selected.getStock() + "개)");
              quantity = 0;
            }
          } catch (NumberFormatException e) {
            System.out.println("숫자를 입력해주세요.");
          }
        }

        cu.showStep(4, "배송지 정보를 입력합니다.");
        System.out.println("1. 기존 배송지 사용 (" + auth.getCurrentMember().getAddress() + ")");
        System.out.println("2. 새 배송지 입력");
        cu.showPrompt();
        String shippingAddress;
        if (iu.readLine().equals("1")) {
          shippingAddress = auth.getCurrentMember().getAddress();
          if (shippingAddress == null || shippingAddress.isEmpty()) {
            System.out.println("올바른 배송지 정보를 입력해주세요.");
            return;
          }
        } else {
          System.out.print("배송지 주소: ");
          shippingAddress = iu.readLine();
          if (shippingAddress.isEmpty()) {
            System.out.println("올바른 배송지 정보를 입력해주세요.");
            return;
          }
        }

        int totalAmount = selected.getPrice() * quantity;
        cu.showStep(5, "주문 확인 화면을 출력합니다.");
        cu.showSeparator();
        System.out.println("[주문 확인]");
        System.out.println("상품명   : " + selected.getProductName());
        System.out.println("수량     : " + quantity + "개");
        System.out.println("배송지   : " + shippingAddress);
        System.out.printf("결제 금액 : %,d원%n", totalAmount);
        cu.showSeparator();
        System.out.println("결제를 진행하시겠습니까? (Y/N)");
        cu.showPrompt();
        if (!iu.readLine().equalsIgnoreCase("Y")) return;

        cu.showStep(6, "결제 유스케이스(UC07)를 실행합니다.");
        boolean paid = showPayment(memberId, totalAmount,
            selected.getProductId(), "SPORT_EQUIPMENT");
        if (!paid) return;

        cu.showStep(7, "주문을 등록합니다.");
        Order order = purchase.createEquipmentOrder(memberId, selected, quantity, shippingAddress);
        if (order == null) {
          System.out.println("주문 등록에 실패하였습니다. 고객센터에 문의해주세요.");
          return;
        }
        System.out.println("운동용품 구매가 완료되었습니다.");
        System.out.println("주문 번호: " + order.getOrderId());
        return;
      } catch (NumberFormatException e) {
        System.out.println("올바른 번호를 입력해주세요.");
      }
    }
  }

  private void showPurchasePT() {
    String memberId = auth.getCurrentMember().getMemberId();

    cu.showStep(1, "소속 트레이너 목록을 출력합니다.");
    List<Trainer> displayTrainers = new ArrayList<>(purchase.getAllTrainersList());
    printTrainerList(displayTrainers);
    System.out.println("F. 전문 분야 필터   0. 돌아가기");

    Trainer selectedTrainer = null;
    while (selectedTrainer == null) {
      cu.showPrompt();
      String input = iu.readLine();
      if (input.equals("0")) return;

      if (input.equalsIgnoreCase("F")) {
        System.out.print("전문 분야 키워드 입력 (예: 요가, 웨이트): ");
        String keyword = iu.readLine();
        List<Trainer> filtered = new ArrayList<>();
        for (Trainer t : purchase.getAllTrainersList()) {
          if (t.getSpecialty().contains(keyword)) filtered.add(t);
        }
        if (filtered.isEmpty()) {
          System.out.println("해당 조건의 트레이너가 없습니다. 전체 목록을 표시합니다.");
          displayTrainers = new ArrayList<>(purchase.getAllTrainersList());
        } else {
          displayTrainers = filtered;
        }
        printTrainerList(displayTrainers);
        System.out.println("F. 전문 분야 필터   0. 돌아가기");
        continue;
      }

      try {
        int idx = Integer.parseInt(input) - 1;
        if (idx >= 0 && idx < displayTrainers.size()) selectedTrainer = displayTrainers.get(idx);
        else System.out.println("올바른 번호를 입력해주세요.");
      } catch (NumberFormatException e) {
        System.out.println("올바른 번호를 입력해주세요.");
      }
    }

    cu.showStep(2, "선택한 트레이너의 상세 프로필을 출력합니다.");
    cu.showSeparator();
    System.out.println("이름      : " + selectedTrainer.getName());
    System.out.println("경력      : " + selectedTrainer.getCareer());
    System.out.println("자격증    : " + selectedTrainer.getCertification());
    System.out.println("전문 분야 : " + selectedTrainer.getSpecialty());
    System.out.printf("평점      : %.1f%n", selectedTrainer.getRating());
    cu.showSeparator();
    System.out.println("[PT 프로그램]");
    List<PT> ptCatalog = purchase.getPtCatalog();
    for (int i = 0; i < ptCatalog.size(); i++) {
      PT p = ptCatalog.get(i);
      System.out.printf("%d. %-10s %,d원%n", i + 1, p.getProductName(), p.getPrice());
    }
    System.out.println("0. 돌아가기");

    PT selectedPT = null;
    while (selectedPT == null) {
      cu.showPrompt();
      String input = iu.readLine();
      if (input.equals("0")) return;
      try {
        int idx = Integer.parseInt(input) - 1;
        if (idx >= 0 && idx < ptCatalog.size()) selectedPT = ptCatalog.get(idx);
        else System.out.println("올바른 번호를 입력해주세요.");
      } catch (NumberFormatException e) {
        System.out.println("올바른 번호를 입력해주세요.");
      }
    }

    cu.showStep(3, "트레이너의 예약 가능한 스케줄을 출력합니다.");
    LocalTime[] timeSlots = {LocalTime.of(10, 0), LocalTime.of(14, 0), LocalTime.of(18, 0)};
    LocalDate today = LocalDate.now();
    System.out.println("날짜             시간    상태");
    cu.showSeparator();
    int slotNo = 1;
    List<LocalDate> slotDates = new ArrayList<>();
    List<LocalTime> slotTimes = new ArrayList<>();
    for (int d = 1; d <= 7; d++) {
      LocalDate date = today.plusDays(d);
      for (LocalTime time : timeSlots) {
        boolean booked = purchase.isSlotBooked(selectedTrainer.getTrainerId(), date, time);
        System.out.printf("%2d. %s  %s  %s%n",
            slotNo, date, time, booked ? "[예약됨]" : "[예약 가능]");
        slotDates.add(date);
        slotTimes.add(time);
        slotNo++;
      }
    }
    System.out.println("0. 돌아가기");

    LocalDate chosenDate = null;
    LocalTime chosenTime = null;
    while (chosenDate == null) {
      cu.showPrompt();
      String input = iu.readLine();
      if (input.equals("0")) return;
      try {
        int idx = Integer.parseInt(input) - 1;
        if (idx < 0 || idx >= slotDates.size()) {
          System.out.println("올바른 번호를 입력해주세요.");
          continue;
        }
        LocalDate cDate = slotDates.get(idx);
        LocalTime cTime = slotTimes.get(idx);
        if (purchase.isSlotBooked(selectedTrainer.getTrainerId(), cDate, cTime)) {
          System.out.println("선택하신 시간은 이미 예약되어 있습니다.");
          continue;
        }
        chosenDate = cDate;
        chosenTime = cTime;
      } catch (NumberFormatException e) {
        System.out.println("올바른 번호를 입력해주세요.");
      }
    }

    cu.showStep(5, "주문 확인 화면을 출력합니다.");
    cu.showSeparator();
    System.out.println("[주문 확인]");
    System.out.println("트레이너   : " + selectedTrainer.getName());
    System.out.println("PT 프로그램: " + selectedPT.getProductName());
    System.out.println("첫 일정    : " + chosenDate + " " + chosenTime);
    System.out.printf("결제 금액   : %,d원%n", selectedPT.getPrice());
    cu.showSeparator();
    System.out.println("결제를 진행하시겠습니까? (Y/N)");
    cu.showPrompt();
    if (!iu.readLine().equalsIgnoreCase("Y")) return;

    cu.showStep(6, "결제 유스케이스(UC07)를 실행합니다.");
    boolean paid = showPayment(memberId, selectedPT.getPrice(),
        selectedPT.getProductId(), "PT");
    if (!paid) return;

    cu.showStep(7, "PT 이용권을 계정에 등록합니다.");
    PT newPT = purchase.createMemberPT(memberId, selectedPT, selectedTrainer.getTrainerId());
    if (newPT == null) {
      System.out.println("PT 이용권 등록에 실패하였습니다. 고객센터에 문의해주세요.");
      return;
    }
    boolean scheduled = purchase.addFirstPTSchedule(newPT.getPtId(), memberId,
        selectedTrainer.getTrainerId(), chosenDate, chosenTime);
    if (!scheduled) {
      System.out.println("PT 이용권 등록에 실패하였습니다. 고객센터에 문의해주세요.");
      return;
    }
    System.out.println("[알림] " + selectedTrainer.getName() + " 트레이너에게 신규 PT 신청 알림을 전송했습니다.");
    System.out.println("PT 구매가 완료되었습니다.");
  }

  public boolean showPayment(String memberId, int amount, String productId, String productType) {
    cu.showStep(1, "결제 수단 선택 화면을 출력합니다.");
    System.out.println("1. 신용카드");
    System.out.println("2. 체크카드");
    System.out.println("3. 간편결제");
    System.out.println("0. 취소");
    cu.showPrompt();

    String methodInput = iu.readLine();
    String paymentMethod = switch (methodInput) {
      case "1" -> "신용카드";
      case "2" -> "체크카드";
      case "3" -> "간편결제";
      default  -> null;
    };
    if (paymentMethod == null) {
      System.out.println("결제가 취소되었습니다.");
      return false;
    }
    System.out.print("카드 번호 (예: 1234-5678-9012-3456): ");
    iu.readLine();

    cu.showStep(2, "보유 포인트 잔액을 조회합니다.");
    int pointBalance = purchase.getPointBalance(memberId);
    int usedPoints = 0;
    int finalAmount = amount;

    System.out.printf("보유 포인트: %,d점%n", pointBalance);
    if (pointBalance > 0) {
      System.out.println("포인트를 사용하시겠습니까? (Y/N)");
      cu.showPrompt();
      if (iu.readLine().equalsIgnoreCase("Y")) {
        while (true) {
          System.out.printf("사용할 포인트 (0 ~ %,d): ", pointBalance);
          try {
            usedPoints = Integer.parseInt(iu.readLine());
            if (usedPoints < 0 || usedPoints > pointBalance) {
              System.out.println("올바른 포인트 금액을 입력해주세요.");
              continue;
            }
            if (usedPoints > amount) usedPoints = amount;
            finalAmount = amount - usedPoints;
            break;
          } catch (NumberFormatException e) {
            System.out.println("숫자를 입력해주세요.");
          }
        }
        System.out.printf("포인트 사용 후 결제 금액: %,d원%n", finalAmount);
      }
    }

    cu.showSeparator();
    System.out.printf("결제 수단  : %s%n", paymentMethod);
    System.out.printf("사용 포인트: %,d점%n", usedPoints);
    System.out.printf("최종 금액  : %,d원%n", finalAmount);
    cu.showSeparator();
    System.out.println("[결제하기] 진행하시려면 Y를 입력하세요.");
    cu.showPrompt();
    if (!iu.readLine().equalsIgnoreCase("Y")) {
      System.out.println("결제가 취소되었습니다.");
      return false;
    }

    boolean success = purchase.processPayment(memberId, amount, productId,
        productType, paymentMethod, usedPoints);
    if (!success) {
      System.out.println("결제 서버와의 연결에 실패하였습니다. 잠시 후 다시 시도해주세요.");
      return false;
    }
    cu.showStep(3, "결제가 완료되었습니다.");
    return true;
  }

  private void printSportEquipmentList(List<SportEquipment> list) {
    for (int i = 0; i < list.size(); i++) {
      SportEquipment se = list.get(i);
      System.out.printf("%d. [%s] %-14s %,6d원 | 재고: %d개%n",
          i + 1, se.getCategory(), se.getProductName(), se.getPrice(), se.getStock());
    }
  }

  private void printTrainerList(List<Trainer> list) {
    for (int i = 0; i < list.size(); i++) {
      Trainer t = list.get(i);
      System.out.printf("%d. %-6s | 전문: %-14s | %s | 평점: %.1f%n",
          i + 1, t.getName(), t.getSpecialty(), t.getCareer(), t.getRating());
    }
  }
}
