import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
            initMembers();
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

    private void initMembers() {
        members.add(new Member("member-001", "123", "123", "테스터", "테스터",
                "010-0000-0000", "19900101", "170cm/65kg", "서울시", "", LocalDate.now()));
    }

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
        System.out.println(" 1. 구매하기          2. 기구 검색");
        System.out.println(" 3. 헬스장 출석       4. 운동 기록");
        System.out.println(" 5. 포인트 지급       6. 운동 방법 조회");
        System.out.println(" 7. 공지사항          8. 내 정보");
        System.out.println(" 9. 내 정보 수정     10. 회원권 관리");
        System.out.println("11. 잔여기간 조회    12. 부가상품 관리");
        System.out.println("13. 포인트 내역      14. PT 일정 예약");
        System.out.println("15. 로그아웃");
        System.out.println(" 0. 종료");
        System.out.print("> ");

        String input = scanner.nextLine().trim();
        switch (input) {
            case "1"         -> handlePurchase();
            case "2"         -> handleSearchEquipment();
            case "3"         -> handleCheckAttendance();
            case "4"         -> handleRecordExercise();
            case "5"         -> handleEarnPoints();
            case "6"         -> handleViewExerciseMethod();
            case "7"         -> handleViewNotice();
            case "8"         -> handleManageMyInfo();
            case "9"         -> handleUpdateMyInfo();
            case "10"        -> handleManageMembership();
            case "11"        -> handleCheckRemainingPeriod();
            case "12"        -> handleManageAdditionalProduct();
            case "13"        -> handleViewPointHistory();
            case "14"        -> handleSchedulePT();
            case "15"        -> handleLogout();
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

    private void handleLogin() {
        System.out.println("Step 1: 로그인 정보를 입력합니다.");
        System.out.print("아이디: ");
        String loginId = scanner.nextLine().trim();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine().trim();

        System.out.println("Step 2: 아이디와 비밀번호를 확인합니다.");
        Member found = null;
        for (Member m : members) {
            if (m.getLoginId().equals(loginId) && m.getPassword().equals(password)) {
                found = m;
                break;
            }
        }

        if (found == null) {
            System.out.println("아이디 또는 비밀번호가 올바르지 않습니다.");
            return;
        }

        if (!found.init()) {
            System.out.println("로그인 처리 중 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.");
            return;
        }

        currentMember = found;
        System.out.println(found.getNickname() + "님, 환영합니다.");
    }

    private void handleLogout() {           // (로그아웃)
        currentMember = null;
        System.out.println("로그아웃 되었습니다.");
    }

    private void handlePurchase() {
        // E1: 비로그인 상태 확인
        if (currentMember == null) {
            System.out.println("로그인이 필요한 서비스입니다.");
            return;
        }

        System.out.println("Step 1: 구매 가능한 카테고리 목록을 출력합니다.");

        while (true) {
            System.out.println("\n[구매 카테고리]");
            System.out.println("1. 회원권");
            System.out.println("2. 헬스 프로그램");
            System.out.println("3. 운동용품");
            System.out.println("4. PT");
            System.out.println("5. 이벤트 중인 상품");
            System.out.println("0. 돌아가기");
            System.out.print("> ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> {
                    if (membershipCatalog.isEmpty()) {
                        System.out.println("현재 해당 카테고리에 판매 중인 상품이 없습니다.");
                    } else {
                        System.out.println("Step 2: 선택한 카테고리의 구매 유스케이스로 이동합니다.");
                        handlePurchaseMembership();
                        return;
                    }
                }
                case "2" -> {
                    if (programCatalog.isEmpty()) {
                        System.out.println("현재 해당 카테고리에 판매 중인 상품이 없습니다.");
                    } else {
                        System.out.println("Step 2: 선택한 카테고리의 구매 유스케이스로 이동합니다.");
                        handlePurchaseProgram();
                        return;
                    }
                }
                case "3" -> {
                    if (sportEquipmentCatalog.isEmpty()) {
                        System.out.println("현재 해당 카테고리에 판매 중인 상품이 없습니다.");
                    } else {
                        System.out.println("Step 2: 선택한 카테고리의 구매 유스케이스로 이동합니다.");
                        handlePurchaseEquipment();
                        return;
                    }
                }
                case "4" -> {
                    if (ptCatalog.isEmpty()) {
                        System.out.println("현재 해당 카테고리에 판매 중인 상품이 없습니다.");
                    } else {
                        System.out.println("Step 2: 선택한 카테고리의 구매 유스케이스로 이동합니다.");
                        handlePurchasePT();
                        return;
                    }
                }
                case "5" -> System.out.println("현재 해당 카테고리에 판매 중인 상품이 없습니다.");
                case "0" -> { return; }
                default  -> System.out.println("올바른 메뉴를 선택해주세요.");
            }
        }
    }

    private void handlePurchaseMembership() {
        // Step 1: 회원권 목록 출력
        System.out.println("Step 1: 이용 가능한 회원권 목록을 출력합니다.");
        for (int i = 0; i < membershipCatalog.size(); i++) {
            Membership m = membershipCatalog.get(i);
            System.out.printf("%d. %-8s %,6d원  |  %s%n",
                    i + 1, m.getProductName(), m.getPrice(), m.getDescription());
        }
        System.out.println("0. 돌아가기");

        Membership selected = null;
        while (selected == null) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.equals("0")) return;
            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < membershipCatalog.size()) {
                    selected = membershipCatalog.get(idx);
                } else {
                    System.out.println("올바른 번호를 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("올바른 번호를 입력해주세요.");
            }
        }

        // E2: 판매 종료 확인
        if (!"ACTIVE".equals(selected.getStatus())) {
            System.out.println("해당 회원권은 현재 판매되지 않습니다.");
            return;
        }

        // Step 2: 상세 정보 출력
        System.out.println("Step 2: 선택한 회원권의 상세 정보를 출력합니다.");
        System.out.println("─────────────────────────────────");
        System.out.println("상품명    : " + selected.getProductName());
        System.out.printf("가격      : %,d원%n", selected.getPrice());
        System.out.println("설명      : " + selected.getDescription());
        System.out.println("이용 시설 : 헬스장 전체 시설");
        System.out.println("─────────────────────────────────");
        System.out.println("구매하시겠습니까? (Y/N)");
        System.out.print("> ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("Y")) return;

        // Step 3: 활성 회원권 보유 여부 확인 (A1)
        System.out.println("Step 3: 현재 활성화된 회원권 보유 여부를 확인합니다.");
        Membership active = null;
        for (Membership ms : memberMemberships) {
            if (ms.getMemberId().equals(currentMember.getMemberId()) && "ACTIVE".equals(ms.getStatus())) {
                active = ms;
                break;
            }
        }

        boolean isExtension = false;
        if (active != null) {
            System.out.println("[현재 보유 회원권]");
            System.out.println("종류   : " + active.getProductName());
            System.out.println("만료일 : " + active.getEndDate());
            System.out.println("기존 회원권 만료 후 적용됩니다.");
            System.out.println("연장 구매하시겠습니까? (Y/N)");
            System.out.print("> ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("Y")) return;
            isExtension = true;
        }

        // Step 4: 시작일 선택 (A2)
        System.out.println("Step 4: 회원권 시작일을 선택합니다.");
        System.out.println("1. 즉시 시작 (오늘: " + LocalDate.now() + ")");
        System.out.println("2. 날짜 직접 입력 (yyyy-MM-dd)");
        System.out.print("> ");

        LocalDate startDate;
        if (scanner.nextLine().trim().equals("2")) {
            LocalDate parsed = null;
            while (parsed == null) {
                System.out.print("시작일 (yyyy-MM-dd): ");
                try {
                    parsed = LocalDate.parse(scanner.nextLine().trim());
                } catch (Exception e) {
                    System.out.println("올바른 날짜 형식을 입력해주세요. (예: 2025-06-01)");
                }
            }
            startDate = parsed;
        } else {
            startDate = isExtension ? active.getEndDate().plusDays(1) : LocalDate.now();
        }

        int duration = getMembershipDurationDays(selected.getProductName());
        LocalDate endDate = startDate.plusDays(duration - 1);

        // Step 5: 주문 확인 화면
        System.out.println("Step 5: 주문 확인 화면을 출력합니다.");
        System.out.println("─────────────────────────────────");
        System.out.println("[주문 확인]");
        System.out.println("상품명    : " + selected.getProductName());
        System.out.println("시작일    : " + startDate);
        System.out.println("만료일    : " + endDate);
        System.out.printf("결제 금액  : %,d원%n", selected.getPrice());
        System.out.println("─────────────────────────────────");
        System.out.println("결제를 진행하시겠습니까? (Y/N)");
        System.out.print("> ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("Y")) return;

        // Step 6: 결제 (UC07)
        System.out.println("Step 6: 결제 유스케이스(UC07)를 실행합니다.");
        boolean paid = handlePay(selected.getPrice(), selected.getProductId(), "MEMBERSHIP");
        if (!paid) return;

        // Step 7: 회원권 등록 (E1)
        System.out.println("Step 7: 회원권을 계정에 등록합니다.");
        String newMsId = "ms-" + String.format("%03d", memberMemberships.size() + 1);
        Membership newMs = new Membership(
                selected.getProductId(), selected.getProductName(),
                selected.getPrice(), selected.getDescription(),
                newMsId, currentMember.getMemberId(), "ACTIVE", startDate, endDate);
        if (!newMs.init()) {
            System.out.println("회원권 등록에 실패하였습니다. 고객센터에 문의해주세요.");
            return;
        }
        memberMemberships.add(newMs);
        System.out.println("회원권 구매가 완료되었습니다.");
    }

    private int getMembershipDurationDays(String productName) {
        return switch (productName) {
            case "1개월권" -> 30;
            case "3개월권" -> 90;
            case "6개월권" -> 180;
            case "1년권"   -> 365;
            default        -> 30;
        };
    }

    private void handlePurchaseProgram() {
        // Step 1: 헬스 프로그램 목록 출력
        System.out.println("Step 1: 이용 가능한 헬스 프로그램 목록을 출력합니다.");
        for (int i = 0; i < programCatalog.size(); i++) {
            ExerciseProgram p = programCatalog.get(i);
            Trainer trainer = findTrainer(p.getInstructorId());
            String trainerName = (trainer != null) ? trainer.getName() : "미정";
            System.out.printf("%d. %-14s %,6d원 | 담당: %-6s | 잔여: %d/%d명%n",
                    i + 1, p.getProductName(), p.getPrice(),
                    trainerName, p.getRemainingCapacity(), p.getCapacity());
        }
        System.out.println("0. 돌아가기");

        ExerciseProgram selected = null;
        while (selected == null) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.equals("0")) return;
            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < programCatalog.size()) {
                    selected = programCatalog.get(idx);
                } else {
                    System.out.println("올바른 번호를 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("올바른 번호를 입력해주세요.");
            }
        }

        // Step 2: 상세 정보 출력
        Trainer trainer = findTrainer(selected.getInstructorId());
        String trainerName = (trainer != null) ? trainer.getName() : "미정";
        System.out.println("Step 2: 선택한 프로그램의 상세 정보를 출력합니다.");
        System.out.println("─────────────────────────────────");
        System.out.println("프로그램명 : " + selected.getProductName());
        System.out.printf("가격       : %,d원%n", selected.getPrice());
        System.out.println("설명       : " + selected.getDescription());
        System.out.println("담당 강사  : " + trainerName);
        System.out.println("정원       : " + selected.getCapacity() + "명");
        System.out.println("잔여 정원  : " + selected.getRemainingCapacity() + "명");
        System.out.println("─────────────────────────────────");
        System.out.println("구매하시겠습니까? (Y/N)");
        System.out.print("> ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("Y")) return;

        // Step 3: 잔여 정원 확인 (A1)
        System.out.println("Step 3: 해당 프로그램의 잔여 정원을 확인합니다.");
        if (selected.getRemainingCapacity() <= 0) {
            System.out.println("해당 프로그램은 현재 정원이 마감되었습니다.");
            System.out.println("대기자 등록은 카운터에 문의해주세요.");
            return;
        }

        // Step 4: 시작 희망일 선택
        System.out.println("Step 4: 프로그램 시작 희망일을 선택합니다.");
        System.out.println("1. 즉시 시작 (오늘: " + LocalDate.now() + ")");
        System.out.println("2. 날짜 직접 입력 (yyyy-MM-dd)");
        System.out.print("> ");

        LocalDate startDate;
        if (scanner.nextLine().trim().equals("2")) {
            LocalDate parsed = null;
            while (parsed == null) {
                System.out.print("시작 희망일 (yyyy-MM-dd): ");
                try {
                    parsed = LocalDate.parse(scanner.nextLine().trim());
                } catch (Exception e) {
                    System.out.println("올바른 날짜 형식을 입력해주세요. (예: 2025-06-01)");
                }
            }
            startDate = parsed;
        } else {
            startDate = LocalDate.now();
        }

        // Step 5: 주문 확인 화면
        System.out.println("Step 5: 주문 확인 화면을 출력합니다.");
        System.out.println("─────────────────────────────────");
        System.out.println("[주문 확인]");
        System.out.println("프로그램명 : " + selected.getProductName());
        System.out.println("담당 강사  : " + trainerName);
        System.out.println("시작 희망일: " + startDate);
        System.out.printf("결제 금액  : %,d원%n", selected.getPrice());
        System.out.println("─────────────────────────────────");
        System.out.println("결제를 진행하시겠습니까? (Y/N)");
        System.out.print("> ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("Y")) return;

        // Step 6: 결제 (UC07)
        System.out.println("Step 6: 결제 유스케이스(UC07)를 실행합니다.");
        boolean paid = handlePay(selected.getPrice(), selected.getProductId(), "PROGRAM");
        if (!paid) return;

        // Step 7: 프로그램 등록 (E2)
        System.out.println("Step 7: 프로그램을 계정에 등록합니다.");
        String orderId = "order-" + String.format("%03d", orders.size() + 1);
        Order order = new Order(orderId, currentMember.getMemberId(), selected.getProductId(),
                1, selected.getPrice(), "", "COMPLETED", LocalDateTime.now());
        if (!order.init()) {
            System.out.println("프로그램 등록에 실패하였습니다. 고객센터에 문의해주세요.");
            return;
        }
        orders.add(order);
        selected.setRemainingCapacity(selected.getRemainingCapacity() - 1);
        System.out.println("헬스 프로그램 구매가 완료되었습니다.");
    }

    private Trainer findTrainer(String trainerId) {
        for (Trainer t : trainers) {
            if (t.getTrainerId().equals(trainerId)) return t;
        }
        return null;
    }

    private void handlePurchaseEquipment() {
        // Step 1: 운동용품 목록 출력 (A1: 필터 포함)
        System.out.println("Step 1: 판매 중인 운동용품 목록을 출력합니다.");
        List<SportEquipment> displayList = new ArrayList<>(sportEquipmentCatalog);

        System.out.println("F. 카테고리 필터 / 검색어 입력   0. 돌아가기");
        printSportEquipmentList(displayList);

        // A1: 검색/필터 루프
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) return;

            if (input.equalsIgnoreCase("F")) {
                System.out.println("검색어 또는 카테고리(헬스용품/의류/보충제)를 입력하세요.");
                System.out.print("> ");
                String keyword = scanner.nextLine().trim();
                displayList = new ArrayList<>();
                for (SportEquipment se : sportEquipmentCatalog) {
                    if (se.getProductName().contains(keyword) || se.getCategory().contains(keyword)) {
                        displayList.add(se);
                    }
                }
                if (displayList.isEmpty()) {
                    System.out.println("검색 결과가 없습니다. 전체 목록을 표시합니다.");
                    displayList = new ArrayList<>(sportEquipmentCatalog);
                }
                printSportEquipmentList(displayList);
                continue;
            }

            // 상품 선택
            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx < 0 || idx >= displayList.size()) {
                    System.out.println("올바른 번호를 입력해주세요.");
                    continue;
                }
                SportEquipment selected = displayList.get(idx);

                // Step 2: 상세 정보 출력
                System.out.println("Step 2: 선택한 상품의 상세 정보를 출력합니다.");
                System.out.println("─────────────────────────────────");
                System.out.println("상품명   : " + selected.getProductName());
                System.out.println("카테고리 : " + selected.getCategory());
                System.out.printf("가격     : %,d원%n", selected.getPrice());
                System.out.println("설명     : " + selected.getDescription());
                System.out.println("재고     : " + selected.getStock() + "개");
                System.out.println("─────────────────────────────────");

                // Step 3: 수량 입력
                System.out.println("Step 3: 구매 수량을 입력합니다.");
                int quantity = 0;
                while (quantity <= 0) {
                    System.out.print("수량: ");
                    try {
                        quantity = Integer.parseInt(scanner.nextLine().trim());
                        if (quantity <= 0) {
                            System.out.println("1 이상의 수량을 입력해주세요.");
                        } else if (quantity > selected.getStock()) {
                            System.out.println("재고가 부족합니다. (현재 재고: " + selected.getStock() + "개)");
                            quantity = 0;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("숫자를 입력해주세요.");
                    }
                }

                // Step 4: 배송지 입력 (A2, E2)
                System.out.println("Step 4: 배송지 정보를 입력합니다.");
                System.out.println("1. 기존 배송지 사용 (" + currentMember.getAddress() + ")");
                System.out.println("2. 새 배송지 입력");
                System.out.print("> ");
                String addrChoice = scanner.nextLine().trim();

                String shippingAddress;
                if (addrChoice.equals("1")) {
                    shippingAddress = currentMember.getAddress();
                    if (shippingAddress == null || shippingAddress.isEmpty()) {
                        System.out.println("올바른 배송지 정보를 입력해주세요.");
                        return;
                    }
                } else {
                    System.out.print("배송지 주소: ");
                    shippingAddress = scanner.nextLine().trim();
                    if (shippingAddress.isEmpty()) {
                        System.out.println("올바른 배송지 정보를 입력해주세요.");
                        return;
                    }
                }

                int totalAmount = selected.getPrice() * quantity;

                // Step 5: 주문 확인 화면
                System.out.println("Step 5: 주문 확인 화면을 출력합니다.");
                System.out.println("─────────────────────────────────");
                System.out.println("[주문 확인]");
                System.out.println("상품명   : " + selected.getProductName());
                System.out.println("수량     : " + quantity + "개");
                System.out.println("배송지   : " + shippingAddress);
                System.out.printf("결제 금액 : %,d원%n", totalAmount);
                System.out.println("─────────────────────────────────");
                System.out.println("결제를 진행하시겠습니까? (Y/N)");
                System.out.print("> ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("Y")) return;

                // Step 6: 결제 (UC07)
                System.out.println("Step 6: 결제 유스케이스(UC07)를 실행합니다.");
                boolean paid = handlePay(totalAmount, selected.getProductId(), "SPORT_EQUIPMENT");
                if (!paid) return;

                // Step 7: 주문 등록 (E1)
                System.out.println("Step 7: 주문을 등록합니다.");
                String orderId = "order-" + String.format("%03d", orders.size() + 1);
                Order order = new Order(orderId, currentMember.getMemberId(), selected.getProductId(),
                        quantity, totalAmount, shippingAddress, "COMPLETED", LocalDateTime.now());
                if (!order.init()) {
                    System.out.println("주문 등록에 실패하였습니다. 고객센터에 문의해주세요.");
                    return;
                }
                orders.add(order);
                selected.setStock(selected.getStock() - quantity);
                System.out.println("운동용품 구매가 완료되었습니다.");
                System.out.println("주문 번호: " + orderId);
                return;

            } catch (NumberFormatException e) {
                System.out.println("올바른 번호를 입력해주세요.");
            }
        }
    }

    private void printSportEquipmentList(List<SportEquipment> list) {
        for (int i = 0; i < list.size(); i++) {
            SportEquipment se = list.get(i);
            System.out.printf("%d. [%s] %-14s %,6d원 | 재고: %d개%n",
                    i + 1, se.getCategory(), se.getProductName(), se.getPrice(), se.getStock());
        }
    }

    private void handlePurchasePT() {
        // Step 1: 트레이너 목록 출력 (A1: 필터 포함)
        System.out.println("Step 1: 소속 트레이너 목록을 출력합니다.");
        List<Trainer> displayTrainers = new ArrayList<>(trainers);
        printTrainerList(displayTrainers);
        System.out.println("F. 전문 분야 필터   0. 돌아가기");

        Trainer selectedTrainer = null;
        while (selectedTrainer == null) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) return;

            if (input.equalsIgnoreCase("F")) {
                System.out.print("전문 분야 키워드 입력 (예: 요가, 웨이트): ");
                String keyword = scanner.nextLine().trim();
                displayTrainers = new ArrayList<>();
                for (Trainer t : trainers) {
                    if (t.getSpecialty().contains(keyword)) displayTrainers.add(t);
                }
                if (displayTrainers.isEmpty()) {
                    System.out.println("해당 조건의 트레이너가 없습니다. 전체 목록을 표시합니다.");
                    displayTrainers = new ArrayList<>(trainers);
                }
                printTrainerList(displayTrainers);
                System.out.println("F. 전문 분야 필터   0. 돌아가기");
                continue;
            }

            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < displayTrainers.size()) {
                    selectedTrainer = displayTrainers.get(idx);
                } else {
                    System.out.println("올바른 번호를 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("올바른 번호를 입력해주세요.");
            }
        }

        // Step 2: 트레이너 상세 프로필 + PT 프로그램 출력
        System.out.println("Step 2: 선택한 트레이너의 상세 프로필을 출력합니다.");
        System.out.println("─────────────────────────────────");
        System.out.println("이름      : " + selectedTrainer.getName());
        System.out.println("경력      : " + selectedTrainer.getCareer());
        System.out.println("자격증    : " + selectedTrainer.getCertification());
        System.out.println("전문 분야 : " + selectedTrainer.getSpecialty());
        System.out.printf("평점      : %.1f%n", selectedTrainer.getRating());
        System.out.println("─────────────────────────────────");
        System.out.println("[PT 프로그램]");
        for (int i = 0; i < ptCatalog.size(); i++) {
            PT pt = ptCatalog.get(i);
            System.out.printf("%d. %-10s %,d원%n", i + 1, pt.getProductName(), pt.getPrice());
        }
        System.out.println("0. 돌아가기");

        PT selectedPT = null;
        while (selectedPT == null) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.equals("0")) return;
            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < ptCatalog.size()) {
                    selectedPT = ptCatalog.get(idx);
                } else {
                    System.out.println("올바른 번호를 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("올바른 번호를 입력해주세요.");
            }
        }

        // Step 3: 트레이너 스케줄 출력 (E3)
        System.out.println("Step 3: 트레이너의 예약 가능한 스케줄을 출력합니다.");
        LocalTime[] timeSlots = {LocalTime.of(10, 0), LocalTime.of(14, 0), LocalTime.of(18, 0)};
        LocalDate today = LocalDate.now();

        System.out.println("날짜             시간    상태");
        System.out.println("─────────────────────────────────");
        int slotNo = 1;
        List<LocalDate> slotDates = new ArrayList<>();
        List<LocalTime> slotTimes = new ArrayList<>();

        for (int d = 1; d <= 7; d++) {
            LocalDate date = today.plusDays(d);
            for (LocalTime time : timeSlots) {
                boolean booked = false;
                for (PTSchedule s : ptSchedules) {
                    if (s.getTrainerId().equals(selectedTrainer.getTrainerId())
                            && s.getDate().equals(date) && s.getTime().equals(time)) {
                        booked = true;
                        break;
                    }
                }
                String status = booked ? "[예약됨]" : "[예약 가능]";
                System.out.printf("%2d. %s  %s  %s%n", slotNo, date, time, status);
                slotDates.add(date);
                slotTimes.add(time);
                slotNo++;
            }
        }
        System.out.println("0. 돌아가기");

        // Step 4: 일정 선택 (A2)
        LocalDate chosenDate = null;
        LocalTime chosenTime = null;
        while (chosenDate == null) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.equals("0")) return;
            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx < 0 || idx >= slotDates.size()) {
                    System.out.println("올바른 번호를 입력해주세요.");
                    continue;
                }
                LocalDate candidateDate = slotDates.get(idx);
                LocalTime candidateTime = slotTimes.get(idx);

                // A2: 이미 예약된 슬롯 확인
                boolean alreadyBooked = false;
                for (PTSchedule s : ptSchedules) {
                    if (s.getTrainerId().equals(selectedTrainer.getTrainerId())
                            && s.getDate().equals(candidateDate)
                            && s.getTime().equals(candidateTime)) {
                        alreadyBooked = true;
                        break;
                    }
                }
                if (alreadyBooked) {
                    System.out.println("선택하신 시간은 이미 예약되어 있습니다.");
                    continue;
                }
                chosenDate = candidateDate;
                chosenTime = candidateTime;
            } catch (NumberFormatException e) {
                System.out.println("올바른 번호를 입력해주세요.");
            }
        }

        // Step 5: 주문 확인 화면
        System.out.println("Step 5: 주문 확인 화면을 출력합니다.");
        System.out.println("─────────────────────────────────");
        System.out.println("[주문 확인]");
        System.out.println("트레이너  : " + selectedTrainer.getName());
        System.out.println("PT 프로그램: " + selectedPT.getProductName());
        System.out.println("첫 일정   : " + chosenDate + " " + chosenTime);
        System.out.printf("결제 금액  : %,d원%n", selectedPT.getPrice());
        System.out.println("─────────────────────────────────");
        System.out.println("결제를 진행하시겠습니까? (Y/N)");
        System.out.print("> ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("Y")) return;

        // Step 6: 결제 (UC07)
        System.out.println("Step 6: 결제 유스케이스(UC07)를 실행합니다.");
        boolean paid = handlePay(selectedPT.getPrice(), selectedPT.getProductId(), "PT");
        if (!paid) return;

        // Step 7: PT 이용권 등록 (E2)
        System.out.println("Step 7: PT 이용권을 계정에 등록합니다.");
        String newPtId = "pt-" + String.format("%03d", memberPTs.size() + 1);
        PT newPT = new PT(selectedPT.getProductId(), selectedPT.getProductName(),
                selectedPT.getPrice(), selectedPT.getDescription(),
                newPtId, currentMember.getMemberId(), selectedTrainer.getTrainerId(),
                selectedPT.getTotalCount(), selectedPT.getRemainingCount(), "ACTIVE");
        if (!newPT.init()) {
            System.out.println("PT 이용권 등록에 실패하였습니다. 고객센터에 문의해주세요.");
            return;
        }

        // 첫 PT 일정 등록
        String scheduleId = "sched-" + String.format("%03d", ptSchedules.size() + 1);
        PTSchedule firstSchedule = new PTSchedule(scheduleId, newPtId,
                currentMember.getMemberId(), selectedTrainer.getTrainerId(),
                chosenDate, chosenTime, "RESERVED");
        if (!firstSchedule.init()) {
            System.out.println("PT 이용권 등록에 실패하였습니다. 고객센터에 문의해주세요.");
            return;
        }

        memberPTs.add(newPT);
        ptSchedules.add(firstSchedule);
        System.out.println("[알림] " + selectedTrainer.getName() + " 트레이너에게 신규 PT 신청 알림을 전송했습니다.");
        System.out.println("PT 구매가 완료되었습니다.");
    }

    private void printTrainerList(List<Trainer> list) {
        for (int i = 0; i < list.size(); i++) {
            Trainer t = list.get(i);
            System.out.printf("%d. %-6s | 전문: %-14s | %s | 평점: %.1f%n",
                    i + 1, t.getName(), t.getSpecialty(), t.getCareer(), t.getRating());
        }
    }

    private boolean handlePay(int amount, String productId, String productType) {
        System.out.println("Step 1: 결제 수단 선택 화면을 출력합니다.");
        System.out.println("1. 신용카드");
        System.out.println("2. 체크카드");
        System.out.println("3. 간편결제");
        System.out.println("0. 취소");
        System.out.print("> ");

        String methodInput = scanner.nextLine().trim();
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
        scanner.nextLine();

        // Step 2: 포인트 잔액 조회 (A1)
        System.out.println("Step 2: 보유 포인트 잔액을 조회합니다.");
        Point memberPoint = null;
        for (Point p : points) {
            if (p.getMemberId().equals(currentMember.getMemberId())) {
                memberPoint = p;
                break;
            }
        }
        int pointBalance = (memberPoint != null) ? memberPoint.getBalance() : 0;
        int usedPoints = 0;
        int finalAmount = amount;

        System.out.printf("보유 포인트: %,d점%n", pointBalance);
        if (pointBalance > 0) {
            System.out.println("포인트를 사용하시겠습니까? (Y/N)");
            System.out.print("> ");
            if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
                while (true) {
                    System.out.printf("사용할 포인트 (0 ~ %,d): ", pointBalance);
                    try {
                        usedPoints = Integer.parseInt(scanner.nextLine().trim());
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

        // Step 3: 최종 결제 확인
        System.out.println("─────────────────────────────────");
        System.out.printf("결제 수단  : %s%n", paymentMethod);
        System.out.printf("사용 포인트: %,d점%n", usedPoints);
        System.out.printf("최종 금액  : %,d원%n", finalAmount);
        System.out.println("─────────────────────────────────");
        System.out.println("[결제하기] 진행하시려면 Y를 입력하세요.");
        System.out.print("> ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("Y")) {
            System.out.println("결제가 취소되었습니다.");
            return false;
        }

        // 결제 처리
        String paymentId = "pay-" + String.format("%03d", payments.size() + 1);
        Payment payment = new Payment(paymentId, currentMember.getMemberId(), productId, productType,
                paymentMethod, finalAmount, usedPoints, "COMPLETED", LocalDateTime.now());
        if (!payment.init()) {
            System.out.println("결제 서버와의 연결에 실패하였습니다. 잠시 후 다시 시도해주세요.");
            return false;
        }
        payments.add(payment);

        // 포인트 차감
        if (usedPoints > 0 && memberPoint != null) {
            memberPoint.setBalance(memberPoint.getBalance() - usedPoints);
            String histId = "ph-" + String.format("%03d", pointHistories.size() + 1);
            pointHistories.add(new PointHistory(histId, currentMember.getMemberId(),
                    "사용", -usedPoints, productType + " 결제 포인트 사용",
                    LocalDate.now(), memberPoint.getBalance()));
        }

        System.out.println("Step 3: 결제가 완료되었습니다.");
        return true;
    }

    private void handleCheckAttendance() {
        // Step 2: 당일 출석 여부 확인 (A1)
        System.out.println("Step 2: 당일 출석 여부를 확인합니다.");
        LocalDate today = LocalDate.now();
        for (Attendance a : attendances) {
            if (a.getMemberId().equals(currentMember.getMemberId())
                    && a.getAttendanceDateTime().toLocalDate().equals(today)) {
                System.out.println("오늘은 이미 출석이 완료되었습니다.");
                return;
            }
        }

        // Step 3: QR 코드 생성 (E1)
        System.out.println("Step 3: 출석 QR 코드를 생성합니다.");
        String qrCode = "QR-" + currentMember.getMemberId() + "-" + today;
        if (qrCode.isEmpty()) {
            System.out.println("QR 코드 생성에 실패하였습니다. 잠시 후 다시 시도해주세요.");
            return;
        }

        while (true) {
            System.out.println("┌─────────────────────────────────────┐");
            System.out.println("│           [출석 QR 코드]              │");
            System.out.printf( "│  %-35s│%n", qrCode);
            System.out.println("└─────────────────────────────────────┘");
            System.out.println("Enter: 스캔   R: QR 재발급");
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            // E2: QR 코드 스캔 시간 초과 → 재발급
            if (input.equalsIgnoreCase("R")) {
                System.out.println("QR 코드가 만료되었습니다. 새로운 QR 코드를 발급해주세요.");
                qrCode = "QR-" + currentMember.getMemberId() + "-" + today + "-" + System.currentTimeMillis() % 10000;
                System.out.println("새로운 QR 코드가 발급되었습니다.");
                continue;
            }

            // Step 5: QR 유효성 검증 (E3)
            System.out.println("Step 5: QR 코드의 유효성을 검증합니다.");
            if (!qrCode.startsWith("QR-" + currentMember.getMemberId())) {
                System.out.println("유효하지 않은 QR 코드입니다. 앱에서 QR 코드를 다시 확인해주세요.");
                return;
            }
            break;
        }

        // Step 6: 출석 완료
        String attendanceId = "att-" + String.format("%03d", attendances.size() + 1);
        Attendance attendance = new Attendance(attendanceId, currentMember.getMemberId(),
                LocalDateTime.now(), "QR");
        if (!attendance.init()) {
            System.out.println("QR 코드 생성에 실패하였습니다. 잠시 후 다시 시도해주세요.");
            return;
        }
        attendances.add(attendance);
        System.out.println("출석이 완료되었습니다.");
    }

    private void handleRecordExercise() {
        // E3: 활성 회원권 확인
        boolean hasActive = false;
        for (Membership ms : memberMemberships) {
            if (ms.getMemberId().equals(currentMember.getMemberId())
                    && "ACTIVE".equals(ms.getStatus())) {
                hasActive = true;
                break;
            }
        }
        if (!hasActive) {
            System.out.println("활성화된 회원권이 없습니다. 회원권을 먼저 구매해주세요.");
            return;
        }

        // Step 2: 운동 기록 입력 화면 출력
        System.out.println("Step 2: 운동 기록 입력 화면을 출력합니다.");

        // Step 3: 날짜 선택 (A1: 오늘 자동)
        System.out.println("Step 3: 운동 날짜를 선택합니다.");
        System.out.println("1. 오늘 날짜 자동 선택 (" + LocalDate.now() + ")");
        System.out.println("2. 날짜 직접 입력 (yyyy-MM-dd)");
        System.out.print("> ");

        LocalDate exerciseDate;
        if (scanner.nextLine().trim().equals("2")) {
            LocalDate parsed = null;
            while (parsed == null) {
                System.out.print("날짜 (yyyy-MM-dd): ");
                try { parsed = LocalDate.parse(scanner.nextLine().trim()); }
                catch (Exception e) { System.out.println("올바른 날짜 형식을 입력해주세요."); }
            }
            exerciseDate = parsed;
        } else {
            exerciseDate = LocalDate.now();
        }

        int totalExerciseTime = 0;
        List<ExerciseRecord> sessionRecords = new ArrayList<>();

        // Step 4~5: 운동 입력 루프 (A2: 복수 추가)
        while (true) {
            System.out.println("Step 4: 수행한 운동 종류를 입력합니다.");
            System.out.print("운동 종류 (예: 벤치프레스, 러닝): ");
            String exerciseType = scanner.nextLine().trim();

            // E1: 필수 항목 미입력
            if (exerciseType.isEmpty()) {
                System.out.println("운동 종류와 운동 시간은 필수 입력 항목입니다.");
                return;
            }

            System.out.println("Step 5: 운동 세부 정보를 입력합니다.");
            int exerciseTime = readPositiveInt("운동 시간 (분)");
            int sets         = readPositiveInt("세트 수");
            int reps         = readPositiveInt("반복 횟수");
            totalExerciseTime += exerciseTime;

            // Step 7: 메모/사진 첨부 (A3: 선택)
            System.out.println("Step 7: 추가 메모 또는 사진을 첨부합니다. (생략 시 Enter)");
            System.out.print("메모: ");
            String memo = scanner.nextLine().trim();
            System.out.print("사진 파일명: ");
            String photo = scanner.nextLine().trim();

            String recordId = "rec-" + String.format("%03d", exerciseRecords.size() + sessionRecords.size() + 1);
            ExerciseRecord record = new ExerciseRecord(recordId, currentMember.getMemberId(),
                    exerciseDate, exerciseType, exerciseTime, sets, reps, memo, photo);
            sessionRecords.add(record);

            // A2: 운동 추가 여부
            System.out.println("운동을 추가하시겠습니까? (Y: 추가   그 외: 저장)");
            System.out.print("> ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("Y")) break;
        }

        // Step 8~9: 기록 저장 (E2) + UC10 포인트 지급
        System.out.println("Step 8: 운동 기록을 저장합니다.");
        for (ExerciseRecord r : sessionRecords) {
            if (!r.init()) {
                System.out.println("운동 기록 저장에 실패하였습니다. 잠시 후 다시 시도해주세요.");
                return;
            }
            exerciseRecords.add(r);
        }

        System.out.println("Step 9: 포인트 지급 유스케이스(UC10)를 실행합니다.");
        int earned = earnPoints(totalExerciseTime);

        // Step 10: 완료 메시지
        System.out.println("저장 완료! 지급된 포인트: " + Math.max(earned, 0) + "점");
    }

    private int readPositiveInt(String label) {
        while (true) {
            System.out.print(label + ": ");
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value > 0) return value;
                System.out.println("유효하지 않은 입력값입니다. 운동 횟수는 숫자로 작성하여 주세요.");
            } catch (NumberFormatException e) {
                System.out.println("유효하지 않은 입력값입니다. 운동 횟수는 숫자로 작성하여 주세요.");
            }
        }
    }

    private int earnPoints(int exerciseTime) {
        // UC10: Step 1~2 - 정책 조회 및 포인트 산정 (A1: 시간 기준 미달)
        if (pointPolicy == null) { return -1; }
        int earned = pointPolicy.getBasePoints();
        if (exerciseTime >= pointPolicy.getExerciseTimeStandard()) {
            earned += pointPolicy.getTimeBonusPoints();
        } else {
            System.out.println("운동 시간이 기준(" + pointPolicy.getExerciseTimeStandard()
                    + "분) 미만으로 기본 포인트만 지급됩니다.");
        }

        // UC10: Step 3 - 오늘 이미 적립 여부 확인 (A2)
        LocalDate today = LocalDate.now();
        for (PointHistory ph : pointHistories) {
            if (ph.getMemberId().equals(currentMember.getMemberId())
                    && ph.getDate().equals(today)
                    && "운동적립".equals(ph.getType())) {
                System.out.println("오늘은 이미 포인트가 적립되었습니다.");
                return 0;
            }
        }

        // UC10: Step 4~5 - 포인트 적립 및 이력 저장
        Point memberPoint = null;
        for (Point p : points) {
            if (p.getMemberId().equals(currentMember.getMemberId())) { memberPoint = p; break; }
        }
        if (memberPoint == null) {
            memberPoint = new Point("point-" + String.format("%03d", points.size() + 1),
                    currentMember.getMemberId(), 0, today.plusYears(1));
            if (!memberPoint.init()) {
                System.out.println("포인트 적립 중 오류가 발생하였습니다. 고객센터에 문의해주세요.");
                return -1;
            }
            points.add(memberPoint);
        }
        memberPoint.setBalance(memberPoint.getBalance() + earned);
        pointHistories.add(new PointHistory(
                "ph-" + String.format("%03d", pointHistories.size() + 1),
                currentMember.getMemberId(), "운동적립", earned,
                "운동 기록 포인트 적립", today, memberPoint.getBalance()));

        // UC10: Step 6 - 연속 출석 보너스 확인 (A3)
        int consecutive = countConsecutiveAttendance();
        if (consecutive > 0 && consecutive % pointPolicy.getConsecutiveAttendanceDays() == 0) {
            int bonus = pointPolicy.getConsecutiveAttendanceBonus();
            memberPoint.setBalance(memberPoint.getBalance() + bonus);
            pointHistories.add(new PointHistory(
                    "ph-" + String.format("%03d", pointHistories.size() + 1),
                    currentMember.getMemberId(), "연속출석보너스", bonus,
                    consecutive + "일 연속 출석 달성 보너스", today, memberPoint.getBalance()));
            earned += bonus;
            System.out.printf("%d일 연속 출석 달성! 보너스 포인트 %d점이 추가 적립되었습니다.%n",
                    consecutive, bonus);
        }
        return earned;
    }

    private int countConsecutiveAttendance() {
        LocalDate check = LocalDate.now();
        int count = 0;
        while (true) {
            final LocalDate d = check;
            boolean found = false;
            for (Attendance a : attendances) {
                if (a.getMemberId().equals(currentMember.getMemberId())
                        && a.getAttendanceDateTime().toLocalDate().equals(d)) {
                    found = true;
                    break;
                }
            }
            if (!found) break;
            count++;
            check = check.minusDays(1);
        }
        return count;
    }

    private void handleEarnPoints() {
        System.out.println("포인트는 운동 기록(메뉴 4번) 저장 시 자동으로 지급됩니다.");
        int balance = 0;
        for (Point p : points) {
            if (p.getMemberId().equals(currentMember.getMemberId())) {
                balance = p.getBalance();
                break;
            }
        }
        System.out.printf("현재 포인트 잔액: %,d점%n", balance);
    }

    private void handleSearchEquipment() {
        // Step 2: 기구 전체 리스트 출력
        System.out.println("Step 2: 기구 전체 목록을 출력합니다.");
        List<Equipment> displayList = new ArrayList<>(equipments);
        printEquipmentList(displayList);
        System.out.println("F. 검색/카테고리 필터   0. 돌아가기");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.equals("0")) return;

            // A1: 기구 검색 또는 카테고리 필터
            if (input.equalsIgnoreCase("F")) {
                System.out.print("기구명 또는 카테고리(유산소/근력/스트레칭) 입력: ");
                String keyword = scanner.nextLine().trim();
                displayList = new ArrayList<>();
                for (Equipment eq : equipments) {
                    if (eq.getName().contains(keyword) || eq.getCategory().contains(keyword))
                        displayList.add(eq);
                }
                if (displayList.isEmpty()) {
                    System.out.println("검색 결과가 없습니다. 전체 목록을 표시합니다.");
                    displayList = new ArrayList<>(equipments);
                }
                printEquipmentList(displayList);
                System.out.println("F. 검색/카테고리 필터   0. 돌아가기");
                continue;
            }

            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx < 0 || idx >= displayList.size()) {
                    System.out.println("올바른 번호를 입력해주세요.");
                    continue;
                }
                Equipment selected = displayList.get(idx);

                // Step 4: 기구 상세 정보 출력 (E1)
                System.out.println("Step 4: 선택한 기구의 상세 정보를 출력합니다.");
                if (!selected.init()) {
                    System.out.println("기구 상세 정보를 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
                    return;
                }
                System.out.println("─────────────────────────────────");
                System.out.println("기구명   : " + selected.getName());
                System.out.println("카테고리 : " + selected.getCategory());
                System.out.println("설명     : " + selected.getDescription());
                System.out.println("상태     : " + selected.getStatus());

                // Step 6: 운동 방법 목록 출력
                System.out.println("─────────────────────────────────");
                List<ExerciseMethod> methods = selected.getExerciseMethods();
                if (methods.isEmpty()) {
                    System.out.println("등록된 운동 방법이 없습니다.");
                } else {
                    System.out.println("[운동 방법 목록]");
                    for (int i = 0; i < methods.size(); i++) {
                        System.out.printf("%d. %s (%s)%n",
                                i + 1, methods.get(i).getExerciseName(), methods.get(i).getDifficulty());
                    }
                    // A2: 운동방법 조회 선택 → UC12 실행
                    System.out.println("번호 입력: 운동방법 상세 조회   0. 돌아가기");
                    System.out.print("> ");
                    String methodInput = scanner.nextLine().trim();
                    if (!methodInput.equals("0")) {
                        try {
                            int midx = Integer.parseInt(methodInput) - 1;
                            if (midx >= 0 && midx < methods.size()) {
                                showExerciseMethodDetail(methods.get(midx));
                            } else {
                                System.out.println("올바른 번호를 입력해주세요.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("올바른 번호를 입력해주세요.");
                        }
                    }
                }
                return;
            } catch (NumberFormatException e) {
                System.out.println("올바른 번호를 입력해주세요.");
            }
        }
    }

    private void printEquipmentList(List<Equipment> list) {
        for (int i = 0; i < list.size(); i++) {
            Equipment eq = list.get(i);
            System.out.printf("%d. [%s] %-10s - %s%n",
                    i + 1, eq.getCategory(), eq.getName(), eq.getStatus());
        }
    }

    private void handleViewExerciseMethod() {  // UC12 진입점 (UC11 흐름 재사용)
        handleSearchEquipment();
    }

    private void showExerciseMethodDetail(ExerciseMethod method) {
        System.out.println("Step 1: 운동 방법 상세 정보를 출력합니다.");
        if (!method.init()) {
            System.out.println("운동방법 정보를 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
            return;
        }
        System.out.println("─────────────────────────────────");
        System.out.println("운동명    : " + method.getExerciseName());
        System.out.println("대상 부위 : " + method.getTargetBodyPart());
        System.out.println("난이도    : " + method.getDifficulty());
        System.out.println("준비 자세 : " + method.getPreparationPose());
        System.out.println("[단계별 운동 방법]");
        System.out.println(method.getStepByStepMethod());
        System.out.println("─────────────────────────────────");
        System.out.println("참고 이미지: " + method.getImage());
        System.out.println("영상 URL   : " + method.getVideoUrl());
        System.out.println("─────────────────────────────────");
    }

    private void handleViewNotice() {
        // Step 2: 공지사항 목록 최신순 출력 (E1)
        System.out.println("Step 2: 공지사항 목록을 최신순으로 출력합니다.");
        if (notices.isEmpty()) {
            System.out.println("공지사항을 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
            return;
        }

        List<Notice> sorted = new ArrayList<>(notices);
        sorted.sort((a, b) -> b.getWriteDate().compareTo(a.getWriteDate()));

        while (true) {
            System.out.println("─────────────────────────────────");
            for (int i = 0; i < sorted.size(); i++) {
                Notice n = sorted.get(i);
                String mark = n.isReadBy(currentMember.getMemberId()) ? "[읽음]  " : "[미읽음]";
                System.out.printf("%d. %s [%s] %s (%s)%n",
                        i + 1, mark, n.getCategory(), n.getTitle(), n.getWriteDate());
            }
            System.out.println("0. 돌아가기");
            System.out.print("> ");

            String input = scanner.nextLine().trim();
            if (input.equals("0")) return;

            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx < 0 || idx >= sorted.size()) {
                    System.out.println("올바른 번호를 입력해주세요.");
                    continue;
                }

                // Step 5~6: 상세 내용 출력 및 읽음 처리 (E2)
                System.out.println("Step 5: 선택한 공지사항의 상세 내용을 출력합니다.");
                Notice selected = sorted.get(idx);
                if (!selected.init()) {
                    System.out.println("해당 공지사항을 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
                    return;
                }
                printNoticeDetail(selected);
                selected.markAsRead(currentMember.getMemberId());
                System.out.println("Step 6: 읽음 처리가 완료되었습니다.");

                // Step 7~9: 이전/다음 탐색 및 첨부파일 (A1, A2)
                while (true) {
                    System.out.println("P. 이전 공지   N. 다음 공지   D. 첨부파일 다운로드   0. 목록으로");
                    System.out.print("> ");
                    String nav = scanner.nextLine().trim();

                    if (nav.equals("0")) break;

                    if (nav.equalsIgnoreCase("P")) {
                        if (idx == 0) {
                            System.out.println("이전 공지사항이 없습니다.");
                        } else {
                            idx--;
                            selected = sorted.get(idx);
                            printNoticeDetail(selected);
                            selected.markAsRead(currentMember.getMemberId());
                        }
                    } else if (nav.equalsIgnoreCase("N")) {
                        if (idx == sorted.size() - 1) {
                            System.out.println("다음 공지사항이 없습니다.");
                        } else {
                            idx++;
                            selected = sorted.get(idx);
                            printNoticeDetail(selected);
                            selected.markAsRead(currentMember.getMemberId());
                        }
                    } else if (nav.equalsIgnoreCase("D")) {
                        // A1: 첨부파일 다운로드
                        if ("없음".equals(selected.getAttachment()) || selected.getAttachment().isEmpty()) {
                            System.out.println("첨부파일이 없습니다.");
                        } else {
                            System.out.println("첨부파일 '" + selected.getAttachment() + "' 다운로드가 완료되었습니다.");
                        }
                    }
                }

                // Step 10: 목록으로 돌아가기
                System.out.println("Step 10: 공지사항 목록으로 돌아갑니다.");

            } catch (NumberFormatException e) {
                System.out.println("올바른 번호를 입력해주세요.");
            }
        }
    }

    private void printNoticeDetail(Notice n) {
        System.out.println("─────────────────────────────────");
        System.out.println("제목     : " + n.getTitle());
        System.out.println("카테고리 : " + n.getCategory());
        System.out.println("작성일   : " + n.getWriteDate());
        System.out.println("첨부파일 : " + n.getAttachment());
        System.out.println("[내용]");
        System.out.println(n.getContent());
        System.out.println("─────────────────────────────────");
    }

    private void handleManageMyInfo() {
        // E2: 세션 확인
        if (currentMember == null) {
            System.out.println("로그인 세션이 만료되었습니다. 다시 로그인해주세요.");
            return;
        }

        while (true) {
            // Step 2: 내정보 메인 화면 출력 (E1)
            System.out.println("Step 2: 내정보 메인 화면을 출력합니다.");
            if (!currentMember.init()) {
                System.out.println("내정보를 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
                return;
            }

            System.out.println("═════════════════════════════════");
            System.out.println("[프로필 정보]");
            System.out.println("이름     : " + currentMember.getName());
            System.out.println("닉네임   : " + currentMember.getNickname());
            System.out.println("아이디   : " + currentMember.getLoginId());
            System.out.println("전화번호 : " + currentMember.getPhoneNumber());
            System.out.println("생년월일 : " + currentMember.getBirthDate());
            System.out.println("신체정보 : " + currentMember.getPhysicalInfo());
            System.out.println("주소     : " + currentMember.getAddress());
            System.out.println("가입일   : " + currentMember.getJoinDate());

            // Step 3: 회원권 현황 요약 (UC16 요약)
            System.out.println("─────────────────────────────────");
            System.out.println("[회원권 현황]");
            Membership active = null;
            for (Membership ms : memberMemberships) {
                if (ms.getMemberId().equals(currentMember.getMemberId())
                        && "ACTIVE".equals(ms.getStatus())) {
                    active = ms;
                    break;
                }
            }
            if (active == null) {
                System.out.println("보유 중인 회원권이 없습니다.");
            } else {
                System.out.println("종류   : " + active.getProductName());
                System.out.println("만료일 : " + active.getEndDate());
                long remaining = java.time.temporal.ChronoUnit.DAYS.between(
                        LocalDate.now(), active.getEndDate());
                System.out.println("잔여   : " + remaining + "일");
            }

            // Step 4: 포인트 잔액 요약 (UC19 요약)
            System.out.println("─────────────────────────────────");
            System.out.println("[포인트 잔액]");
            int balance = 0;
            for (Point p : points) {
                if (p.getMemberId().equals(currentMember.getMemberId())) {
                    balance = p.getBalance();
                    break;
                }
            }
            System.out.printf("현재 포인트: %,d점%n", balance);
            System.out.println("═════════════════════════════════");

            // Step 6: 세부 메뉴 (A1, A2)
            System.out.println("1. 내정보 수정   2. 회원권 관리   3. 포인트 내역");
            System.out.println("0. 돌아가기");
            System.out.print("> ");

            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> handleUpdateMyInfo();       // A1 → UC15
                case "2" -> handleManageMembership();   // A2 → UC16
                case "3" -> handleViewPointHistory();   // UC19
                case "0" -> { return; }
                default  -> System.out.println("올바른 메뉴를 선택해주세요.");
            }
        }
    }

    private void handleUpdateMyInfo() {
        // Step 2: 수정 폼 출력
        System.out.println("Step 2: 내정보 수정 폼을 출력합니다.");
        System.out.println("(생략 시 Enter → 현재 값 유지)");
        System.out.println("─────────────────────────────────");

        // 닉네임
        System.out.println("현재 닉네임: " + currentMember.getNickname());
        System.out.print("새 닉네임: ");
        String newNickname = scanner.nextLine().trim();

        // 전화번호 (A2: 형식 검증 및 중복 확인)
        System.out.println("현재 전화번호: " + currentMember.getPhoneNumber());
        String newPhone = "";
        while (true) {
            System.out.print("새 전화번호 (예: 010-1234-5678): ");
            newPhone = scanner.nextLine().trim();
            if (newPhone.isEmpty()) break;

            // A2: 형식 검증
            if (!newPhone.matches("\\d{3}-\\d{4}-\\d{4}")) {
                System.out.println("올바른 형식의 전화번호를 입력해주세요.");
                continue;
            }
            // A2: 중복 확인
            boolean duplicate = false;
            for (Member m : members) {
                if (!m.getMemberId().equals(currentMember.getMemberId())
                        && m.getPhoneNumber().equals(newPhone)) {
                    duplicate = true;
                    break;
                }
            }
            if (duplicate) {
                System.out.println("이미 사용 중인 전화번호입니다.");
                continue;
            }
            break;
        }

        // 생년월일
        System.out.println("현재 생년월일: " + currentMember.getBirthDate());
        System.out.print("새 생년월일 (예: 19900101): ");
        String newBirthDate = scanner.nextLine().trim();

        // 신체정보
        System.out.println("현재 신체정보: " + currentMember.getPhysicalInfo());
        System.out.print("새 신체정보 (예: 175cm/70kg): ");
        String newPhysicalInfo = scanner.nextLine().trim();

        // 주소
        System.out.println("현재 주소: " + currentMember.getAddress());
        System.out.print("새 주소: ");
        String newAddress = scanner.nextLine().trim();

        // 프로필 사진 (A1)
        System.out.println("현재 프로필 사진: " + currentMember.getProfilePicture());
        System.out.println("프로필 사진을 변경하시겠습니까? (Y/N)");
        System.out.print("> ");
        String newProfilePicture = "";
        if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
            System.out.println("1. 갤러리에서 선택   2. 카메라 촬영");
            System.out.print("> ");
            scanner.nextLine();
            System.out.print("파일명 입력: ");
            newProfilePicture = scanner.nextLine().trim();
            if (newProfilePicture.isEmpty()) {
                System.out.println("프로필 사진 업로드에 실패하였습니다. 파일 크기 또는 형식을 확인해주세요.");
                newProfilePicture = "";
            } else {
                System.out.println("[미리보기] " + newProfilePicture);
            }
        }

        // 비밀번호 변경 (A3, E1)
        System.out.println("비밀번호를 변경하시겠습니까? (Y/N)");
        System.out.print("> ");
        String newPassword = "";
        if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
            // Step 5~6: 현재 비밀번호 확인 (E1)
            System.out.println("Step 5: 현재 비밀번호를 입력하여 본인 확인을 진행합니다.");
            System.out.print("현재 비밀번호: ");
            String currentPw = scanner.nextLine().trim();
            if (!currentPw.equals(currentMember.getPassword())) {
                System.out.println("현재 비밀번호가 올바르지 않습니다.");
                return;
            }
            System.out.print("새 비밀번호: ");
            newPassword = scanner.nextLine().trim();
        }

        // Step 7: 최종 확인
        System.out.println("Step 7: 수정 내용을 최종 확인합니다.");
        System.out.println("─────────────────────────────────");
        if (!newNickname.isEmpty())     System.out.println("닉네임   : " + newNickname);
        if (!newPhone.isEmpty())        System.out.println("전화번호 : " + newPhone);
        if (!newBirthDate.isEmpty())    System.out.println("생년월일 : " + newBirthDate);
        if (!newPhysicalInfo.isEmpty()) System.out.println("신체정보 : " + newPhysicalInfo);
        if (!newAddress.isEmpty())      System.out.println("주소     : " + newAddress);
        if (!newProfilePicture.isEmpty()) System.out.println("프로필   : " + newProfilePicture);
        if (!newPassword.isEmpty())     System.out.println("비밀번호 : (변경됨)");
        System.out.println("─────────────────────────────────");
        System.out.println("저장하시겠습니까? (Y/N)");
        System.out.print("> ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("Y")) return;

        // Step 8: 저장 (E2)
        System.out.println("Step 8: 수정된 정보를 저장합니다.");
        if (!currentMember.init()) {
            System.out.println("정보 저장에 실패하였습니다. 잠시 후 다시 시도해주세요.");
            return;
        }
        if (!newNickname.isEmpty())     currentMember.setNickname(newNickname);
        if (!newPhone.isEmpty())        currentMember.setPhoneNumber(newPhone);
        if (!newBirthDate.isEmpty())    currentMember.setBirthDate(newBirthDate);
        if (!newPhysicalInfo.isEmpty()) currentMember.setPhysicalInfo(newPhysicalInfo);
        if (!newAddress.isEmpty())      currentMember.setAddress(newAddress);
        if (!newProfilePicture.isEmpty()) currentMember.setProfilePicture(newProfilePicture);
        if (!newPassword.isEmpty())     currentMember.setPassword(newPassword);

        System.out.println("내정보가 성공적으로 수정되었습니다.");
    }

    private void handleManageMembership() {
        // Step 2: 회원권 관리 화면
        System.out.println("Step 2: 회원권 관리 화면을 출력합니다.");

        // Step 3: UC17 잔여기간 조회 실행 (E1)
        System.out.println("Step 3: 잔여기간 조회 유스케이스(UC17)를 실행합니다.");
        Membership active = findActiveMembership();

        // A1: 보유 회원권 없음
        if (active == null) {
            System.out.println("현재 보유 중인 회원권이 없습니다.");
            System.out.println("[회원권 구매하기] 1. 구매   0. 돌아가기");
            System.out.print("> ");
            if (scanner.nextLine().trim().equals("1")) {
                handlePurchaseMembership();
            }
            return;
        }

        showRemainingPeriod(active);

        // Step 5: 부가 상품 현황
        System.out.println("Step 5: 부가 상품 보유 현황을 출력합니다.");
        System.out.println("─────────────────────────────────");
        System.out.println("[부가 상품 현황]");
        long lockerCount = orders.stream()
                .filter(o -> o.getMemberId().equals(currentMember.getMemberId())
                        && "ADDITIONAL_PRODUCT".equals(getProductTypeById(o.getProductId())))
                .count();
        int ptRemaining = memberPTs.stream()
                .filter(pt -> pt.getMemberId().equals(currentMember.getMemberId())
                        && "ACTIVE".equals(pt.getStatus()))
                .mapToInt(PT::getRemainingCount).sum();
        System.out.println("부가 상품 구매 수: " + lockerCount + "건");
        System.out.println("PT 잔여 횟수    : " + ptRemaining + "회");

        // Step 7~9: 이용 내역 (E2)
        System.out.println("─────────────────────────────────");
        System.out.println("[이용 내역 (출석 기록)]");
        List<Attendance> myAttendances = new ArrayList<>();
        for (Attendance a : attendances) {
            if (a.getMemberId().equals(currentMember.getMemberId())) myAttendances.add(a);
        }
        if (myAttendances.isEmpty()) {
            System.out.println("이용 내역이 없습니다.");
        } else {
            for (Attendance a : myAttendances) {
                System.out.println("- " + a.getAttendanceDateTime().toLocalDate()
                        + "  " + a.getAttendanceDateTime().toLocalTime()
                        + "  (" + a.getAuthMethod() + ")");
            }
        }

        // Step 10: 만료 임박 경고 (7일 이하)
        long remaining = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), active.getEndDate());
        if (remaining <= 7 && remaining >= 0) {
            System.out.println("─────────────────────────────────");
            System.out.println("[안내] 회원권이 " + remaining + "일 후 만료됩니다. 갱신을 권장합니다.");
        }

        System.out.println("─────────────────────────────────");
        System.out.println("1. 부가 상품 관리   0. 돌아가기");
        System.out.print("> ");
        String input = scanner.nextLine().trim();

        // A2: 부가 상품 관리 → UC18
        if (input.equals("1")) {
            handleManageAdditionalProduct();
        }
    }

    private Membership findActiveMembership() {
        for (Membership ms : memberMemberships) {
            if (ms.getMemberId().equals(currentMember.getMemberId())
                    && "ACTIVE".equals(ms.getStatus())) {
                return ms;
            }
        }
        return null;
    }

    private String getProductTypeById(String productId) {
        for (AdditionalProduct ap : additionalProductCatalog) {
            if (ap.getProductId().equals(productId)) return "ADDITIONAL_PRODUCT";
        }
        return "";
    }

    private void showRemainingPeriod(Membership ms) {
        System.out.println("─────────────────────────────────");
        System.out.println("[회원권 잔여기간]");
        System.out.println("종류   : " + ms.getProductName());
        System.out.println("시작일 : " + ms.getStartDate());
        System.out.println("만료일 : " + ms.getEndDate());

        // A1: 일시정지 상태
        if (ms.getPauseDate() != null) {
            System.out.println("상태   : 일시정지");
            System.out.println("정지일 : " + ms.getPauseDate());
            if (ms.getResumeDate() != null) System.out.println("재개일 : " + ms.getResumeDate());
        } else {
            long remaining = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), ms.getEndDate());
            if (remaining < 0) {
                System.out.println("상태   : 만료됨");
            } else {
                System.out.println("잔여일 : " + remaining + "일");
            }
        }
        System.out.println("─────────────────────────────────");
    }

    private void handleCheckRemainingPeriod() {
        System.out.println("Step 2: 회원권 정보를 조회합니다.");
        Membership active = findActiveMembership();

        // A2: 만료 또는 보유 없음
        if (active == null) {
            System.out.println("회원권이 만료되었습니다.");
            System.out.println("[회원권 구매하기] 1. 구매   0. 돌아가기");
            System.out.print("> ");
            if (scanner.nextLine().trim().equals("1")) handlePurchaseMembership();
            return;
        }

        showRemainingPeriod(active);

        // Step 6: 만료 임박 경고
        long remaining = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), active.getEndDate());
        if (remaining <= 7 && remaining >= 0) {
            System.out.println("[안내] 회원권이 " + remaining + "일 후 만료됩니다. 갱신을 권장합니다.");
        }

        // Step 7~9: 상세 정보 조회
        System.out.println("상세 정보를 보시겠습니까? (Y/N)");
        System.out.print("> ");
        if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
            System.out.println("─────────────────────────────────");
            System.out.println("설명     : " + active.getDescription());
            System.out.println("구매 상품: " + active.getProductName());
            System.out.println("─────────────────────────────────");
        }
    }

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
