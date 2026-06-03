package com.fitcoach.client.view;

import java.time.LocalDate;
import java.util.List;
import com.fitcoach.client.controller.AuthController;
import com.fitcoach.client.controller.MemberController;
import com.fitcoach.client.model.member.Attendance;
import com.fitcoach.client.model.member.Member;
import com.fitcoach.client.model.order.Order;
import com.fitcoach.client.model.point.Point;
import com.fitcoach.client.model.point.PointHistory;
import com.fitcoach.client.model.product.AdditionalProduct;
import com.fitcoach.client.model.product.Membership;
import com.fitcoach.client.util.ConsoleUtil;
import com.fitcoach.client.util.InputUtil;

public class MemberView {
  private InputUtil iu;
  private ConsoleUtil cu;
  private AuthController auth;
  private MemberController member;
  private PurchaseView purchaseView;

  public MemberView(InputUtil iu, ConsoleUtil cu, AuthController auth,
      MemberController member, PurchaseView purchaseView) {
    this.iu = iu;
    this.cu = cu;
    this.auth = auth;
    this.member = member;
    this.purchaseView = purchaseView;
  }

  public void showManageMyInfo() {
    Member current = auth.getCurrentMember();
    if (current == null) {
      System.out.println("로그인 세션이 만료되었습니다. 다시 로그인해주세요.");
      return;
    }

    while (true) {
      cu.showStep(2, "내정보 메인 화면을 출력합니다.");
      if (!current.init()) {
        System.out.println("내정보를 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
        return;
      }

      cu.showThickSeparator();
      System.out.println("[프로필 정보]");
      System.out.println("이름     : " + current.getName());
      System.out.println("닉네임   : " + current.getNickname());
      System.out.println("아이디   : " + current.getLoginId());
      System.out.println("전화번호 : " + current.getPhoneNumber());
      System.out.println("생년월일 : " + current.getBirthDate());
      System.out.println("신체정보 : " + current.getPhysicalInfo());
      System.out.println("주소     : " + current.getAddress());
      System.out.println("가입일   : " + current.getJoinDate());

      cu.showSeparator();
      System.out.println("[회원권 현황]");
      Membership active = member.getActiveMembership(current.getMemberId());
      if (active == null) {
        System.out.println("보유 중인 회원권이 없습니다.");
      } else {
        System.out.println("종류   : " + active.getProductName());
        System.out.println("만료일 : " + active.getEndDate());
        long remaining = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), active.getEndDate());
        System.out.println("잔여   : " + remaining + "일");
      }

      cu.showSeparator();
      System.out.println("[포인트 잔액]");
      System.out.printf("현재 포인트: %,d점%n", member.getPointBalance(current.getMemberId()));
      cu.showThickSeparator();

      System.out.println("1. 내정보 수정   2. 회원권 관리   3. 포인트 내역");
      System.out.println("0. 돌아가기");
      cu.showPrompt();

      String input = iu.readLine();
      switch (input) {
        case "1" -> showUpdateMyInfo();
        case "2" -> showManageMembership();
        case "3" -> showViewPointHistory();
        case "0" -> { return; }
        default  -> System.out.println("올바른 메뉴를 선택해주세요.");
      }
    }
  }

  public void showUpdateMyInfo() {
    Member current = auth.getCurrentMember();
    cu.showStep(2, "내정보 수정 폼을 출력합니다.");
    System.out.println("(생략 시 Enter → 현재 값 유지)");
    cu.showSeparator();

    System.out.println("현재 닉네임: " + current.getNickname());
    System.out.print("새 닉네임: ");
    String newNickname = iu.readLine();

    System.out.println("현재 전화번호: " + current.getPhoneNumber());
    String newPhone = "";
    while (true) {
      System.out.print("새 전화번호 (예: 010-1234-5678): ");
      newPhone = iu.readLine();
      if (newPhone.isEmpty()) break;
      if (!newPhone.matches("\\d{3}-\\d{4}-\\d{4}")) {
        System.out.println("올바른 형식의 전화번호를 입력해주세요.");
        continue;
      }
      if (member.isDuplicatePhone(newPhone, current.getMemberId())) {
        System.out.println("이미 사용 중인 전화번호입니다.");
        continue;
      }
      break;
    }

    System.out.println("현재 생년월일: " + current.getBirthDate());
    System.out.print("새 생년월일 (예: 19900101): ");
    String newBirthDate = iu.readLine();

    System.out.println("현재 신체정보: " + current.getPhysicalInfo());
    System.out.print("새 신체정보 (예: 175cm/70kg): ");
    String newPhysicalInfo = iu.readLine();

    System.out.println("현재 주소: " + current.getAddress());
    System.out.print("새 주소: ");
    String newAddress = iu.readLine();

    System.out.println("현재 프로필 사진: " + current.getProfilePicture());
    System.out.println("프로필 사진을 변경하시겠습니까? (Y/N)");
    cu.showPrompt();
    String newProfilePicture = "";
    if (iu.readLine().equalsIgnoreCase("Y")) {
      System.out.println("1. 갤러리에서 선택   2. 카메라 촬영");
      cu.showPrompt();
      iu.readLine();
      System.out.print("파일명 입력: ");
      newProfilePicture = iu.readLine();
      if (newProfilePicture.isEmpty()) {
        System.out.println("프로필 사진 업로드에 실패하였습니다. 파일 크기 또는 형식을 확인해주세요.");
        newProfilePicture = "";
      } else {
        System.out.println("[미리보기] " + newProfilePicture);
      }
    }

    System.out.println("비밀번호를 변경하시겠습니까? (Y/N)");
    cu.showPrompt();
    String newPassword = "";
    if (iu.readLine().equalsIgnoreCase("Y")) {
      cu.showStep(5, "현재 비밀번호를 입력하여 본인 확인을 진행합니다.");
      System.out.print("현재 비밀번호: ");
      String currentPw = iu.readLine();
      if (!currentPw.equals(current.getPassword())) {
        System.out.println("현재 비밀번호가 올바르지 않습니다.");
        return;
      }
      System.out.print("새 비밀번호: ");
      newPassword = iu.readLine();
    }

    cu.showStep(7, "수정 내용을 최종 확인합니다.");
    cu.showSeparator();
    if (!newNickname.isEmpty())     System.out.println("닉네임   : " + newNickname);
    if (!newPhone.isEmpty())        System.out.println("전화번호 : " + newPhone);
    if (!newBirthDate.isEmpty())    System.out.println("생년월일 : " + newBirthDate);
    if (!newPhysicalInfo.isEmpty()) System.out.println("신체정보 : " + newPhysicalInfo);
    if (!newAddress.isEmpty())      System.out.println("주소     : " + newAddress);
    if (!newProfilePicture.isEmpty()) System.out.println("프로필   : " + newProfilePicture);
    if (!newPassword.isEmpty())     System.out.println("비밀번호 : (변경됨)");
    cu.showSeparator();
    System.out.println("저장하시겠습니까? (Y/N)");
    cu.showPrompt();
    if (!iu.readLine().equalsIgnoreCase("Y")) return;

    cu.showStep(8, "수정된 정보를 저장합니다.");
    if (!current.init()) {
      System.out.println("정보 저장에 실패하였습니다. 잠시 후 다시 시도해주세요.");
      return;
    }
    member.updateMemberInfo(current, newNickname, newPhone, newBirthDate,
        newPhysicalInfo, newAddress, newProfilePicture, newPassword);
    System.out.println("내정보가 성공적으로 수정되었습니다.");
  }

  public void showManageMembership() {
    String memberId = auth.getCurrentMember().getMemberId();
    cu.showStep(2, "회원권 관리 화면을 출력합니다.");
    cu.showStep(3, "잔여기간 조회 유스케이스(UC17)를 실행합니다.");

    Membership active = member.getActiveMembership(memberId);
    if (active == null) {
      System.out.println("현재 보유 중인 회원권이 없습니다.");
      System.out.println("[회원권 구매하기] 1. 구매   0. 돌아가기");
      cu.showPrompt();
      if (iu.readLine().equals("1")) purchaseView.showPurchaseMembership();
      return;
    }

    showRemainingPeriod(active);

    cu.showStep(5, "부가 상품 보유 현황을 출력합니다.");
    cu.showSeparator();
    System.out.println("[부가 상품 현황]");
    System.out.println("부가 상품 구매 수: " + member.getAdditionalOrderCount(memberId) + "건");
    System.out.println("PT 잔여 횟수    : " + member.getActivePtRemainingCount(memberId) + "회");

    cu.showSeparator();
    System.out.println("[이용 내역 (출석 기록)]");
    List<Attendance> myAttendances = member.getAttendances(memberId);
    if (myAttendances.isEmpty()) {
      System.out.println("이용 내역이 없습니다.");
    } else {
      for (Attendance a : myAttendances) {
        System.out.println("- " + a.getAttendanceDateTime().toLocalDate()
            + "  " + a.getAttendanceDateTime().toLocalTime()
            + "  (" + a.getAuthMethod() + ")");
      }
    }

    long remaining = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), active.getEndDate());
    if (remaining <= 7 && remaining >= 0) {
      cu.showSeparator();
      System.out.println("[안내] 회원권이 " + remaining + "일 후 만료됩니다. 갱신을 권장합니다.");
    }

    cu.showSeparator();
    System.out.println("1. 부가 상품 관리   0. 돌아가기");
    cu.showPrompt();
    if (iu.readLine().equals("1")) showManageAdditionalProduct();
  }

  public void showCheckRemainingPeriod() {
    String memberId = auth.getCurrentMember().getMemberId();
    cu.showStep(2, "회원권 정보를 조회합니다.");
    Membership active = member.getActiveMembership(memberId);

    if (active == null) {
      System.out.println("회원권이 만료되었습니다.");
      System.out.println("[회원권 구매하기] 1. 구매   0. 돌아가기");
      cu.showPrompt();
      if (iu.readLine().equals("1")) purchaseView.showPurchaseMembership();
      return;
    }

    showRemainingPeriod(active);

    long remaining = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), active.getEndDate());
    if (remaining <= 7 && remaining >= 0) {
      System.out.println("[안내] 회원권이 " + remaining + "일 후 만료됩니다. 갱신을 권장합니다.");
    }

    System.out.println("상세 정보를 보시겠습니까? (Y/N)");
    cu.showPrompt();
    if (iu.readLine().equalsIgnoreCase("Y")) {
      cu.showSeparator();
      System.out.println("설명     : " + active.getDescription());
      System.out.println("구매 상품: " + active.getProductName());
      cu.showSeparator();
    }
  }

  public void showManageAdditionalProduct() {
    String memberId = auth.getCurrentMember().getMemberId();

    if (member.getActiveMembership(memberId) == null) {
      System.out.println("부가 상품은 활성화된 회원권 보유 시에만 신청 가능합니다.");
      return;
    }

    while (true) {
      cu.showStep(2, "현재 이용 중인 부가 상품 목록을 출력합니다.");
      cu.showSeparator();
      System.out.println("[보유 부가 상품]");
      List<Order> myAdOrders = member.getAdditionalOrders(memberId);
      if (myAdOrders.isEmpty()) {
        System.out.println("이용 중인 부가 상품이 없습니다.");
      } else {
        for (Order o : myAdOrders) {
          AdditionalProduct ap = member.findAdditionalProduct(o.getProductId());
          String name = (ap != null) ? ap.getProductName() : o.getProductId();
          System.out.println("- " + name + " (주문일: " + o.getOrderDateTime().toLocalDate() + ")");
        }
      }

      cu.showStep(3, "신청 가능한 부가 상품 목록을 출력합니다.");
      cu.showSeparator();
      System.out.println("[신청 가능한 부가 상품]");
      List<AdditionalProduct> catalog = member.getAdditionalProductCatalog();
      if (catalog.isEmpty()) {
        System.out.println("부가 상품 정보를 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
        return;
      }
      for (int i = 0; i < catalog.size(); i++) {
        AdditionalProduct ap = catalog.get(i);
        System.out.printf("%d. %-12s %,6d원 | 이용기간: %d일%n",
            i + 1, ap.getProductName(), ap.getPrice(), ap.getUsagePeriod());
      }
      System.out.println("0. 돌아가기");
      cu.showPrompt();

      String input = iu.readLine();
      if (input.equals("0")) return;

      AdditionalProduct selected;
      try {
        int idx = Integer.parseInt(input) - 1;
        if (idx < 0 || idx >= catalog.size()) {
          System.out.println("올바른 번호를 입력해주세요.");
          continue;
        }
        selected = catalog.get(idx);
      } catch (NumberFormatException e) {
        System.out.println("올바른 번호를 입력해주세요.");
        continue;
      }

      boolean alreadyOwned = myAdOrders.stream()
          .anyMatch(o -> o.getProductId().equals(selected.getProductId()));
      if (alreadyOwned) {
        System.out.println("이미 해당 부가 상품을 이용 중입니다. 추가 구매하시겠습니까? (Y/N)");
        cu.showPrompt();
        if (!iu.readLine().equalsIgnoreCase("Y")) continue;
      }

      cu.showStep(5, "선택한 부가 상품의 상세 정보를 출력합니다.");
      cu.showSeparator();
      System.out.println("[신청 확인]");
      System.out.println("상품명   : " + selected.getProductName());
      System.out.println("설명     : " + selected.getDescription());
      System.out.println("이용기간 : " + selected.getUsagePeriod() + "일");
      System.out.printf("가격     : %,d원%n", selected.getPrice());
      cu.showSeparator();
      System.out.println("[결제 및 신청] 진행하시겠습니까? (Y/N)");
      cu.showPrompt();
      if (!iu.readLine().equalsIgnoreCase("Y")) continue;

      cu.showStep(7, "결제 유스케이스(UC07)를 실행합니다.");
      boolean paid = purchaseView.showPayment(memberId, selected.getPrice(),
          selected.getProductId(), "ADDITIONAL");
      if (!paid) continue;

      cu.showStep(8, "부가 상품을 계정에 등록합니다.");
      Order order = member.createAdditionalProductOrder(memberId, selected);
      if (order == null) {
        System.out.println("부가 상품 등록에 실패하였습니다. 고객센터에 문의해주세요.");
        return;
      }
      System.out.println("부가 상품 신청이 완료되었습니다.");
      System.out.println("[신청 내역] " + selected.getProductName()
          + " | 이용기간: " + selected.getUsagePeriod() + "일"
          + " | 주문번호: " + order.getOrderId());
    }
  }

  public void showViewPointHistory() {
    String memberId = auth.getCurrentMember().getMemberId();
    cu.showStep(2, "포인트 정보를 조회합니다.");

    Point memberPoint = member.getPoint(memberId);
    if (memberPoint == null || memberPoint.getBalance() == 0) {
      System.out.println("현재 보유한 포인트가 없습니다.");
      System.out.println("[포인트 적립 방법] 운동 기록 저장 시 자동 지급 (기본 10점 + 30분 이상 시 5점 추가)");
      return;
    }

    cu.showStep(3, "포인트 잔액과 유효기간 정보를 출력합니다.");
    cu.showSeparator();
    System.out.printf("현재 포인트 잔액: %,d점%n", memberPoint.getBalance());
    System.out.println("유효기간        : " + memberPoint.getExpiryDate() + "까지");
    cu.showSeparator();

    List<PointHistory> myHistory = member.getPointHistories(memberId);
    myHistory.sort((a, b) -> b.getDate().compareTo(a.getDate()));
    LocalDate filterFrom = null;

    while (true) {
      List<PointHistory> displayList = new java.util.ArrayList<>();
      for (PointHistory ph : myHistory) {
        if (filterFrom == null || !ph.getDate().isBefore(filterFrom)) displayList.add(ph);
      }

      cu.showStep(4, "포인트 적립/사용 내역을 출력합니다.");
      if (filterFrom != null) System.out.println("[필터] " + filterFrom + " 이후 내역");
      cu.showSeparator();
      System.out.printf("%-4s  %-10s  %-8s  %-14s  %6s  %s%n",
          "No.", "날짜", "구분", "사유", "변동", "잔여");
      if (displayList.isEmpty()) {
        System.out.println("해당 기간의 포인트 내역이 없습니다.");
      } else {
        for (int i = 0; i < displayList.size(); i++) {
          PointHistory ph = displayList.get(i);
          String sign = ph.getAmount() >= 0 ? "+" : "";
          String reason = ph.getReason().length() > 14
              ? ph.getReason().substring(0, 13) + "…" : ph.getReason();
          System.out.printf("%-4d  %-10s  %-8s  %-14s  %s%d점  %d점%n",
              i + 1, ph.getDate(), ph.getType(), reason,
              sign, ph.getAmount(), ph.getBalanceAfter());
        }
      }
      cu.showSeparator();

      System.out.println("F. 기간 필터   번호: 상세 조회   0. 돌아가기");
      cu.showPrompt();
      String input = iu.readLine();
      if (input.equals("0")) break;

      if (input.equalsIgnoreCase("F")) {
        cu.showStep(6, "조회 기간을 선택합니다.");
        System.out.println("1. 1개월   2. 3개월   3. 6개월   4. 직접 입력   0. 필터 해제");
        cu.showPrompt();
        String filterInput = iu.readLine();
        switch (filterInput) {
          case "1" -> filterFrom = LocalDate.now().minusMonths(1);
          case "2" -> filterFrom = LocalDate.now().minusMonths(3);
          case "3" -> filterFrom = LocalDate.now().minusMonths(6);
          case "4" -> filterFrom = iu.readDate("시작일 (yyyy-MM-dd): ");
          case "0" -> filterFrom = null;
          default  -> System.out.println("올바른 번호를 입력해주세요.");
        }
        continue;
      }

      try {
        int idx = Integer.parseInt(input) - 1;
        if (idx < 0 || idx >= displayList.size()) {
          System.out.println("올바른 번호를 입력해주세요.");
          continue;
        }
        PointHistory selected = displayList.get(idx);
        cu.showStep(9, "포인트 내역 상세 정보를 출력합니다.");
        cu.showSeparator();
        System.out.println("날짜     : " + selected.getDate());
        System.out.println("구분     : " + selected.getType());
        System.out.println("상세 사유: " + selected.getReason());
        System.out.println("변동 포인트: " + (selected.getAmount() >= 0 ? "+" : "") + selected.getAmount() + "점");
        System.out.println("잔여 포인트: " + selected.getBalanceAfter() + "점");
        cu.showSeparator();
      } catch (NumberFormatException e) {
        System.out.println("올바른 번호를 입력해주세요.");
      }
    }

    long daysToExpiry = java.time.temporal.ChronoUnit.DAYS.between(
        LocalDate.now(), memberPoint.getExpiryDate());
    if (daysToExpiry <= 30 && daysToExpiry >= 0) {
      System.out.printf("%d일 후 %,d포인트가 소멸 예정입니다.%n",
          daysToExpiry, memberPoint.getBalance());
    }
  }

  private void showRemainingPeriod(Membership ms) {
    cu.showSeparator();
    System.out.println("[회원권 잔여기간]");
    System.out.println("종류   : " + ms.getProductName());
    System.out.println("시작일 : " + ms.getStartDate());
    System.out.println("만료일 : " + ms.getEndDate());
    if (ms.getPauseDate() != null) {
      System.out.println("상태   : 일시정지");
      System.out.println("정지일 : " + ms.getPauseDate());
      if (ms.getResumeDate() != null) System.out.println("재개일 : " + ms.getResumeDate());
    } else {
      long remaining = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), ms.getEndDate());
      if (remaining < 0) System.out.println("상태   : 만료됨");
      else System.out.println("잔여일 : " + remaining + "일");
    }
    cu.showSeparator();
  }
}
