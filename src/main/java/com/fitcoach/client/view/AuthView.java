package com.fitcoach.client.view;

import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.controller.AuthController;
import com.fitcoach.client.model.member.Member;
import com.fitcoach.client.util.ConsoleUtil;
import com.fitcoach.client.util.InputUtil;

public class AuthView {
  private InputUtil iu;
  private ConsoleUtil cu;
  private AuthController auth;

  public AuthView(InputUtil iu, ConsoleUtil cu, AuthController auth) {
    this.iu = iu;
    this.cu = cu;
    this.auth = auth;
  }

  public void showSignUp() {
    cu.showStep(1, "회원가입 입력 폼을 출력합니다.");

    String name, nickname, loginId, password, phoneNumber;
    String birthDate, physicalInfo, address, profilePicture;

    while (true) {
      System.out.println("\n[필수 항목]");
      System.out.print("이름: ");         name = iu.readLine();
      System.out.print("닉네임: ");       nickname = iu.readLine();
      System.out.print("아이디: ");       loginId = iu.readLine();
      System.out.print("비밀번호: ");     password = iu.readLine();
      System.out.print("전화번호: ");     phoneNumber = iu.readLine();

      System.out.println("[선택 항목] (생략 시 Enter)");
      System.out.print("생년월일 (예: 19900101): ");   birthDate = iu.readLine();
      System.out.print("신체정보 (예: 175cm/70kg): "); physicalInfo = iu.readLine();
      System.out.print("주소: ");                      address = iu.readLine();
      System.out.print("프로필 사진 파일명: ");         profilePicture = iu.readLine();

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

      cu.showStep(2, "아이디 중복 여부를 확인합니다.");
      if (auth.isDuplicateLoginId(loginId)) {
        System.out.println("이미 사용 중인 아이디입니다.");
        continue;
      }
      break;
    }

    cu.showStep(3, "서비스 이용약관 및 개인정보 처리방침을 확인합니다.");
    System.out.println("1. FitCoach 서비스 이용약관 동의 (필수)");
    System.out.println("2. 개인정보 처리방침 동의 (필수)");
    System.out.println("3. 마케팅 정보 수신 동의 (선택)");
    while (true) {
      System.out.println("필수 약관(1, 2)에 모두 동의하시면 Y를 입력하세요.");
      cu.showPrompt();
      if (iu.readLine().equalsIgnoreCase("Y")) break;
      System.out.println("필수 약관에 동의하셔야 가입이 가능합니다.");
    }

    cu.showStep(4, "신규 계정을 생성합니다.");
    boolean created = auth.createMember(loginId, password, name, nickname,
        phoneNumber, birthDate, physicalInfo, address, profilePicture);
    if (!created) {
      System.out.println("일시적인 오류가 발생하였습니다. 잠시 후 다시 시도해주세요");
      return;
    }
    System.out.println("회원가입이 완료되었습니다.");
  }

  public void showLogin() {
    cu.showStep(1, "로그인 정보를 입력합니다.");
    System.out.print("아이디: ");   String loginId = iu.readLine();
    System.out.print("비밀번호: "); String password = iu.readLine();

    cu.showStep(2, "아이디와 비밀번호를 확인합니다.");
    Member found = auth.validateLogin(loginId, password);
    if (found == null) {
      System.out.println("아이디 또는 비밀번호가 올바르지 않습니다.");
      return;
    }
    if (!auth.login(found)) {
      System.out.println("로그인 처리 중 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.");
      return;
    }
    System.out.println(found.getNickname() + "님, 환영합니다.");
  }

  public void showLogout() {
    auth.logout();
    System.out.println("로그아웃 되었습니다.");
  }
}
