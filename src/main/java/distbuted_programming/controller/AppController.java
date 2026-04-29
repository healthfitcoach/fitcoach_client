package distbuted_programming.controller;

import distbuted_programming.domain.attendance.Attendance;
import distbuted_programming.domain.attendance.ExerciseRecord;
import distbuted_programming.domain.equipment.Equipment;
import distbuted_programming.domain.equipment.ExerciseMethod;
import distbuted_programming.domain.member.Member;
import distbuted_programming.domain.notice.Notice;
import distbuted_programming.domain.order.Order;
import distbuted_programming.domain.order.Payment;
import distbuted_programming.domain.point.Point;
import distbuted_programming.domain.point.PointHistory;
import distbuted_programming.domain.point.PointPolicy;
import distbuted_programming.domain.product.AdditionalProduct;
import distbuted_programming.domain.product.ExerciseProgram;
import distbuted_programming.domain.product.Membership;
import distbuted_programming.domain.product.PT;
import distbuted_programming.domain.product.SportEquipment;
import distbuted_programming.domain.trainer.PTSchedule;
import distbuted_programming.domain.trainer.Trainer;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class AppController {

  private Member currentMember;

  public AppController() {
    initSampleData();
  }

  private void initSampleData() {
    PointPolicy.setInstance(new PointPolicy("PP001", 10, 30, 20, 50, 7));

    Trainer.add(new Trainer("T001", "김민수", "근력 트레이닝", "5년",
        "생활스포츠지도사 2급", 4.8f, "trainer1.jpg"));
    Trainer.add(new Trainer("T002", "이지영", "다이어트/유산소", "3년",
        "퍼스널트레이너 자격증", 4.5f, "trainer2.jpg"));

    LocalDate now = LocalDate.now();
    ExerciseProgram.add(new ExerciseProgram("EP001", "요가", "몸과 마음의 균형",
        "T001", 80000, 20, 15, now, now.plusMonths(3), "판매중"));
    ExerciseProgram.add(new ExerciseProgram("EP002", "줌바댄스", "신나는 라틴 댄스",
        "T002", 70000, 15, 10, now, now.plusMonths(3), "판매중"));
    ExerciseProgram.add(new ExerciseProgram("EP003", "다이어트 댄스", "칼로리 소모 특화",
        "T002", 75000, 20, 18, now, now.plusMonths(3), "판매중"));
    ExerciseProgram.add(new ExerciseProgram("EP004", "스피닝", "실내 사이클 운동",
        "T001", 65000, 12, 8, now, now.plusMonths(3), "판매중"));

    SportEquipment.add(new SportEquipment("SE001", "헬스 장갑", "헬스용품",
        25000, 50, "손 보호 헬스 장갑"));
    SportEquipment.add(new SportEquipment("SE002", "운동복 세트", "의류",
        45000, 30, "흡습속건 운동복 세트"));
    SportEquipment.add(new SportEquipment("SE003", "단백질 보충제", "보충제",
        55000, 20, "WPI 단백질 1kg"));
    SportEquipment.add(new SportEquipment("SE004", "스트레칭 밴드", "헬스용품",
        15000, 100, "탄성 스트레칭 밴드 세트"));

    Equipment.add(new Equipment("EQ001", "벤치프레스", "근력",
        "bench.jpg", "가슴 근육 단련 기구", "정상"));
    Equipment.add(new Equipment("EQ002", "스쿼트 랙", "근력",
        "squat.jpg", "하체 근육 단련 기구", "정상"));
    Equipment.add(new Equipment("EQ003", "트레드밀", "유산소",
        "treadmill.jpg", "실내 달리기 기구", "정상"));
    Equipment.add(new Equipment("EQ004", "사이클", "유산소",
        "cycle.jpg", "실내 자전거 기구", "정상"));
    Equipment.add(new Equipment("EQ005", "덤벨 랙", "근력",
        "dumbbell.jpg", "다양한 무게의 덤벨 세트", "정상"));

    ExerciseMethod.add(new ExerciseMethod("EM001", "EQ001", "벤치프레스",
        "가슴, 삼두", "중급", "바벨을 가슴 위에 위치시킨다",
        "1.바 그립 → 2.내려서 가슴 터치 → 3.밀어올리기", "bench.jpg", ""));
    ExerciseMethod.add(new ExerciseMethod("EM002", "EQ002", "스쿼트",
        "대퇴사두, 둔근", "중급", "어깨 너비로 발을 벌린다",
        "1.등을 세운다 → 2.무릎을 발 방향으로 → 3.앉았다 일어나기", "squat.jpg", ""));
    ExerciseMethod.add(new ExerciseMethod("EM003", "EQ003", "트레드밀 걷기",
        "전신 유산소", "초급", "트레드밀에 올라서서 준비한다",
        "1.속도 설정 → 2.자세 유지 → 3.팔 자연스럽게 흔들기", "treadmill.jpg", ""));
    ExerciseMethod.add(new ExerciseMethod("EM004", "EQ005", "덤벨 컬",
        "이두근", "초급", "다리는 어깨 너비, 등은 곧게 펴기",
        "1.덤벨 잡기 → 2.팔꿈치 고정 → 3.천천히 올리고 내리기", "dumbbell.jpg", ""));

    Notice.add(new Notice("N001", "시설 점검 안내",
        "2026년 5월 1일 오전 6시~8시 시설 점검이 있습니다.",
        "시설", LocalDate.of(2026, 4, 28), ""));
    Notice.add(new Notice("N002", "신규 프로그램 오픈",
        "5월부터 필라테스 클래스가 신규 오픈됩니다!",
        "프로그램", LocalDate.of(2026, 4, 25), "notice2.pdf"));
    Notice.add(new Notice("N003", "이벤트 안내",
        "5월 가정의 달 회원권 20% 할인 이벤트!",
        "이벤트", LocalDate.of(2026, 4, 20), ""));

    AdditionalProduct.add(new AdditionalProduct(
        "APT001", "", "락커 대여", 10000, 30, "판매중"));
    AdditionalProduct.add(new AdditionalProduct(
        "APT002", "", "운동복 대여", 5000, 1, "판매중"));
  }

  public void run(Scanner scanner) {
    System.out.println("========================================");
    System.out.println("     헬스장 관리 시스템 — 고객용 SW");
    System.out.println("========================================");

    boolean running = true;
    while (running) {
      System.out.println("\n[메인 메뉴]"
          + (currentMember != null ? " (로그인: " + currentMember.getNickname() + ")" : ""));
      System.out.println(" 1. UC01 회원가입        2. UC02 구매하기");
      System.out.println(" 3. UC03 회원권 구매     4. UC04 헬스 프로그램 구매");
      System.out.println(" 5. UC05 운동용품 구매   6. UC06 PT 구매");
      System.out.println(" 7. UC07 결제(안내)      8. UC08 출석 체크");
      System.out.println(" 9. UC09 운동기록       10. UC10 포인트 지급(안내)");
      System.out.println("11. UC11 기구 검색      12. UC12 운동방법 조회");
      System.out.println("13. UC13 공지사항       14. UC14 내정보 관리");
      System.out.println("15. UC15 내정보 수정    16. UC16 회원권 관리");
      System.out.println("17. UC17 잔여기간 조회  18. UC18 부가상품 관리");
      System.out.println("19. UC19 포인트 조회    20. UC20 PT 일정 예약");
      System.out.println(" 0. 종료");
      System.out.print("메뉴 선택: ");
      String choice = scanner.nextLine().trim();

      switch (choice) {
        case "1": runUC01(scanner); break;
        case "2": runUC02(scanner); break;
        case "3": runUC03(scanner); break;
        case "4": runUC04(scanner); break;
        case "5": runUC05(scanner); break;
        case "6": runUC06(scanner); break;
        case "7": runUC07(scanner); break;
        case "8": runUC08(scanner); break;
        case "9": runUC09(scanner); break;
        case "10": runUC10(scanner); break;
        case "11": runUC11(scanner); break;
        case "12": runUC12(scanner); break;
        case "13": runUC13(scanner); break;
        case "14": runUC14(scanner); break;
        case "15": runUC15(scanner); break;
        case "16": runUC16(scanner); break;
        case "17": runUC17(scanner); break;
        case "18": runUC18(scanner); break;
        case "19": runUC19(scanner); break;
        case "20": runUC20(scanner); break;
        case "0":
          running = false;
          System.out.println("시스템을 종료합니다. 감사합니다.");
          break;
        default:
          System.out.println("[안내] 올바른 메뉴 번호를 입력해주세요.");
      }
    }
  }

  // ─────────────────────────────────────────────────
  // UC01 회원가입을 한다
  // ─────────────────────────────────────────────────
  private void runUC01(Scanner scanner) {
    System.out.println("\n[UC01] 회원가입을 한다");
    System.out.println("회원가입 정보를 입력하세요.");

    // [UC01] Basic Path - 3단계: 필수 항목 입력
    System.out.print("이름: ");
    String name = scanner.nextLine().trim();
    System.out.print("닉네임: ");
    String nickname = scanner.nextLine().trim();

    // [UC01] Basic Path - 4단계: 아이디 중복 확인 (A2)
    Member proxy = new Member();
    String loginId;
    while (true) {
      System.out.print("아이디: ");
      loginId = scanner.nextLine().trim();
      if (loginId.isEmpty()) {
        System.out.println("[A1] 아이디는 필수 항목입니다.");
        continue;
      }
      if (proxy.checkLoginIdDuplicate(loginId)) {
        System.out.println("[A2] 이미 사용 중인 아이디입니다. 다른 아이디를 입력해주세요.");
      } else {
        break;
      }
    }

    System.out.print("비밀번호: ");
    String password = scanner.nextLine().trim();
    System.out.print("전화번호 (010-0000-0000): ");
    String phone = scanner.nextLine().trim();
    System.out.print("생년월일 (yyyy-MM-dd): ");
    String birthStr = scanner.nextLine().trim();
    System.out.print("신체정보 (키/몸무게, 예: 175cm/70kg): ");
    String bodyInfo = scanner.nextLine().trim();
    System.out.print("주소: ");
    String address = scanner.nextLine().trim();
    System.out.print("프로필사진 파일명 (없으면 Enter): ");
    String profileImage = scanner.nextLine().trim();

    // [UC01] Alternative Path A1 - 필수 항목 미입력
    if (name.isEmpty() || password.isEmpty() || phone.isEmpty()) {
      System.out.println("[A1] 필수 항목이 미입력되었습니다. 이름, 비밀번호, 전화번호는 필수입니다.");
      return;
    }

    // [UC01] Basic Path - 5단계: 약관 동의 (A3)
    System.out.print("[약관] 서비스 이용약관 및 개인정보 처리방침에 동의하십니까? (y/n): ");
    String agree = scanner.nextLine().trim();
    if (!agree.equalsIgnoreCase("y")) {
      // [UC01] Alternative Path A3 - 필수 약관 미동의
      System.out.println("[A3] 필수 약관에 동의하셔야 가입이 가능합니다.");
      return;
    }

    // [UC01] Basic Path - 7-8단계: 계정 생성
    try {
      LocalDate birthDate = birthStr.isEmpty()
          ? LocalDate.of(2000, 1, 1) : LocalDate.parse(birthStr);
      if (profileImage.isEmpty()) profileImage = "default.jpg";
      proxy.register(name, nickname, loginId, password, phone,
          birthDate, bodyInfo, address, profileImage);
      System.out.println("[완료] 회원가입이 완료되었습니다. 환영합니다, " + nickname + "님!");
    } catch (Exception e) {
      // [UC01] Exception E1 - 서버 오류
      System.out.println("[E1] 일시적인 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.");
    }
  }

  // ─────────────────────────────────────────────────
  // UC02 구매한다
  // ─────────────────────────────────────────────────
  private void runUC02(Scanner scanner) {
    System.out.println("\n[UC02] 구매한다");
    if (!checkLogin(scanner)) return;

    System.out.println("구매 가능한 카테고리:");
    System.out.println("1. 회원권  2. 헬스 프로그램  3. 운동용품  4. PT");
    System.out.print("카테고리 선택: ");
    String choice = scanner.nextLine().trim();

    switch (choice) {
      case "1": runUC03(scanner); break;
      case "2": runUC04(scanner); break;
      case "3": runUC05(scanner); break;
      case "4": runUC06(scanner); break;
      default:
        // [UC02] Alternative Path A1 - 판매 상품 없는 카테고리
        System.out.println("[A1] 현재 해당 카테고리에 판매 중인 상품이 없습니다.");
    }
  }

  // ─────────────────────────────────────────────────
  // UC03 회원권을 구매한다
  // ─────────────────────────────────────────────────
  private void runUC03(Scanner scanner) {
    System.out.println("\n[UC03] 회원권을 구매한다");
    if (!checkLogin(scanner)) return;

    // [UC03] Basic Path - 2단계: 회원권 목록 출력
    System.out.println("이용 가능한 회원권 목록:");
    System.out.println("1. 1개월권  -  50,000원 | 30일 이용");
    System.out.println("2. 3개월권  - 130,000원 | 90일 이용");
    System.out.println("3. 6개월권  - 230,000원 | 180일 이용");
    System.out.println("4. 1년권    - 400,000원 | 365일 이용");
    System.out.print("구매할 회원권 선택 (1-4): ");
    String choice = scanner.nextLine().trim();

    String type;
    int price;
    int days;
    switch (choice) {
      case "1": type = "1개월권"; price = 50000; days = 30; break;
      case "2": type = "3개월권"; price = 130000; days = 90; break;
      case "3": type = "6개월권"; price = 230000; days = 180; break;
      case "4": type = "1년권"; price = 400000; days = 365; break;
      default:
        // [UC03] Exception E2 - 판매 종료
        System.out.println("[E2] 해당 회원권은 현재 판매되지 않습니다.");
        return;
    }

    // [UC03] Basic Path - 6단계: 활성 회원권 보유 확인 (A1)
    Membership existing = Membership.findActiveByMemberId(currentMember.getMemberId());
    LocalDate startDate = LocalDate.now();
    if (existing != null) {
      // [UC03] Alternative Path A1 - 이미 활성 회원권 존재
      System.out.println("[A1] 현재 활성 회원권 정보: " + existing);
      System.out.println("[안내] 기존 회원권 만료 후 적용됩니다.");
      System.out.print("연장 구매를 진행하시겠습니까? (y/n): ");
      if (!scanner.nextLine().trim().equalsIgnoreCase("y")) return;
      startDate = existing.getEndDate().plusDays(1);
    } else {
      // [UC03] Alternative Path A2 - 즉시 시작
      System.out.println("[A2] 오늘 날짜(" + startDate + ")가 시작일로 자동 설정됩니다.");
    }

    System.out.println("\n[주문 확인] " + type + " | " + price + "원 | 시작일: " + startDate);
    System.out.print("결제를 진행하시겠습니까? (y/n): ");
    if (!scanner.nextLine().trim().equalsIgnoreCase("y")) return;

    Payment payment = buildPayment("회원권", type, price);
    Payment result = runPayment(scanner, payment, price);
    if (result == null) return;

    // [UC03] Basic Path - 10단계: 회원권 등록
    try {
      Membership m = new Membership(UUID.randomUUID().toString(),
          currentMember.getMemberId(), type, startDate, startDate.plusDays(days), price, "활성");
      Membership.add(m);
      System.out.println("[완료] 회원권 구매가 완료되었습니다.");
    } catch (Exception e) {
      // [UC03] Exception E1 - 등록 실패
      System.out.println("[E1] 회원권 등록에 실패하였습니다. 고객센터에 문의해주세요.");
    }
  }

  // ─────────────────────────────────────────────────
  // UC04 헬스 프로그램을 구매한다
  // ─────────────────────────────────────────────────
  private void runUC04(Scanner scanner) {
    System.out.println("\n[UC04] 헬스 프로그램을 구매한다");
    if (!checkLogin(scanner)) return;

    List<ExerciseProgram> programs = ExerciseProgram.getAll();
    if (programs.isEmpty()) {
      // [UC04] Exception E1 - 로딩 실패
      System.out.println("[E1] 프로그램 정보를 불러오는 데 실패하였습니다.");
      return;
    }

    System.out.println("이용 가능한 헬스 프로그램:");
    for (int i = 0; i < programs.size(); i++) {
      System.out.println((i + 1) + ". " + programs.get(i));
    }
    System.out.print("프로그램 선택 (번호): ");
    int idx;
    try {
      idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
      if (idx < 0 || idx >= programs.size()) throw new NumberFormatException();
    } catch (NumberFormatException e) {
      System.out.println("[오류] 올바른 번호를 입력해주세요.");
      return;
    }

    ExerciseProgram prog = programs.get(idx);

    // [UC04] Basic Path - 6단계: 잔여 정원 확인 (A1)
    if (prog.getRemainingCapacity() <= 0) {
      System.out.println("[A1] 해당 프로그램은 현재 정원이 마감되었습니다. 대기자 등록을 이용해주세요.");
      return;
    }

    System.out.println("[상세] " + prog);
    System.out.print("시작 희망일 (yyyy-MM-dd, Enter=오늘): ");
    scanner.nextLine(); // 날짜 선택 (시뮬레이션)

    System.out.println("\n[주문 확인] " + prog.getName() + " | " + prog.getPrice() + "원");
    System.out.print("결제를 진행하시겠습니까? (y/n): ");
    if (!scanner.nextLine().trim().equalsIgnoreCase("y")) return;

    Payment payment = buildPayment("헬스프로그램", prog.getProgramId(), prog.getPrice());
    Payment result = runPayment(scanner, payment, prog.getPrice());
    if (result == null) return;

    try {
      prog.purchase(prog.getProgramId());
      System.out.println("[완료] 헬스 프로그램 구매가 완료되었습니다.");
    } catch (Exception e) {
      // [UC04] Exception E2 - 등록 실패
      System.out.println("[E2] 프로그램 등록에 실패하였습니다. 고객센터에 문의해주세요.");
    }
  }

  // ─────────────────────────────────────────────────
  // UC05 운동용품을 구매한다
  // ─────────────────────────────────────────────────
  private void runUC05(Scanner scanner) {
    System.out.println("\n[UC05] 운동용품을 구매한다");
    if (!checkLogin(scanner)) return;

    List<SportEquipment> items = SportEquipment.getAll();

    // [UC05] Alternative Path A1 - 검색/필터
    System.out.print("검색어 입력 (없으면 Enter=전체 조회): ");
    String keyword = scanner.nextLine().trim();
    if (!keyword.isEmpty()) {
      items = new SportEquipment().search(keyword);
    }

    if (items.isEmpty()) {
      System.out.println("[안내] 해당 조건의 상품이 없습니다.");
      return;
    }

    System.out.println("판매 중인 운동용품:");
    for (int i = 0; i < items.size(); i++) {
      System.out.println((i + 1) + ". " + items.get(i));
    }
    System.out.print("상품 선택 (번호): ");
    int idx;
    try {
      idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
      if (idx < 0 || idx >= items.size()) throw new NumberFormatException();
    } catch (NumberFormatException e) {
      System.out.println("[오류] 올바른 번호를 입력해주세요.");
      return;
    }

    SportEquipment selected = items.get(idx);
    System.out.println("[상세] " + selected);
    System.out.print("구매 수량 (재고: " + selected.getStock() + "): ");
    int qty;
    try {
      qty = Integer.parseInt(scanner.nextLine().trim());
      if (qty <= 0 || qty > selected.getStock()) throw new NumberFormatException();
    } catch (NumberFormatException e) {
      System.out.println("[오류] 유효하지 않은 수량입니다.");
      return;
    }

    // [UC05] Alternative Path A2 - 배송지 입력
    System.out.print("배송지 주소 (Enter=회원 주소 사용): ");
    String addr = scanner.nextLine().trim();
    if (addr.isEmpty()) addr = currentMember.getAddress();
    if (addr == null || addr.isEmpty()) {
      // [UC05] Exception E2
      System.out.println("[E2] 올바른 배송지 정보를 입력해주세요.");
      return;
    }

    int totalAmount = selected.getPrice() * qty;
    System.out.println("\n[주문 확인] " + selected.getName() + " x " + qty
        + " | " + totalAmount + "원 | 배송지: " + addr);
    System.out.print("결제를 진행하시겠습니까? (y/n): ");
    if (!scanner.nextLine().trim().equalsIgnoreCase("y")) return;

    Payment payment = buildPayment("운동용품", selected.getProductId(), totalAmount);
    Payment result = runPayment(scanner, payment, totalAmount);
    if (result == null) return;

    try {
      Order o = new Order(UUID.randomUUID().toString(),
          currentMember.getMemberId(), selected.getProductId(), qty, addr,
          totalAmount, java.time.LocalDateTime.now().toString(), "주문완료");
      Order.add(o);
      selected.decreaseStock(qty);
      System.out.println("[완료] 운동용품 구매가 완료되었습니다. 주문번호: "
          + o.getOrderId().substring(0, 8));
    } catch (Exception e) {
      // [UC05] Exception E1
      System.out.println("[E1] 주문 등록에 실패하였습니다. 고객센터에 문의해주세요.");
    }
  }

  // ─────────────────────────────────────────────────
  // UC06 PT를 구매한다
  // ─────────────────────────────────────────────────
  private void runUC06(Scanner scanner) {
    System.out.println("\n[UC06] PT를 구매한다");
    if (!checkLogin(scanner)) return;

    List<Trainer> trainers = Trainer.getAll();

    // [UC06] Alternative Path A1 - 트레이너 필터/검색
    System.out.print("트레이너 전문분야 검색 (없으면 Enter=전체): ");
    String filter = scanner.nextLine().trim();
    List<Trainer> filtered = filter.isEmpty() ? trainers : new Trainer().search(filter, 0);

    if (filtered.isEmpty()) {
      System.out.println("[안내] 해당 조건의 트레이너가 없습니다.");
      return;
    }

    System.out.println("소속 트레이너 목록:");
    for (int i = 0; i < filtered.size(); i++) {
      System.out.println((i + 1) + ". " + filtered.get(i));
    }
    System.out.print("트레이너 선택 (번호): ");
    int tidx;
    try {
      tidx = Integer.parseInt(scanner.nextLine().trim()) - 1;
      if (tidx < 0 || tidx >= filtered.size()) throw new NumberFormatException();
    } catch (NumberFormatException e) {
      System.out.println("[오류] 올바른 번호를 입력해주세요.");
      return;
    }
    Trainer trainer = filtered.get(tidx);
    System.out.println("[상세] " + trainer);

    System.out.println("PT 프로그램 선택:");
    System.out.println("1. 10회권 - 500,000원  2. 20회권 - 900,000원  3. 30회권 - 1,200,000원");
    System.out.print("선택: ");
    int count;
    int ptPrice;
    switch (scanner.nextLine().trim()) {
      case "1": count = 10; ptPrice = 500000; break;
      case "2": count = 20; ptPrice = 900000; break;
      case "3": count = 30; ptPrice = 1200000; break;
      default: System.out.println("[오류] 올바른 선택을 해주세요."); return;
    }

    // [UC06] Basic Path - 7단계: 일정 선택 (A2)
    System.out.print("첫 PT 희망 날짜 (yyyy-MM-dd): ");
    String dateStr = scanner.nextLine().trim();
    System.out.print("희망 시간 (예: 10:00): ");
    String time = scanner.nextLine().trim();

    LocalDate ptDate;
    try {
      ptDate = LocalDate.parse(dateStr);
    } catch (Exception e) {
      ptDate = LocalDate.now().plusDays(1);
    }

    if (PTSchedule.isSlotTaken(trainer.getTrainerId(), ptDate, time)) {
      // [UC06] Alternative Path A2
      System.out.println("[A2] 선택하신 시간은 이미 예약되어 있습니다. 다른 일정을 선택해주세요.");
      return;
    }

    System.out.println("\n[주문 확인] 트레이너: " + trainer.getName()
        + " | " + count + "회권 | " + ptPrice + "원 | 첫 일정: " + ptDate + " " + time);
    System.out.print("결제를 진행하시겠습니까? (y/n): ");
    if (!scanner.nextLine().trim().equalsIgnoreCase("y")) return;

    Payment payment = buildPayment("PT", trainer.getTrainerId(), ptPrice);
    Payment result = runPayment(scanner, payment, ptPrice);
    if (result == null) return;

    try {
      PT pt = new PT();
      pt.setMemberId(currentMember.getMemberId());
      PT purchased = pt.purchase(trainer.getTrainerId(), count);

      PTSchedule sched = new PTSchedule();
      sched.setPtId(purchased.getPtId());
      sched.setTrainerId(trainer.getTrainerId());
      sched.setMemberId(currentMember.getMemberId());
      sched.reserve(ptDate, time);

      System.out.println("[완료] PT 구매가 완료되었습니다. 트레이너에게 신규 PT 신청 알림을 전송합니다.");
    } catch (Exception e) {
      // [UC06] Exception E2
      System.out.println("[E2] PT 이용권 등록에 실패하였습니다. 고객센터에 문의해주세요.");
    }
  }

  // ─────────────────────────────────────────────────
  // UC07 결제한다 (독립 메뉴 안내)
  // ─────────────────────────────────────────────────
  private void runUC07(Scanner scanner) {
    System.out.println("\n[UC07] 결제한다");
    if (!checkLogin(scanner)) return;
    System.out.println("[안내] 결제는 각 구매 UC(03~06, 18) 진행 중 자동으로 실행됩니다.");
  }

  private Payment runPayment(Scanner scanner, Payment payment, int amount) {
    System.out.println("\n[UC07] 결제 수단 선택:");
    System.out.println("1. 신용카드  2. 체크카드  3. 간편결제");

    Point point = Point.findByMemberId(payment.getMemberId());
    int pointBalance = (point != null) ? point.getBalance() : 0;
    System.out.println("보유 포인트: " + pointBalance + "점");

    System.out.print("결제 수단 (1-3): ");
    String method;
    switch (scanner.nextLine().trim()) {
      case "1": method = "신용카드"; break;
      case "2": method = "체크카드"; break;
      case "3": method = "간편결제"; break;
      default: method = "신용카드";
    }

    // [UC07] Alternative Path A1 - 포인트 부분 사용
    int usedPoint = 0;
    int maxUsable = Math.min(pointBalance, amount);
    System.out.print("포인트 사용 금액 (0=사용안함, 최대 " + maxUsable + "점): ");
    try {
      usedPoint = Integer.parseInt(scanner.nextLine().trim());
      usedPoint = Math.max(0, Math.min(usedPoint, maxUsable));
      if (usedPoint > 0) {
        System.out.println("[A1] 포인트 " + usedPoint + "점 사용 → 잔여 결제: "
            + (amount - usedPoint) + "원");
      }
    } catch (NumberFormatException e) {
      usedPoint = 0;
    }

    System.out.println("최종 결제 금액: " + (amount - usedPoint) + "원");
    System.out.print("[결제하기] 진행하시겠습니까? (y/n): ");
    if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
      System.out.println("[취소] 결제가 취소되었습니다.");
      return null;
    }

    try {
      Payment result = payment.pay(amount, method, usedPoint);
      System.out.println("[UC07] 결제 완료. 알림이 발송되었습니다.");
      return result;
    } catch (Exception e) {
      // [UC07] Exception E1 - PG 연결 실패
      System.out.println("[E1] 결제 서버와의 연결에 실패하였습니다. 잠시 후 다시 시도해주세요.");
      return null;
    }
  }

  // ─────────────────────────────────────────────────
  // UC08 헬스장에 출석한다
  // ─────────────────────────────────────────────────
  private void runUC08(Scanner scanner) {
    System.out.println("\n[UC08] 헬스장에 출석한다");
    if (!checkLogin(scanner)) return;

    // [UC08] Alternative Path A1 - 당일 출석 이미 완료
    if (Attendance.hasTodayAttendance(currentMember.getMemberId())) {
      System.out.println("[A1] 오늘은 이미 출석이 완료되었습니다.");
      return;
    }

    // [UC08] Basic Path - 3단계: QR 코드 생성
    try {
      String qrCode = "QR-" + currentMember.getMemberId().substring(0, 8).toUpperCase();
      System.out.println("[QR 코드 생성] " + qrCode);
      System.out.println("[스캔 중] QR 리더기 스캔 시뮬레이션...");

      // [UC08] Basic Path - 5-6단계: 유효성 검증 후 출석 완료
      new Attendance().checkIn(currentMember.getMemberId());
      System.out.println("[완료] 출석이 완료되었습니다.");
    } catch (Exception e) {
      // [UC08] Exception E1
      System.out.println("[E1] QR 코드 생성에 실패하였습니다. 잠시 후 다시 시도해주세요.");
    }
  }

  // ─────────────────────────────────────────────────
  // UC09 운동기록을 기록한다
  // ─────────────────────────────────────────────────
  private void runUC09(Scanner scanner) {
    System.out.println("\n[UC09] 운동기록을 기록한다");
    if (!checkLogin(scanner)) return;

    // [UC09] Exception E3 - 회원권 미보유
    if (Membership.findActiveByMemberId(currentMember.getMemberId()) == null) {
      System.out.println("[E3] 활성화된 회원권이 없습니다. 회원권을 먼저 구매해주세요.");
      return;
    }

    // [UC09] Basic Path - 3단계: 날짜 선택 (A1=오늘 날짜 자동)
    System.out.print("운동 날짜 (yyyy-MM-dd, Enter=오늘): ");
    String dateStr = scanner.nextLine().trim();
    LocalDate exerciseDate;
    if (dateStr.isEmpty()) {
      exerciseDate = LocalDate.now();
      System.out.println("[A1] 오늘 날짜(" + exerciseDate + ")가 자동 설정되었습니다.");
    } else {
      try {
        exerciseDate = LocalDate.parse(dateStr);
      } catch (Exception e) {
        System.out.println("[E4] 유효하지 않은 날짜 형식입니다.");
        return;
      }
    }

    System.out.print("운동 종류: ");
    String exerciseType = scanner.nextLine().trim();

    // [UC09] Exception E1 - 필수 항목 미입력
    if (exerciseType.isEmpty()) {
      System.out.println("[E1] 운동 종류와 운동 시간은 필수 입력 항목입니다.");
      return;
    }

    System.out.print("운동 시간 (분): ");
    int exerciseTime;
    try {
      exerciseTime = Integer.parseInt(scanner.nextLine().trim());
      if (exerciseTime <= 0) throw new NumberFormatException();
    } catch (NumberFormatException e) {
      System.out.println("[E4] 유효하지 않은 입력값입니다. 운동 횟수는 숫자로 작성하여 주세요.");
      return;
    }

    // [UC09] Alternative Path A2 - 복수 운동 추가 (간단화: 단일 운동 처리)
    System.out.print("세트 수 (없으면 0): ");
    int sets = 0;
    try { sets = Integer.parseInt(scanner.nextLine().trim()); } catch (NumberFormatException e) {}

    System.out.print("반복 횟수 (없으면 0): ");
    int reps = 0;
    try { reps = Integer.parseInt(scanner.nextLine().trim()); } catch (NumberFormatException e) {}

    // [UC09] Alternative Path A3 - 메모, 사진
    System.out.print("메모 (없으면 Enter): ");
    String memo = scanner.nextLine().trim();
    System.out.print("사진 파일명 (없으면 Enter): ");
    String photo = scanner.nextLine().trim();

    try {
      ExerciseRecord proxy = new ExerciseRecord();
      proxy.setMemberId(currentMember.getMemberId());
      ExerciseRecord saved = proxy.record(exerciseDate, exerciseType,
          exerciseTime, sets, reps, memo, photo);
      System.out.println("[완료] 운동 기록이 저장되었습니다: " + saved);

      // [UC09] Basic Path - 9단계: 포인트 지급 UC10 실행
      saved.receivePoint();
    } catch (Exception e) {
      // [UC09] Exception E2
      System.out.println("[E2] 운동 기록 저장에 실패하였습니다. 잠시 후 다시 시도해주세요.");
    }
  }

  // ─────────────────────────────────────────────────
  // UC10 포인트를 지급받는다 (UC09에서 자동 + 독립 안내)
  // ─────────────────────────────────────────────────
  private void runUC10(Scanner scanner) {
    System.out.println("\n[UC10] 포인트를 지급받는다");
    if (!checkLogin(scanner)) return;
    System.out.println("[안내] 포인트는 운동기록(UC09) 저장 시 자동으로 지급됩니다.");
    System.out.println("현재 " + Point.getOrCreate(currentMember.getMemberId()));
  }

  // ─────────────────────────────────────────────────
  // UC11 기구를 검색한다
  // ─────────────────────────────────────────────────
  private void runUC11(Scanner scanner) {
    System.out.println("\n[UC11] 기구를 검색한다");

    List<Equipment> equipList = Equipment.getAll();

    // [UC11] Alternative Path A1 - 기구 검색
    System.out.print("기구 이름/키워드 검색 (없으면 Enter=전체 조회): ");
    String keyword = scanner.nextLine().trim();
    if (!keyword.isEmpty()) {
      equipList = new Equipment().search(keyword);
    }

    if (equipList.isEmpty()) {
      System.out.println("[안내] 해당 기구를 찾을 수 없습니다.");
      return;
    }

    System.out.println("기구 목록:");
    for (int i = 0; i < equipList.size(); i++) {
      System.out.println((i + 1) + ". " + equipList.get(i));
    }
    System.out.print("상세 조회할 기구 선택 (번호, 0=취소): ");
    int idx;
    try {
      idx = Integer.parseInt(scanner.nextLine().trim());
      if (idx == 0) return;
      idx--;
      if (idx < 0 || idx >= equipList.size()) throw new NumberFormatException();
    } catch (NumberFormatException e) {
      System.out.println("[오류] 올바른 번호를 입력해주세요.");
      return;
    }

    try {
      Equipment selected = equipList.get(idx);
      System.out.println("[상세] " + selected);
      System.out.println("이미지: " + selected.getImage() + " | 상태: " + selected.getStatus());

      // [UC11] Alternative Path A2 - 운동방법 조회
      List<ExerciseMethod> methods =
          ExerciseMethod.findByEquipmentId(selected.getEquipmentId());
      if (!methods.isEmpty()) {
        System.out.println("\n관련 운동방법 목록:");
        for (int i = 0; i < methods.size(); i++) {
          System.out.println("  " + (i + 1) + ". " + methods.get(i));
        }
        System.out.print("운동방법 상세 조회하시겠습니까? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
          runUC12WithMethods(scanner, methods);
        }
      } else {
        System.out.println("등록된 운동방법이 없습니다.");
      }
    } catch (Exception e) {
      // [UC11] Exception E1
      System.out.println("[E1] 기구 상세 정보를 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
    }
  }

  // ─────────────────────────────────────────────────
  // UC12 운동방법을 조회한다
  // ─────────────────────────────────────────────────
  private void runUC12(Scanner scanner) {
    System.out.println("\n[UC12] 운동방법을 조회한다");
    System.out.print("운동 이름 또는 키워드: ");
    String keyword = scanner.nextLine().trim();
    System.out.print("대상 근육 (없으면 Enter): ");
    String muscle = scanner.nextLine().trim();

    List<ExerciseMethod> methods = new ExerciseMethod().search(keyword, muscle);
    if (methods.isEmpty()) {
      System.out.println("[E1] 운동방법 정보를 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
      return;
    }
    runUC12WithMethods(scanner, methods);
  }

  private void runUC12WithMethods(Scanner scanner, List<ExerciseMethod> methods) {
    System.out.println("운동방법 목록:");
    for (int i = 0; i < methods.size(); i++) {
      System.out.println((i + 1) + ". " + methods.get(i));
    }
    System.out.print("상세 조회 번호 (0=취소): ");
    int idx;
    try {
      idx = Integer.parseInt(scanner.nextLine().trim());
      if (idx == 0) return;
      idx--;
      if (idx < 0 || idx >= methods.size()) throw new NumberFormatException();
    } catch (NumberFormatException e) {
      System.out.println("[오류] 올바른 번호를 입력해주세요.");
      return;
    }

    ExerciseMethod m = methods.get(idx);
    System.out.println("\n[UC12] 운동방법 상세:");
    System.out.println("운동명: " + m.getExerciseName());
    System.out.println("대상부위: " + m.getTargetMuscle() + " | 난이도: " + m.getDifficulty());
    System.out.println("준비자세: " + m.getStartingPosition());
    System.out.println("단계별 방법: " + m.getStepByStep());
    System.out.println("참고이미지: " + m.getImage());
    if (m.getVideoUrl().isEmpty()) {
      // [UC12] Exception E2 - 영상 재생 실패
      System.out.println("[E2] 동영상을 불러오는 데 실패하였습니다. 이미지 및 텍스트 설명을 참고해주세요.");
    } else {
      System.out.println("영상URL: " + m.getVideoUrl());
    }
  }

  // ─────────────────────────────────────────────────
  // UC13 공지사항을 확인한다
  // ─────────────────────────────────────────────────
  private void runUC13(Scanner scanner) {
    System.out.println("\n[UC13] 공지사항을 확인한다");

    List<Notice> notices = Notice.getAll();
    if (notices.isEmpty()) {
      // [UC13] Exception E1
      System.out.println("[E1] 공지사항을 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
      return;
    }

    // [UC13] Basic Path - 2단계: 최신순 목록 출력
    System.out.println("공지사항 목록 (최신순):");
    for (int i = notices.size() - 1; i >= 0; i--) {
      System.out.println((notices.size() - i) + ". " + notices.get(i));
    }
    System.out.print("상세 조회할 공지 번호 (0=취소): ");
    int choice;
    try {
      choice = Integer.parseInt(scanner.nextLine().trim());
      if (choice == 0) return;
      if (choice < 1 || choice > notices.size()) throw new NumberFormatException();
    } catch (NumberFormatException e) {
      System.out.println("[오류] 올바른 번호를 입력해주세요.");
      return;
    }

    try {
      Notice selected = notices.get(notices.size() - choice);
      System.out.println("\n[상세]");
      System.out.println("제목: " + selected.getTitle());
      System.out.println("카테고리: " + selected.getCategory() + " | 작성일: " + selected.getCreatedDate());
      System.out.println("내용: " + selected.getContent());

      // [UC13] Alternative Path A1 - 첨부파일 다운로드
      if (!selected.getAttachment().isEmpty()) {
        System.out.println("첨부파일: " + selected.getAttachment());
        System.out.print("첨부파일을 다운로드하시겠습니까? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
          System.out.println("[다운로드] " + selected.getAttachment() + " 저장이 완료되었습니다.");
        }
      }

      // [UC13] Basic Path - 6단계: 읽음 처리
      selected.markAsRead(selected.getNoticeId());
      System.out.println("[읽음 처리 완료]");

      // [UC13] Alternative Path A2 - 인접 공지 이동
      System.out.print("이전/다음 공지 이동? (p=이전, n=다음, Enter=목록): ");
      String nav = scanner.nextLine().trim();
      int currentIdx = notices.size() - choice;
      if ("p".equals(nav)) {
        if (currentIdx <= 0) {
          System.out.println("[A2] 이전 공지사항이 없습니다.");
        } else {
          Notice prev = notices.get(currentIdx - 1);
          System.out.println("[이전 공지] " + prev.getTitle() + " - " + prev.getContent());
          prev.markAsRead(prev.getNoticeId());
        }
      } else if ("n".equals(nav)) {
        if (currentIdx >= notices.size() - 1) {
          System.out.println("[A2] 다음 공지사항이 없습니다.");
        } else {
          Notice next = notices.get(currentIdx + 1);
          System.out.println("[다음 공지] " + next.getTitle() + " - " + next.getContent());
          next.markAsRead(next.getNoticeId());
        }
      }
    } catch (Exception e) {
      // [UC13] Exception E2
      System.out.println("[E2] 해당 공지사항을 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
    }
  }

  // ─────────────────────────────────────────────────
  // UC14 내정보를 관리한다
  // ─────────────────────────────────────────────────
  private void runUC14(Scanner scanner) {
    System.out.println("\n[UC14] 내정보를 관리한다");
    if (!checkLogin(scanner)) return;

    try {
      System.out.println("[프로필] " + currentMember);

      Membership ms = Membership.findActiveByMemberId(currentMember.getMemberId());
      System.out.println("[회원권] " + (ms != null ? ms.getType() + " | 잔여 " + ms.getRemainingDays() + "일" : "없음"));

      Point pt = Point.getOrCreate(currentMember.getMemberId());
      System.out.println("[포인트] 잔액: " + pt.getBalance() + "점");

      System.out.println("\n세부 메뉴: 1.내정보 수정  2.회원권 관리  3.포인트 조회  0.돌아가기");
      System.out.print("선택: ");
      switch (scanner.nextLine().trim()) {
        case "1": runUC15(scanner); break;
        case "2": runUC16(scanner); break;
        case "3": runUC19(scanner); break;
        default: break;
      }
    } catch (Exception e) {
      // [UC14] Exception E1
      System.out.println("[E1] 내정보를 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
    }
  }

  // ─────────────────────────────────────────────────
  // UC15 내정보를 수정한다
  // ─────────────────────────────────────────────────
  private void runUC15(Scanner scanner) {
    System.out.println("\n[UC15] 내정보를 수정한다");
    if (!checkLogin(scanner)) return;

    System.out.println("수정 가능 항목: 닉네임, 전화번호, 비밀번호, 프로필사진, 신체정보, 주소");
    System.out.print("새 닉네임 (변경 없으면 Enter): ");
    String nickname = scanner.nextLine().trim();
    System.out.print("새 전화번호 (변경 없으면 Enter): ");
    String phone = scanner.nextLine().trim();

    // [UC15] Alternative Path A2 - 전화번호 형식 오류
    if (!phone.isEmpty() && !phone.matches("\\d{3}-\\d{4}-\\d{4}")) {
      System.out.println("[A2] 올바른 형식의 전화번호를 입력해주세요. (예: 010-1234-5678)");
      return;
    }

    String newPassword = "";
    System.out.print("비밀번호 변경하시겠습니까? (y/n): ");
    if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
      System.out.print("현재 비밀번호: ");
      String currentPw = scanner.nextLine().trim();
      if (!currentPw.equals(currentMember.getPassword())) {
        // [UC15] Exception E1 - 비밀번호 불일치
        System.out.println("[E1] 현재 비밀번호가 올바르지 않습니다.");
        return;
      }
      System.out.print("새 비밀번호: ");
      newPassword = scanner.nextLine().trim();
    }
    // [UC15] Alternative Path A3 - 비밀번호 변경 미선택 시 생략

    System.out.print("새 프로필사진 파일명 (변경 없으면 Enter): ");
    String profileImage = scanner.nextLine().trim();
    System.out.print("새 신체정보 (변경 없으면 Enter): ");
    String bodyInfo = scanner.nextLine().trim();
    System.out.print("새 주소 (변경 없으면 Enter): ");
    String address = scanner.nextLine().trim();

    try {
      currentMember.updateInfo(nickname, phone, newPassword, profileImage, bodyInfo, address);
      System.out.println("[완료] 내정보가 성공적으로 수정되었습니다.");
    } catch (Exception e) {
      // [UC15] Exception E2
      System.out.println("[E2] 정보 저장에 실패하였습니다. 잠시 후 다시 시도해주세요.");
    }
  }

  // ─────────────────────────────────────────────────
  // UC16 회원권을 관리한다
  // ─────────────────────────────────────────────────
  private void runUC16(Scanner scanner) {
    System.out.println("\n[UC16] 회원권을 관리한다");
    if (!checkLogin(scanner)) return;

    try {
      // [UC16] Basic Path - 3단계: 잔여기간 조회 UC 실행
      runUC17(scanner);

      Membership ms = Membership.findActiveByMemberId(currentMember.getMemberId());
      if (ms == null) {
        // [UC16] Alternative Path A1 - 보유 회원권 없음
        System.out.println("[A1] 현재 보유 중인 회원권이 없습니다. UC03에서 구매해주세요.");
        return;
      }

      List<AdditionalProduct> aps =
          AdditionalProduct.findByMemberId(currentMember.getMemberId());
      System.out.println("[부가상품] 보유: " + (aps.isEmpty() ? "없음" : aps.size() + "개"));
      aps.forEach(p -> System.out.println("  - " + p));

      System.out.println("\n1.이용내역 조회  2.부가상품 관리(UC18)  0.돌아가기");
      System.out.print("선택: ");
      switch (scanner.nextLine().trim()) {
        case "1":
          List<Attendance> history =
              Attendance.findAllByMemberId(currentMember.getMemberId());
          System.out.println("[이용내역] 총 " + history.size() + "회");
          history.forEach(a -> System.out.println("  " + a));
          break;
        case "2": runUC18(scanner); break;
        default: break;
      }

      // [UC16] Basic Path - 10단계: 만료 임박 확인
      if (ms.getRemainingDays() <= 7) {
        System.out.println("\n[알림] 회원권 만료 " + ms.getRemainingDays()
            + "일 전입니다. 갱신을 고려해주세요.");
      }
    } catch (Exception e) {
      // [UC16] Exception E1
      System.out.println("[E1] 회원권 정보를 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
    }
  }

  // ─────────────────────────────────────────────────
  // UC17 잔여기간을 조회한다
  // ─────────────────────────────────────────────────
  private void runUC17(Scanner scanner) {
    System.out.println("\n[UC17] 잔여기간을 조회한다");
    if (!checkLogin(scanner)) return;

    try {
      Membership ms = Membership.findActiveByMemberId(currentMember.getMemberId());
      if (ms == null) {
        // [UC17] Alternative Path A2 - 만료 상태
        System.out.println("[A2] 회원권이 만료되었습니다. UC03에서 새로 구매해주세요.");
        return;
      }

      // [UC17] Alternative Path A1 - 일시정지 상태
      if ("일시정지".equals(ms.getStatus())) {
        System.out.println("[A1] 현재 회원권이 일시정지 상태입니다.");
        System.out.println("일시정지일: " + ms.getPauseDate()
            + " | 재개예정일: " + ms.getResumeDate());
      }

      System.out.println("[잔여기간 정보] " + ms);

      // [UC17] Basic Path - 6단계: 7일 이하 경고
      if (ms.getRemainingDays() <= 7 && ms.getRemainingDays() > 0) {
        System.out.println("[경고] 회원권이 " + ms.getRemainingDays()
            + "일 후 만료됩니다! 갱신을 진행해주세요.");
      }

      System.out.print("상세 정보 조회하시겠습니까? (y/n): ");
      if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
        System.out.println("[상세] 종류: " + ms.getType()
            + " | 시작: " + ms.getStartDate()
            + " | 종료: " + ms.getEndDate()
            + " | 상태: " + ms.getStatus());
      }
    } catch (Exception e) {
      // [UC17] Exception E1
      System.out.println("[E1] 회원권 잔여기간 조회에 실패하였습니다. 잠시 후 다시 시도해주세요.");
    }
  }

  // ─────────────────────────────────────────────────
  // UC18 부가적인 상품을 관리한다
  // ─────────────────────────────────────────────────
  private void runUC18(Scanner scanner) {
    System.out.println("\n[UC18] 부가적인 상품을 관리한다");
    if (!checkLogin(scanner)) return;

    // [UC18] Exception E3 - 활성 회원권 없이 신청 시도
    if (Membership.findActiveByMemberId(currentMember.getMemberId()) == null) {
      System.out.println("[E3] 부가 상품은 활성화된 회원권 보유 시에만 신청 가능합니다.");
      return;
    }

    try {
      List<AdditionalProduct> myProducts =
          AdditionalProduct.findByMemberId(currentMember.getMemberId());
      System.out.println("[보유 부가상품] " + (myProducts.isEmpty() ? "없음" : ""));
      myProducts.forEach(p -> System.out.println("  - " + p));

      System.out.println("\n[신청 가능 부가상품]");
      System.out.println("1. 락커 대여  - 10,000원 / 30일");
      System.out.println("2. 운동복 대여 - 5,000원 / 1일");
      System.out.println("0. 취소");
      System.out.print("신청할 상품 선택: ");
      String choice = scanner.nextLine().trim();
      if ("0".equals(choice)) return;

      String apName;
      int apPrice;
      int apPeriod;
      switch (choice) {
        case "1": apName = "락커 대여"; apPrice = 10000; apPeriod = 30; break;
        case "2": apName = "운동복 대여"; apPrice = 5000; apPeriod = 1; break;
        default: System.out.println("[오류] 올바른 선택을 해주세요."); return;
      }

      // [UC18] Alternative Path A1 - 이미 동일 상품 이용 중
      boolean alreadyUsing = myProducts.stream()
          .anyMatch(p -> p.getName().equals(apName));
      if (alreadyUsing) {
        System.out.print("[A1] 이미 해당 부가 상품을 이용 중입니다. 추가 구매하시겠습니까? (y/n): ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("y")) return;
      }

      System.out.println("[주문 확인] " + apName + " | " + apPrice + "원 | " + apPeriod + "일");
      System.out.print("[결제 및 신청] 진행하시겠습니까? (y/n): ");
      if (!scanner.nextLine().trim().equalsIgnoreCase("y")) return;

      Payment payment = buildPayment("부가상품", apName, apPrice);
      Payment result = runPayment(scanner, payment, apPrice);
      if (result == null) return;

      AdditionalProduct ap = new AdditionalProduct(UUID.randomUUID().toString(),
          currentMember.getMemberId(), apName, apPrice, apPeriod, "이용중");
      AdditionalProduct.add(ap);
      System.out.println("[완료] 부가 상품 신청이 완료되었습니다. " + ap);
    } catch (Exception e) {
      // [UC18] Exception E1
      System.out.println("[E1] 부가 상품 정보를 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
    }
  }

  // ─────────────────────────────────────────────────
  // UC19 포인트를 조회한다
  // ─────────────────────────────────────────────────
  private void runUC19(Scanner scanner) {
    System.out.println("\n[UC19] 포인트를 조회한다");
    if (!checkLogin(scanner)) return;

    try {
      Point point = Point.getOrCreate(currentMember.getMemberId());

      // [UC19] Alternative Path A1 - 잔액 0
      if (point.getBalance() == 0) {
        System.out.println("[A1] 현재 보유한 포인트가 없습니다.");
        System.out.println("[안내] 운동기록(UC09) 또는 출석(UC08) 후 운동기록 저장 시 포인트가 적립됩니다.");
        return;
      }

      System.out.println("[포인트] " + point);

      List<PointHistory> histories =
          PointHistory.findByMemberId(currentMember.getMemberId());

      System.out.println("\n[포인트 내역] 최근 10건:");
      int start = Math.max(0, histories.size() - 10);
      for (int i = histories.size() - 1; i >= start; i--) {
        System.out.println("  " + histories.get(i));
      }

      // [UC19] Alternative Path A2 - 기간 필터
      System.out.print("기간 필터 설정하시겠습니까? (y/n): ");
      if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
        System.out.println("1.1개월  2.3개월  3.6개월  4.직접입력");
        System.out.print("선택: ");
        LocalDate from;
        switch (scanner.nextLine().trim()) {
          case "1": from = LocalDate.now().minusMonths(1); break;
          case "2": from = LocalDate.now().minusMonths(3); break;
          case "3": from = LocalDate.now().minusMonths(6); break;
          default:
            System.out.print("시작일 (yyyy-MM-dd): ");
            try { from = LocalDate.parse(scanner.nextLine().trim()); }
            catch (Exception ex) { from = LocalDate.now().minusMonths(1); }
        }
        List<PointHistory> filtered = new PointHistory()
            .getByPeriod(from, LocalDate.now());
        System.out.println("[필터 결과] " + filtered.size() + "건");
        filtered.forEach(h -> System.out.println("  " + h));
      }

      // [UC19] Basic Path - 10단계: 소멸 예정 안내
      long daysToExpiry = ChronoUnit.DAYS.between(LocalDate.now(), point.getExpiryDate());
      if (daysToExpiry <= 30) {
        System.out.println("\n[알림] " + daysToExpiry + "일 후 "
            + point.getBalance() + "포인트가 소멸 예정입니다.");
      }
    } catch (Exception e) {
      // [UC19] Exception E1
      System.out.println("[E1] 포인트 정보를 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
    }
  }

  // ─────────────────────────────────────────────────
  // UC20 PT 일정을 잡는다
  // ─────────────────────────────────────────────────
  private void runUC20(Scanner scanner) {
    System.out.println("\n[UC20] PT 일정을 잡는다");
    if (!checkLogin(scanner)) return;

    // [UC20] Alternative Path A1 - 등록된 PT 이용권 없음
    PT myPt = PT.findActiveByMemberId(currentMember.getMemberId());
    if (myPt == null) {
      System.out.println("[A1] 등록된 PT 이용권이 없습니다. PT를 먼저 구매해주세요. (UC06)");
      return;
    }

    try {
      Trainer trainer = new Trainer().get(myPt.getTrainerId());
      if (trainer == null) {
        // [UC20] Exception E1
        System.out.println("[E1] 트레이너 스케줄 정보를 불러오는 데 실패하였습니다.");
        return;
      }

      System.out.println("[내 PT 이용권] " + myPt);
      System.out.println("[담당 트레이너] " + trainer);

      List<PTSchedule> booked = PTSchedule.findAllByTrainerId(trainer.getTrainerId());
      System.out.println("기존 예약 슬롯:");
      if (booked.isEmpty()) {
        System.out.println("  없음");
      } else {
        booked.stream().filter(s -> !"취소".equals(s.getStatus()))
            .forEach(s -> System.out.println("  [예약됨] " + s));
      }

      System.out.print("PT 희망 날짜 (yyyy-MM-dd): ");
      String dateStr = scanner.nextLine().trim();
      System.out.print("희망 시간 (예: 10:00): ");
      String time = scanner.nextLine().trim();

      LocalDate date;
      try {
        date = LocalDate.parse(dateStr);
      } catch (Exception e) {
        System.out.println("[오류] 올바른 날짜 형식을 입력해주세요.");
        return;
      }

      if (PTSchedule.isSlotTaken(trainer.getTrainerId(), date, time)) {
        System.out.println("[안내] 선택하신 시간은 이미 예약되어 있습니다. 다른 일정을 선택해주세요.");
        return;
      }

      System.out.println("[예약 확인] 트레이너: " + trainer.getName()
          + " | " + date + " " + time);
      System.out.print("[예약 확정] 버튼 선택 (y/n): ");
      if (!scanner.nextLine().trim().equalsIgnoreCase("y")) return;

      try {
        PTSchedule sched = new PTSchedule();
        sched.setPtId(myPt.getPtId());
        sched.setTrainerId(trainer.getTrainerId());
        sched.setMemberId(currentMember.getMemberId());
        PTSchedule reserved = sched.reserve(date, time);
        System.out.println("[알림] 트레이너에게 신규 예약 알림을 전송합니다.");
        System.out.println("트레이너 수락 후 최종 확정됩니다: " + reserved);
      } catch (Exception e) {
        // [UC20] Exception E2
        System.out.println("[E2] 예약 처리 중 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.");
      }
    } catch (Exception e) {
      System.out.println("[E1] 트레이너 스케줄 정보를 불러오는 데 실패하였습니다.");
    }
  }

  // ─────────────────────────────────────────────────
  // 공통 헬퍼 메서드
  // ─────────────────────────────────────────────────
  private boolean checkLogin(Scanner scanner) {
    if (currentMember != null) return true;
    System.out.println("[로그인 필요] 로그인하세요. (UC01에서 먼저 가입해주세요)");
    System.out.print("아이디: ");
    String id = scanner.nextLine().trim();
    System.out.print("비밀번호: ");
    String pw = scanner.nextLine().trim();
    Member found = new Member().login(id, pw);
    if (found == null) {
      System.out.println("[오류] 아이디 또는 비밀번호가 올바르지 않습니다.");
      return false;
    }
    currentMember = found;
    Point.getOrCreate(currentMember.getMemberId());
    System.out.println("[로그인 완료] " + currentMember.getNickname() + "님 환영합니다!");
    return true;
  }

  private Payment buildPayment(String productType, String productId, int amount) {
    Payment p = new Payment();
    p.setMemberId(currentMember.getMemberId());
    p.setProductType(productType);
    p.setProductId(productId);
    p.setAmount(amount);
    return p;
  }
}
