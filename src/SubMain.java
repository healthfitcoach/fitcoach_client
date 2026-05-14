import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubMain {

    private Scanner scanner;
    private Member currentMember;

    // 상품 카탈로그 (In-Memory)
    private List<Membership> membershipCatalog;
    private List<ExerciseProgram> programCatalog;
    private List<SportEquipment> sportEquipmentCatalog;
    private List<PT> ptCatalog;
    private List<AdditionalProduct> additionalProductCatalog;

    // 트레이너 & 기구
    private List<Trainer> trainers;
    private List<Equipment> equipments;
    private List<Notice> notices;
    private PointPolicy pointPolicy;

    // 트랜잭션 데이터
    private List<Member> members;
    private List<Membership> memberMemberships;
    private List<PT> memberPTs;
    private List<PTSchedule> ptSchedules;
    private List<Order> orders;
    private List<Payment> payments;
    private List<Attendance> attendances;
    private List<ExerciseRecord> exerciseRecords;
    private List<Point> points;
    private List<PointHistory> pointHistories;

    public SubMain() {
        this.scanner = null;
        this.currentMember = null;
        this.membershipCatalog = new ArrayList<>();
        this.programCatalog = new ArrayList<>();
        this.sportEquipmentCatalog = new ArrayList<>();
        this.ptCatalog = new ArrayList<>();
        this.additionalProductCatalog = new ArrayList<>();
        this.trainers = new ArrayList<>();
        this.equipments = new ArrayList<>();
        this.notices = new ArrayList<>();
        this.pointPolicy = null;
        this.members = new ArrayList<>();
        this.memberMemberships = new ArrayList<>();
        this.memberPTs = new ArrayList<>();
        this.ptSchedules = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.attendances = new ArrayList<>();
        this.exerciseRecords = new ArrayList<>();
        this.points = new ArrayList<>();
        this.pointHistories = new ArrayList<>();
    }

    public boolean init() {
        try {
            scanner = new Scanner(System.in);
            initMembershipCatalog();
            initProgramCatalog();
            initSportEquipmentCatalog();
            initPTCatalog();
            initAdditionalProductCatalog();
            initTrainers();
            initEquipments();
            initNotices();
            initPointPolicy();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // -------------------------
    // 초기 데이터 세팅 (initial_data.md 기준)
    // -------------------------

    private void initMembershipCatalog() {
        membershipCatalog.add(new Membership(
                "ms-product-001", "1개월권", 99000, "헬스장 전체 시설 이용 (30일)",
                "ms-product-001", null, "ACTIVE", null, null));
        membershipCatalog.add(new Membership(
                "ms-product-002", "3개월권", 270000, "헬스장 전체 시설 이용 (90일)",
                "ms-product-002", null, "ACTIVE", null, null));
        membershipCatalog.add(new Membership(
                "ms-product-003", "6개월권", 500000, "헬스장 전체 시설 이용 (180일)",
                "ms-product-003", null, "ACTIVE", null, null));
        membershipCatalog.add(new Membership(
                "ms-product-004", "1년권", 900000, "헬스장 전체 시설 이용 (365일)",
                "ms-product-004", null, "ACTIVE", null, null));
    }

    private void initProgramCatalog() {
        programCatalog.add(new ExerciseProgram(
                "prog-001", "요가", 80000, "요가 프로그램",
                "prog-001", "trainer-001", "ACTIVE", 15, 15));
        programCatalog.add(new ExerciseProgram(
                "prog-002", "줌바", 70000, "줌바 댄스 프로그램",
                "prog-002", "trainer-002", "ACTIVE", 20, 20));
        programCatalog.add(new ExerciseProgram(
                "prog-003", "다이어트 댄스", 70000, "다이어트 댄스 프로그램",
                "prog-003", "trainer-002", "ACTIVE", 20, 20));
        programCatalog.add(new ExerciseProgram(
                "prog-004", "스피닝", 90000, "스피닝 프로그램",
                "prog-004", "trainer-003", "ACTIVE", 15, 15));
    }

    private void initSportEquipmentCatalog() {
        sportEquipmentCatalog.add(new SportEquipment("se-001", "요가 매트", 35000, "고품질 요가 매트", 50, "헬스용품"));
        sportEquipmentCatalog.add(new SportEquipment("se-002", "폼롤러", 25000, "근막 이완용 폼롤러", 30, "헬스용품"));
        sportEquipmentCatalog.add(new SportEquipment("se-003", "헬스 반팔 티셔츠", 28000, "기능성 헬스 반팔 티셔츠", 100, "의류"));
        sportEquipmentCatalog.add(new SportEquipment("se-004", "운동 레깅스", 45000, "기능성 운동 레깅스", 80, "의류"));
        sportEquipmentCatalog.add(new SportEquipment("se-005", "단백질 보충제", 55000, "고단백 보충제", 40, "보충제"));
        sportEquipmentCatalog.add(new SportEquipment("se-006", "BCAA", 38000, "아미노산 보충제", 40, "보충제"));
    }

    private void initPTCatalog() {
        ptCatalog.add(new PT("pt-product-001", "PT 10회권", 350000, "PT 10회 이용권",
                "pt-product-001", null, null, 10, 10, "ACTIVE"));
        ptCatalog.add(new PT("pt-product-002", "PT 20회권", 650000, "PT 20회 이용권",
                "pt-product-002", null, null, 20, 20, "ACTIVE"));
        ptCatalog.add(new PT("pt-product-003", "PT 30회권", 900000, "PT 30회 이용권",
                "pt-product-003", null, null, 30, 30, "ACTIVE"));
    }

    private void initAdditionalProductCatalog() {
        additionalProductCatalog.add(new AdditionalProduct(
                "ap-001", "락커 이용권", 10000, "락커 30일 이용권",
                "ap-001", null, "AVAILABLE", 30));
        additionalProductCatalog.add(new AdditionalProduct(
                "ap-002", "운동복 대여권", 15000, "운동복 30일 대여권",
                "ap-002", null, "AVAILABLE", 30));
    }

    private void initTrainers() {
        trainers.add(new Trainer("trainer-001", "김민준", "경력 7년",
                "생활스포츠지도사 2급, 요가지도자 1급", "요가, 필라테스", 4.8, "trainer_01.jpg"));
        trainers.add(new Trainer("trainer-002", "이서연", "경력 5년",
                "생활스포츠지도사 2급, 줌바 인증강사", "댄스, 유산소", 4.6, "trainer_02.jpg"));
        trainers.add(new Trainer("trainer-003", "박도현", "경력 10년",
                "건강운동관리사, NSCA-CPT", "웨이트, 체형교정", 4.9, "trainer_03.jpg"));
    }

    private void initEquipments() {
        Equipment treadmill = new Equipment("eq-001", "트레드밀",
                "러닝 및 걷기 운동 기구. 속도와 경사 조절 가능.", "유산소", "AVAILABLE");
        treadmill.addExerciseMethod(new ExerciseMethod("em-001", "eq-001", "인터벌 러닝",
                "전신, 심폐기능", "중급",
                "트레드밀 위에 올라 발을 어깨 너비로 벌린다.",
                "1. 속도 6km/h로 5분 워밍업 2. 속도 12km/h로 1분 달리기 3. 속도 7km/h로 2분 걷기 4. 2~3번 8회 반복 5. 속도 5km/h로 5분 쿨다운",
                "treadmill_interval.jpg", "https://example.com/treadmill_interval.mp4"));
        equipments.add(treadmill);

        equipments.add(new Equipment("eq-002", "사이클",
                "실내 자전거 운동 기구. 저충격 유산소 운동에 적합.", "유산소", "AVAILABLE"));

        Equipment bench = new Equipment("eq-003", "벤치프레스",
                "가슴, 어깨, 삼두근 강화 기구. 바벨 또는 덤벨 사용 가능.", "근력", "AVAILABLE");
        bench.addExerciseMethod(new ExerciseMethod("em-002", "eq-003", "바벨 벤치프레스",
                "대흉근, 삼각근 전면, 삼두근", "중급",
                "벤치에 누워 등을 밀착시키고 발을 바닥에 고정한다.",
                "1. 바벨을 어깨 너비보다 약간 넓게 잡는다 2. 바벨을 가슴 중앙으로 천천히 내린다 3. 가슴에 살짝 닿으면 힘차게 밀어올린다 4. 팔꿈치를 완전히 펴지 않고 반복한다 5. 세트당 8~12회 수행",
                "bench_press.jpg", "https://example.com/bench_press.mp4"));
        equipments.add(bench);

        Equipment legPress = new Equipment("eq-004", "레그프레스",
                "하체 전반(대퇴사두근, 햄스트링) 강화 기구.", "근력", "AVAILABLE");
        legPress.addExerciseMethod(new ExerciseMethod("em-003", "eq-004", "레그프레스",
                "대퇴사두근, 햄스트링, 둔근", "초급",
                "시트에 등을 기대고 발을 플레이트 중앙에 어깨 너비로 놓는다.",
                "1. 안전 잠금장치를 해제한다 2. 무릎을 90도로 구부려 플레이트를 내린다 3. 발뒤꿈치로 밀어 플레이트를 밀어올린다 4. 무릎을 완전히 펴지 않고 반복한다 5. 세트당 10~15회 수행",
                "leg_press.jpg", "https://example.com/leg_press.mp4"));
        equipments.add(legPress);

        equipments.add(new Equipment("eq-005", "폼롤러",
                "근막 이완 및 유연성 향상을 위한 자가 마사지 도구.", "스트레칭", "AVAILABLE"));
        equipments.add(new Equipment("eq-006", "스트레칭존",
                "매트와 스트레칭 보조 기구가 구비된 전용 공간.", "스트레칭", "AVAILABLE"));
    }

    private void initNotices() {
        notices.add(new Notice("notice-001", "헬스장 이용 안내 및 주의사항",
                "안녕하세요. FitCoach 헬스장을 이용해 주셔서 감사합니다. 기구 사용 후 반드시 제자리에 정리해 주시고, 타인을 배려하는 이용 문화를 만들어 주세요. 운동 중 부상 방지를 위해 워밍업을 충분히 진행해 주십시오.",
                "공지", LocalDate.of(2025, 1, 1), "없음"));
        notices.add(new Notice("notice-002", "5월 봄맞이 회원권 할인 이벤트",
                "5월 한 달간 3개월권 이상 구매 시 10% 할인 혜택을 드립니다. 이벤트 기간: 2025년 5월 1일 ~ 5월 31일. 카운터 또는 앱에서 구매 가능합니다.",
                "이벤트", LocalDate.of(2025, 4, 15), "event_may.jpg"));
        notices.add(new Notice("notice-003", "정기 기구 점검 안내 (5월 20일)",
                "5월 20일(화) 오전 6시 ~ 오전 10시 정기 기구 점검이 진행됩니다. 해당 시간에는 일부 기구 이용이 제한될 수 있습니다. 양해 부탁드립니다.",
                "점검", LocalDate.of(2025, 5, 10), "없음"));
    }

    private void initPointPolicy() {
        pointPolicy = new PointPolicy("policy-001", 10, 5, 30, 7, 50);
    }

    // -------------------------
    // 메인 루프
    // -------------------------

    public void run() {
        System.out.println("=================================================");
        System.out.println("     FitCoach 헬스장 시스템에 오신 것을 환영합니다.");
        System.out.println("=================================================");

        while (true) {
            if (currentMember == null) {
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
        System.out.print("> ");

        String input = scanner.nextLine().trim();
        switch (input) {
            case "1"         -> handleSignUp();
            case "2"         -> handleLogin();
            case "3"         -> handleSearchEquipment();
            case "0", "exit" -> exitProgram();
            default          -> System.out.println("올바른 메뉴를 선택해주세요.");
        }
    }

    private void showMemberMenu() {
        System.out.println("\n[User System] (로그인: " + currentMember.getNickname() + ")");
        System.out.println(" 1. 회원가입          2. 구매하기");
        System.out.println(" 3. 기구 검색         4. 헬스장 출석");
        System.out.println(" 5. 운동 기록         6. 포인트 지급");
        System.out.println(" 7. 기구 검색         8. 운동 방법 조회");
        System.out.println(" 9. 공지사항         10. 내 정보");
        System.out.println("11. 내 정보 수정     12. 회원권 관리");
        System.out.println("13. 잔여기간 조회    14. 부가상품 관리");
        System.out.println("15. 포인트 내역      16. PT 일정 예약");
        System.out.println("17. 로그아웃");
        System.out.println(" 0. 종료");
        System.out.print("> ");

        String input = scanner.nextLine().trim();
        switch (input) {
            case "1"         -> handleSignUp();
            case "2"         -> handlePurchase();
            case "3"         -> handleSearchEquipment();
            case "4"         -> handleCheckAttendance();
            case "5"         -> handleRecordExercise();
            case "6"         -> handleEarnPoints();
            case "7"         -> handleSearchEquipment();
            case "8"         -> handleViewExerciseMethod();
            case "9"         -> handleViewNotice();
            case "10"        -> handleManageMyInfo();
            case "11"        -> handleUpdateMyInfo();
            case "12"        -> handleManageMembership();
            case "13"        -> handleCheckRemainingPeriod();
            case "14"        -> handleManageAdditionalProduct();
            case "15"        -> handleViewPointHistory();
            case "16"        -> handleSchedulePT();
            case "17"        -> handleLogout();
            case "0", "exit" -> exitProgram();
            default          -> System.out.println("올바른 메뉴를 선택해주세요.");
        }
    }

    // -------------------------
    // 유스케이스 핸들러 (UC01 ~ UC20)
    // -------------------------

    private void handleSignUp() {
        System.out.println("Step 1: 회원가입 입력 폼을 출력합니다.");

        String name, nickname, loginId, password, phoneNumber;
        String birthDate, physicalInfo, address, profilePicture;

        while (true) {
            System.out.println("\n[필수 항목]");
            System.out.print("이름: ");
            name = scanner.nextLine().trim();
            System.out.print("닉네임: ");
            nickname = scanner.nextLine().trim();
            System.out.print("아이디: ");
            loginId = scanner.nextLine().trim();
            System.out.print("비밀번호: ");
            password = scanner.nextLine().trim();
            System.out.print("전화번호: ");
            phoneNumber = scanner.nextLine().trim();

            System.out.println("[선택 항목] (생략 시 Enter)");
            System.out.print("생년월일 (예: 19900101): ");
            birthDate = scanner.nextLine().trim();
            System.out.print("신체정보 (예: 175cm/70kg): ");
            physicalInfo = scanner.nextLine().trim();
            System.out.print("주소: ");
            address = scanner.nextLine().trim();
            System.out.print("프로필 사진 파일명: ");
            profilePicture = scanner.nextLine().trim();

            // A1: 필수 항목 미입력 확인
            List<String> missing = new ArrayList<>();
            if (name.isEmpty())        missing.add("이름");
            if (nickname.isEmpty())    missing.add("닉네임");
            if (loginId.isEmpty())     missing.add("아이디");
            if (password.isEmpty())    missing.add("비밀번호");
            if (phoneNumber.isEmpty()) missing.add("전화번호");

            if (!missing.isEmpty()) {
                System.out.println("[미입력 항목] " + String.join(", ", missing));
                continue;
            }

            // Step 2: 아이디 중복 확인 (A2)
            System.out.println("Step 2: 아이디 중복 여부를 확인합니다.");
            final String idToCheck = loginId;
            boolean duplicate = members.stream().anyMatch(m -> m.getLoginId().equals(idToCheck));
            if (duplicate) {
                System.out.println("이미 사용 중인 아이디입니다.");
                continue;
            }

            break;
        }

        // Step 3: 약관 동의 (A3)
        System.out.println("Step 3: 서비스 이용약관 및 개인정보 처리방침을 확인합니다.");
        System.out.println("1. FitCoach 서비스 이용약관 동의 (필수)");
        System.out.println("2. 개인정보 처리방침 동의 (필수)");
        System.out.println("3. 마케팅 정보 수신 동의 (선택)");

        while (true) {
            System.out.println("필수 약관(1, 2)에 모두 동의하시면 Y를 입력하세요.");
            System.out.print("> ");
            String agree = scanner.nextLine().trim();

            if (agree.equalsIgnoreCase("Y")) break;
            System.out.println("필수 약관에 동의하셔야 가입이 가능합니다.");
        }

        // Step 4: 계정 생성
        System.out.println("Step 4: 신규 계정을 생성합니다.");

        String memberId = "member-" + String.format("%03d", members.size() + 1);
        Member newMember = new Member(memberId, loginId, password, name, nickname,
                phoneNumber, birthDate, physicalInfo, address, profilePicture, LocalDate.now());
        if (!newMember.init()) {
            System.out.println("일시적인 오류가 발생하였습니다. 잠시 후 다시 시도해주세요");
            return;
        }

        members.add(newMember);
        System.out.println("회원가입이 완료되었습니다.");
    }

    private void handleLogin() {}           // (로그인)

    private void handleLogout() {           // (로그아웃)
        currentMember = null;
        System.out.println("로그아웃 되었습니다.");
    }

    private void handlePurchase() {}        // UC02

    private void handlePurchaseMembership() {}  // UC03

    private void handlePurchaseProgram() {}     // UC04

    private void handlePurchaseEquipment() {}   // UC05

    private void handlePurchasePT() {}          // UC06

    private boolean handlePay(int amount) { return false; }  // UC07

    private void handleCheckAttendance() {} // UC08

    private void handleRecordExercise() {}  // UC09

    private void handleEarnPoints() {}      // UC10

    private void handleSearchEquipment() {} // UC11

    private void handleViewExerciseMethod() {}  // UC12

    private void handleViewNotice() {}      // UC13

    private void handleManageMyInfo() {}    // UC14

    private void handleUpdateMyInfo() {}    // UC15

    private void handleManageMembership() {}        // UC16

    private void handleCheckRemainingPeriod() {}    // UC17

    private void handleManageAdditionalProduct() {} // UC18

    private void handleViewPointHistory() {}        // UC19

    private void handleSchedulePT() {}              // UC20

    // -------------------------
    // 유틸리티
    // -------------------------

    private void exitProgram() {
        System.out.println("FitCoach 시스템을 종료합니다. 이용해 주셔서 감사합니다.");
        scanner.close();
        System.exit(0);
    }

    // Getters (테스트 및 핸들러 내부 접근용)
    public Member getCurrentMember() { return currentMember; }
    public void setCurrentMember(Member member) { this.currentMember = member; }
    public List<Member> getMembers() { return members; }
    public List<Membership> getMembershipCatalog() { return membershipCatalog; }
    public List<ExerciseProgram> getProgramCatalog() { return programCatalog; }
    public List<SportEquipment> getSportEquipmentCatalog() { return sportEquipmentCatalog; }
    public List<PT> getPtCatalog() { return ptCatalog; }
    public List<AdditionalProduct> getAdditionalProductCatalog() { return additionalProductCatalog; }
    public List<Trainer> getTrainers() { return trainers; }
    public List<Equipment> getEquipments() { return equipments; }
    public List<Notice> getNotices() { return notices; }
    public PointPolicy getPointPolicy() { return pointPolicy; }
    public List<Membership> getMemberMemberships() { return memberMemberships; }
    public List<PT> getMemberPTs() { return memberPTs; }
    public List<PTSchedule> getPtSchedules() { return ptSchedules; }
    public List<Order> getOrders() { return orders; }
    public List<Payment> getPayments() { return payments; }
    public List<Attendance> getAttendances() { return attendances; }
    public List<ExerciseRecord> getExerciseRecords() { return exerciseRecords; }
    public List<Point> getPoints() { return points; }
    public List<PointHistory> getPointHistories() { return pointHistories; }
    public Scanner getScanner() { return scanner; }
}
