package com.fitcoach.client.util;

import java.time.LocalDate;
import java.util.Scanner;

public class InputUtil {
  private Scanner scanner;

  public InputUtil() {
    this.scanner = null;
  }

  public boolean init() {
    try {
      scanner = new Scanner(System.in);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String readLine() {
    return scanner.nextLine().trim();
  }

  public int readPositiveInt(String label) {
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

  public LocalDate readDate(String prompt) {
    while (true) {
      System.out.print(prompt);
      try {
        return LocalDate.parse(scanner.nextLine().trim());
      } catch (Exception e) {
        System.out.println("올바른 날짜 형식을 입력해주세요. (예: 2025-06-01)");
      }
    }
  }

  public void close() {
    if (scanner != null) scanner.close();
  }
}
