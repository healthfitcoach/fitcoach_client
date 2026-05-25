package com.fitcoach.client.view;

import com.fitcoach.client.controller.ActivityController;
import com.fitcoach.client.controller.AuthController;
import com.fitcoach.client.controller.InfoController;
import com.fitcoach.client.controller.MemberController;
import com.fitcoach.client.controller.PTController;
import com.fitcoach.client.controller.PurchaseController;
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
    dba = createDBA();
    if (dba == null || !dba.init()) {
      System.out.println("[경고] DB 연결에 실패했습니다.");
      return false;
    }

    // Phase 1: 유틸 초기화
    iu = new InputUtil();
    if (!iu.init()) {
      System.out.println("시스템 초기화에 실패했습니다. 잠시 후 다시 시도해주세요.");
      return false;
    }
    cu = new ConsoleUtil();

    // Phase 2: 컨트롤러 생성 (각자 DAO를 통해 DB에 직접 접근)
    auth     = new AuthController(dba);
    info     = new InfoController(dba);
    pt       = new PTController(dba);
    activity = new ActivityController(dba);
    purchase = new PurchaseController(dba);
    member   = new MemberController(dba, purchase, activity, pt);

    // Phase 3: 컨트롤러 초기화 검증
    if (!auth.init() || !info.init() || !pt.init()
        || !activity.init() || !purchase.init() || !member.init()) {
      System.out.println("서비스 초기화에 실패했습니다. 잠시 후 다시 시도해주세요.");
      return false;
    }

    // Phase 4: 뷰 생성
    authView     = new AuthView(iu, cu, auth);
    infoView     = new InfoView(iu, cu, auth, info);
    ptView       = new PTView(iu, cu, auth, pt);
    activityView = new ActivityView(iu, cu, auth, activity);
    purchaseView = new PurchaseView(iu, cu, auth, purchase);
    memberView   = new MemberView(iu, cu, auth, member, purchaseView);

    return true;
  }

  private DBA createDBA() {
    // 접속 정보는 persistence.xml에서 관리 — 코드에서 분리됨
    return new DBA();
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
