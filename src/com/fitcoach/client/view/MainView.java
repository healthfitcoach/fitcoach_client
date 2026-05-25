package com.fitcoach.client.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fitcoach.client.controller.ActivityController;
import com.fitcoach.client.controller.AuthController;
import com.fitcoach.client.controller.InfoController;
import com.fitcoach.client.controller.MemberController;
import com.fitcoach.client.controller.PTController;
import com.fitcoach.client.controller.PurchaseController;
import com.fitcoach.client.model.equipment.Equipment;
import com.fitcoach.client.model.equipment.ExerciseMethod;
import com.fitcoach.client.model.point.PointPolicy;
import com.fitcoach.client.model.product.AdditionalProduct;
import com.fitcoach.client.model.product.Membership;
import com.fitcoach.client.model.product.PT;
import com.fitcoach.client.util.ConsoleUtil;
import com.fitcoach.client.util.InputUtil;
import db.DBA;
import db.dao.AdditionalProductDao;
import db.dao.AttendanceDao;
import db.dao.EquipmentDao;
import db.dao.ExerciseMethodDao;
import db.dao.ExerciseProgramDao;
import db.dao.ExerciseRecordDao;
import db.dao.MemberDao;
import db.dao.MembershipDao;
import db.dao.NoticeDao;
import db.dao.OrderDao;
import db.dao.PaymentDao;
import db.dao.PTDao;
import db.dao.PTScheduleDao;
import db.dao.PointDao;
import db.dao.PointHistoryDao;
import db.dao.PointPolicyDao;
import db.dao.SportEquipmentDao;
import db.dao.TrainerDao;

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

    // Phase 4: DB에서 데이터 로드
    if (dba != null && dba.isConnected()) loadFromDatabase();

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

  // ─── DB → In-Memory 로드 ─────────────────────────────────────

  private void loadFromDatabase() {
    // 회원
    MemberDao memberDao = new MemberDao(dba);
    if (memberDao.init()) auth.getMembers().addAll(memberDao.findAll());

    // 기구 + 운동방법 (연결)
    EquipmentDao equipmentDao   = new EquipmentDao(dba);
    ExerciseMethodDao methodDao = new ExerciseMethodDao(dba);
    if (equipmentDao.init() && methodDao.init()) {
      List<Equipment> eqList = equipmentDao.findAll();
      Map<String, Equipment> eqMap = new HashMap<>();
      for (Equipment eq : eqList) eqMap.put(eq.getEquipmentId(), eq);
      for (ExerciseMethod m : methodDao.findAll()) {
        Equipment parent = eqMap.get(m.getEquipmentId());
        if (parent != null) parent.addExerciseMethod(m);
      }
      info.getEquipments().addAll(eqList);
    }

    // 공지사항
    NoticeDao noticeDao = new NoticeDao(dba);
    if (noticeDao.init()) info.getNotices().addAll(noticeDao.findAll());

    // 트레이너
    TrainerDao trainerDao = new TrainerDao(dba);
    if (trainerDao.init()) pt.getTrainers().addAll(trainerDao.findAll());

    // 포인트 정책
    PointPolicyDao policyDao = new PointPolicyDao(dba);
    if (policyDao.init()) {
      PointPolicy policy = policyDao.findActive();
      if (policy != null) activity.setPointPolicy(policy);
    }

    // 회원권: null memberId → 카탈로그, 그 외 → 구매 목록
    MembershipDao membershipDao = new MembershipDao(dba);
    if (membershipDao.init()) {
      for (Membership ms : membershipDao.findAll()) {
        if (ms.getMemberId() == null) purchase.getMembershipCatalog().add(ms);
        else purchase.getMemberMemberships().add(ms);
      }
    }

    // 헬스 프로그램 카탈로그
    ExerciseProgramDao programDao = new ExerciseProgramDao(dba);
    if (programDao.init()) purchase.getProgramCatalog().addAll(programDao.findAll());

    // 운동용품 카탈로그
    SportEquipmentDao seDao = new SportEquipmentDao(dba);
    if (seDao.init()) purchase.getSportEquipmentCatalog().addAll(seDao.findAll());

    // PT: null memberId → 카탈로그, 그 외 → 구매 목록
    PTDao ptDao = new PTDao(dba);
    if (ptDao.init()) {
      for (PT p : ptDao.findAll()) {
        if (p.getMemberId() == null) purchase.getPtCatalog().add(p);
        else pt.getMemberPTs().add(p);
      }
    }

    // 부가 상품: null memberId → 카탈로그
    AdditionalProductDao apDao = new AdditionalProductDao(dba);
    if (apDao.init()) {
      for (AdditionalProduct ap : apDao.findAll()) {
        if (ap.getMemberId() == null) purchase.getAdditionalProductCatalog().add(ap);
      }
    }

    // 트랜잭션 데이터
    AttendanceDao attendanceDao     = new AttendanceDao(dba);
    ExerciseRecordDao recordDao     = new ExerciseRecordDao(dba);
    PointDao pointDao               = new PointDao(dba);
    PointHistoryDao historyDao      = new PointHistoryDao(dba);
    OrderDao orderDao               = new OrderDao(dba);
    PaymentDao paymentDao           = new PaymentDao(dba);
    PTScheduleDao scheduleDao       = new PTScheduleDao(dba);

    if (attendanceDao.init()) activity.getAttendances().addAll(attendanceDao.findAll());
    if (recordDao.init())     activity.getExerciseRecords().addAll(recordDao.findAll());
    if (pointDao.init())      activity.getPoints().addAll(pointDao.findAll());
    if (historyDao.init())    activity.getPointHistories().addAll(historyDao.findAll());
    if (orderDao.init())      purchase.getOrders().addAll(orderDao.findAll());
    if (paymentDao.init())    purchase.getPayments().addAll(paymentDao.findAll());
    if (scheduleDao.init())   pt.getPtSchedules().addAll(scheduleDao.findAll());
  }

  // ─── 메인 루프 ────────────────────────────────────────────────

  public void run() {
    cu.showWelcome();
    while (true) {
      if (!auth.isLoggedIn()) showGuestMenu();
      else showMemberMenu();
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
