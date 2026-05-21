package com.fitcoach.client.view;

import java.time.LocalDate;
import com.fitcoach.client.controller.ActivityController;
import com.fitcoach.client.controller.AuthController;
import com.fitcoach.client.controller.InfoController;
import com.fitcoach.client.controller.MemberController;
import com.fitcoach.client.controller.PTController;
import com.fitcoach.client.controller.PurchaseController;
import com.fitcoach.client.model.equipment.Equipment;
import com.fitcoach.client.model.equipment.ExerciseMethod;
import com.fitcoach.client.model.member.Member;
import com.fitcoach.client.model.notice.Notice;
import com.fitcoach.client.model.point.PointPolicy;
import com.fitcoach.client.model.product.AdditionalProduct;
import com.fitcoach.client.model.product.ExerciseProgram;
import com.fitcoach.client.model.product.Membership;
import com.fitcoach.client.model.product.PT;
import com.fitcoach.client.model.product.SportEquipment;
import com.fitcoach.client.model.schedule.Trainer;
import com.fitcoach.client.util.ConsoleUtil;
import com.fitcoach.client.util.InputUtil;
import db.DBA;

public class MainView {
  private InputUtil iu;
  private ConsoleUtil cu;

  private AuthController auth;
  private InfoController info;
  private PTController pt;
  private ActivityController activity;
  private PurchaseController purchase;
  private MemberController member;

  private DBA dba;

  private AuthView authView;
  private InfoView infoView;
  private PTView ptView;
  private ActivityView activityView;
  private PurchaseView purchaseView;
  private MemberView memberView;

  public MainView() {
    this.iu = null;
    this.cu = null;
    this.dba = null;
  }

  public boolean init() {
    // Phase 0: DB 연결
    dba = new DBA("localhost", 3306, "fitcoach", "fitcoach", "fitcoach1234");
    if (!dba.init()) {
      System.out.println("[경고] DB 연결에 실패했습니다. In-Memory 모드로 실행합니다.");
    }

    // Phase 1: 유틸 초기화
    iu = new InputUtil();
    if (!iu.init()) {
      System.out.println("시스템 초기화에 실패했습니다. 잠시 후 다시 시도해주세요.");
      return false;
    }
    cu = new ConsoleUtil();

    // Phase 2: 컨트롤러 생성
    auth     = new AuthController();
    info     = new InfoController();
    pt       = new PTController();
    purchase = new PurchaseController();
    activity = new ActivityController();

    // Phase 3: 크로스 레퍼런스 주입
    activity.setMemberMemberships(purchase.getMemberMemberships());
    purchase.setPointLists(activity.getPoints(), activity.getPointHistories());
    purchase.setPTData(pt.getTrainers(), pt.getMemberPTs(), pt.getPtSchedules());

    member = new MemberController(auth, purchase, activity, pt);

    // Phase 4: 더미 데이터 로드
    loadInitialData();

    // Phase 5: 컨트롤러 초기화 (의존성 검증)
    if (!auth.init() || !info.init() || !pt.init()
        || !activity.init() || !purchase.init() || !member.init()) {
      System.out.println("서비스 초기화에 실패했습니다. 잠시 후 다시 시도해주세요.");
      return false;
    }

    // Phase 6: 뷰 생성
    authView     = new AuthView(iu, cu, auth);
    infoView     = new InfoView(iu, cu, auth, info);
    ptView       = new PTView(iu, cu, auth, pt);
    activityView = new ActivityView(iu, cu, auth, activity);
    purchaseView = new PurchaseView(iu, cu, auth, purchase);
    memberView   = new MemberView(iu, cu, auth, member, purchaseView);

    return true;
  }

  private void loadInitialData() {
    // ── 회원 ──
    auth.getMembers().add(new Member("member-001", "123", "123", "테스터", "테스터",
        "010-0000-0000", "19900101", "170cm/65kg", "서울시", "", LocalDate.now()));

    // ── 기구 ──
    Equipment treadmill = new Equipment("eq-001", "트레드밀",
        "러닝 및 걷기 운동 기구. 속도와 경사 조절 가능.", "유산소", "AVAILABLE");
    treadmill.addExerciseMethod(new ExerciseMethod("em-001", "eq-001", "인터벌 러닝",
        "전신, 심폐기능", "중급",
        "트레드밀 위에 올라 발을 어깨 너비로 벌린다.",
        "1. 속도 6km/h로 5분 워밍업 2. 속도 12km/h로 1분 달리기 3. 속도 7km/h로 2분 걷기 4. 2~3번 8회 반복 5. 속도 5km/h로 5분 쿨다운",
        "treadmill_interval.jpg", "https://example.com/treadmill_interval.mp4"));
    info.getEquipments().add(treadmill);

    info.getEquipments().add(new Equipment("eq-002", "사이클",
        "실내 자전거 운동 기구. 저충격 유산소 운동에 적합.", "유산소", "AVAILABLE"));

    Equipment bench = new Equipment("eq-003", "벤치프레스",
        "가슴, 어깨, 삼두근 강화 기구. 바벨 또는 덤벨 사용 가능.", "근력", "AVAILABLE");
    bench.addExerciseMethod(new ExerciseMethod("em-002", "eq-003", "바벨 벤치프레스",
        "대흉근, 삼각근 전면, 삼두근", "중급",
        "벤치에 누워 등을 밀착시키고 발을 바닥에 고정한다.",
        "1. 바벨을 어깨 너비보다 약간 넓게 잡는다 2. 바벨을 가슴 중앙으로 천천히 내린다 3. 가슴에 살짝 닿으면 힘차게 밀어올린다 4. 팔꿈치를 완전히 펴지 않고 반복한다 5. 세트당 8~12회 수행",
        "bench_press.jpg", "https://example.com/bench_press.mp4"));
    info.getEquipments().add(bench);

    Equipment legPress = new Equipment("eq-004", "레그프레스",
        "하체 전반(대퇴사두근, 햄스트링) 강화 기구.", "근력", "AVAILABLE");
    legPress.addExerciseMethod(new ExerciseMethod("em-003", "eq-004", "레그프레스",
        "대퇴사두근, 햄스트링, 둔근", "초급",
        "시트에 등을 기대고 발을 플레이트 중앙에 어깨 너비로 놓는다.",
        "1. 안전 잠금장치를 해제한다 2. 무릎을 90도로 구부려 플레이트를 내린다 3. 발뒤꿈치로 밀어 플레이트를 밀어올린다 4. 무릎을 완전히 펴지 않고 반복한다 5. 세트당 10~15회 수행",
        "leg_press.jpg", "https://example.com/leg_press.mp4"));
    info.getEquipments().add(legPress);

    info.getEquipments().add(new Equipment("eq-005", "폼롤러",
        "근막 이완 및 유연성 향상을 위한 자가 마사지 도구.", "스트레칭", "AVAILABLE"));
    info.getEquipments().add(new Equipment("eq-006", "스트레칭존",
        "매트와 스트레칭 보조 기구가 구비된 전용 공간.", "스트레칭", "AVAILABLE"));

    // ── 공지사항 ──
    info.getNotices().add(new Notice("notice-001", "헬스장 이용 안내 및 주의사항",
        "안녕하세요. FitCoach 헬스장을 이용해 주셔서 감사합니다. 기구 사용 후 반드시 제자리에 정리해 주시고, 타인을 배려하는 이용 문화를 만들어 주세요.",
        "공지", LocalDate.of(2025, 1, 1), "없음"));
    info.getNotices().add(new Notice("notice-002", "5월 봄맞이 회원권 할인 이벤트",
        "5월 한 달간 3개월권 이상 구매 시 10% 할인 혜택을 드립니다. 이벤트 기간: 2025년 5월 1일 ~ 5월 31일.",
        "이벤트", LocalDate.of(2025, 4, 15), "event_may.jpg"));
    info.getNotices().add(new Notice("notice-003", "정기 기구 점검 안내 (5월 20일)",
        "5월 20일(화) 오전 6시 ~ 오전 10시 정기 기구 점검이 진행됩니다. 해당 시간에는 일부 기구 이용이 제한될 수 있습니다.",
        "점검", LocalDate.of(2025, 5, 10), "없음"));

    // ── 트레이너 ──
    pt.getTrainers().add(new Trainer("trainer-001", "김민준", "경력 7년",
        "생활스포츠지도사 2급, 요가지도자 1급", "요가, 필라테스", 4.8, "trainer_01.jpg"));
    pt.getTrainers().add(new Trainer("trainer-002", "이서연", "경력 5년",
        "생활스포츠지도사 2급, 줌바 인증강사", "댄스, 유산소", 4.6, "trainer_02.jpg"));
    pt.getTrainers().add(new Trainer("trainer-003", "박도현", "경력 10년",
        "건강운동관리사, NSCA-CPT", "웨이트, 체형교정", 4.9, "trainer_03.jpg"));

    // ── 포인트 정책 ──
    activity.setPointPolicy(new PointPolicy("policy-001", 10, 5, 30, 7, 50));

    // ── 회원권 카탈로그 ──
    purchase.getMembershipCatalog().add(new Membership("ms-product-001", "1개월권", 99000,
        "헬스장 전체 시설 이용 (30일)", "ms-product-001", null, "ACTIVE", null, null));
    purchase.getMembershipCatalog().add(new Membership("ms-product-002", "3개월권", 270000,
        "헬스장 전체 시설 이용 (90일)", "ms-product-002", null, "ACTIVE", null, null));
    purchase.getMembershipCatalog().add(new Membership("ms-product-003", "6개월권", 500000,
        "헬스장 전체 시설 이용 (180일)", "ms-product-003", null, "ACTIVE", null, null));
    purchase.getMembershipCatalog().add(new Membership("ms-product-004", "1년권", 900000,
        "헬스장 전체 시설 이용 (365일)", "ms-product-004", null, "ACTIVE", null, null));

    // ── 헬스 프로그램 카탈로그 ──
    purchase.getProgramCatalog().add(new ExerciseProgram("prog-001", "요가", 80000, "요가 프로그램",
        "prog-001", "trainer-001", "ACTIVE", 15, 15));
    purchase.getProgramCatalog().add(new ExerciseProgram("prog-002", "줌바", 70000, "줌바 댄스 프로그램",
        "prog-002", "trainer-002", "ACTIVE", 20, 20));
    purchase.getProgramCatalog().add(new ExerciseProgram("prog-003", "다이어트 댄스", 70000, "다이어트 댄스 프로그램",
        "prog-003", "trainer-002", "ACTIVE", 20, 20));
    purchase.getProgramCatalog().add(new ExerciseProgram("prog-004", "스피닝", 90000, "스피닝 프로그램",
        "prog-004", "trainer-003", "ACTIVE", 15, 15));

    // ── 운동용품 카탈로그 ──
    purchase.getSportEquipmentCatalog().add(new SportEquipment("se-001", "요가 매트", 35000, "고품질 요가 매트", 50, "헬스용품"));
    purchase.getSportEquipmentCatalog().add(new SportEquipment("se-002", "폼롤러", 25000, "근막 이완용 폼롤러", 30, "헬스용품"));
    purchase.getSportEquipmentCatalog().add(new SportEquipment("se-003", "헬스 반팔 티셔츠", 28000, "기능성 헬스 반팔 티셔츠", 100, "의류"));
    purchase.getSportEquipmentCatalog().add(new SportEquipment("se-004", "운동 레깅스", 45000, "기능성 운동 레깅스", 80, "의류"));
    purchase.getSportEquipmentCatalog().add(new SportEquipment("se-005", "단백질 보충제", 55000, "고단백 보충제", 40, "보충제"));
    purchase.getSportEquipmentCatalog().add(new SportEquipment("se-006", "BCAA", 38000, "아미노산 보충제", 40, "보충제"));

    // ── PT 카탈로그 ──
    purchase.getPtCatalog().add(new PT("pt-product-001", "PT 10회권", 350000, "PT 10회 이용권",
        "pt-product-001", null, null, 10, 10, "ACTIVE"));
    purchase.getPtCatalog().add(new PT("pt-product-002", "PT 20회권", 650000, "PT 20회 이용권",
        "pt-product-002", null, null, 20, 20, "ACTIVE"));
    purchase.getPtCatalog().add(new PT("pt-product-003", "PT 30회권", 900000, "PT 30회 이용권",
        "pt-product-003", null, null, 30, 30, "ACTIVE"));

    // ── 부가 상품 카탈로그 ──
    purchase.getAdditionalProductCatalog().add(new AdditionalProduct("ap-001", "락커 이용권", 10000,
        "락커 30일 이용권", "ap-001", null, "AVAILABLE", 30));
    purchase.getAdditionalProductCatalog().add(new AdditionalProduct("ap-002", "운동복 대여권", 15000,
        "운동복 30일 대여권", "ap-002", null, "AVAILABLE", 30));
  }

  public void run() {
    cu.showWelcome();
    while (true) {
      if (!auth.isLoggedIn()) {
        showGuestMenu();
      } else {
        showMemberMenu();
      }
    }
  }

  private void showGuestMenu() {
    System.out.println("\n[User System]");
    System.out.println("1. 회원가입");
    System.out.println("2. 로그인");
    System.out.println("3. 기구 검색");
    System.out.println("0. 종료");
    cu.showPrompt();

    String input = iu.readLine();
    switch (input) {
      case "1"         -> authView.showSignUp();
      case "2"         -> authView.showLogin();
      case "3"         -> infoView.showSearchEquipment();
      case "0", "exit" -> exitProgram();
      default          -> System.out.println("올바른 메뉴를 선택해주세요.");
    }
  }

  private void showMemberMenu() {
    System.out.println("\n[User System] (로그인: " + auth.getCurrentMember().getNickname() + ")");
    System.out.println(" 1. 구매하기          2. 기구 검색");
    System.out.println(" 3. 헬스장 출석       4. 운동 기록");
    System.out.println(" 5. 포인트 지급       6. 운동 방법 조회");
    System.out.println(" 7. 공지사항          8. 내 정보");
    System.out.println(" 9. 내 정보 수정     10. 회원권 관리");
    System.out.println("11. 잔여기간 조회    12. 부가상품 관리");
    System.out.println("13. 포인트 내역      14. PT 일정 예약");
    System.out.println("15. 로그아웃");
    System.out.println(" 0. 종료");
    cu.showPrompt();

    String input = iu.readLine();
    switch (input) {
      case "1"         -> purchaseView.showPurchase();
      case "2"         -> infoView.showSearchEquipment();
      case "3"         -> activityView.showCheckAttendance();
      case "4"         -> activityView.showRecordExercise();
      case "5"         -> activityView.showEarnPoints();
      case "6"         -> infoView.showViewExerciseMethod();
      case "7"         -> infoView.showViewNotice();
      case "8"         -> memberView.showManageMyInfo();
      case "9"         -> memberView.showUpdateMyInfo();
      case "10"        -> memberView.showManageMembership();
      case "11"        -> memberView.showCheckRemainingPeriod();
      case "12"        -> memberView.showManageAdditionalProduct();
      case "13"        -> memberView.showViewPointHistory();
      case "14"        -> ptView.showSchedulePT();
      case "15"        -> authView.showLogout();
      case "0", "exit" -> exitProgram();
      default          -> System.out.println("올바른 메뉴를 선택해주세요.");
    }
  }

  public DBA getDba() {
    return dba;
  }

  private void exitProgram() {
    System.out.println("FitCoach 시스템을 종료합니다. 이용해 주셔서 감사합니다.");
    if (dba != null) dba.disconnect();
    iu.close();
    System.exit(0);
  }
}
