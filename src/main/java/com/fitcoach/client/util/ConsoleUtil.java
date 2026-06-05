package com.fitcoach.client.util;

public class ConsoleUtil {
  private static final String SEP = "─────────────────────────────────";
  private static final String THICK_SEP = "═════════════════════════════════";

  public void showWelcome() {
    System.out.println("=================================================");
    System.out.println("     FitCoach 헬스장 시스템에 오신 것을 환영합니다.");
    System.out.println("=================================================");
  }

  public void showSeparator() {
    System.out.println(SEP);
  }

  public void showThickSeparator() {
    System.out.println(THICK_SEP);
  }

  public void showPrompt() {
    System.out.print("> ");
  }

  public void showStep(int n, String desc) {
    System.out.println("Step " + n + ": " + desc);
  }
}
