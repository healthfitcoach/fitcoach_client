package com.fitcoach.client.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.controller.ActivityController;
import com.fitcoach.client.controller.AuthController;
import com.fitcoach.client.model.member.ExerciseRecord;
import com.fitcoach.client.util.ConsoleUtil;
import com.fitcoach.client.util.InputUtil;

public class ActivityView {
  private InputUtil iu;
  private ConsoleUtil cu;
  private AuthController auth;
  private ActivityController activity;

  public ActivityView(InputUtil iu, ConsoleUtil cu, AuthController auth,
      ActivityController activity) {
    this.iu = iu;
    this.cu = cu;
    this.auth = auth;
    this.activity = activity;
  }

  public void showCheckAttendance() {
    String memberId = auth.getCurrentMember().getMemberId();

    cu.showStep(2, "당일 출석 여부를 확인합니다.");
    if (activity.hasCheckedInToday(memberId)) {
      System.out.println("오늘은 이미 출석이 완료되었습니다.");
      return;
    }

    cu.showStep(3, "출석 QR 코드를 생성합니다.");
    String qrCode = "QR-" + memberId + "-" + LocalDate.now();
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
      cu.showPrompt();
      String input = iu.readLine();

      if (input.equalsIgnoreCase("R")) {
        System.out.println("QR 코드가 만료되었습니다. 새로운 QR 코드를 발급해주세요.");
        qrCode = "QR-" + memberId + "-" + LocalDate.now() + "-" + System.currentTimeMillis() % 10000;
        System.out.println("새로운 QR 코드가 발급되었습니다.");
        continue;
      }

      cu.showStep(5, "QR 코드의 유효성을 검증합니다.");
      if (!qrCode.startsWith("QR-" + memberId)) {
        System.out.println("유효하지 않은 QR 코드입니다. 앱에서 QR 코드를 다시 확인해주세요.");
        return;
      }
      break;
    }

    if (!activity.checkIn(memberId)) {
      System.out.println("QR 코드 생성에 실패하였습니다. 잠시 후 다시 시도해주세요.");
      return;
    }
    System.out.println("출석이 완료되었습니다.");
  }

  public void showRecordExercise() {
    String memberId = auth.getCurrentMember().getMemberId();

    if (!activity.hasActiveMembership(memberId)) {
      System.out.println("활성화된 회원권이 없습니다. 회원권을 먼저 구매해주세요.");
      return;
    }

    cu.showStep(2, "운동 기록 입력 화면을 출력합니다.");
    cu.showStep(3, "운동 날짜를 선택합니다.");
    System.out.println("1. 오늘 날짜 자동 선택 (" + LocalDate.now() + ")");
    System.out.println("2. 날짜 직접 입력 (yyyy-MM-dd)");
    cu.showPrompt();

    LocalDate exerciseDate;
    if (iu.readLine().equals("2")) {
      exerciseDate = iu.readDate("날짜 (yyyy-MM-dd): ");
    } else {
      exerciseDate = LocalDate.now();
    }

    int totalExerciseTime = 0;
    List<ExerciseRecord> sessionRecords = new ArrayList<>();

    while (true) {
      cu.showStep(4, "수행한 운동 종류를 입력합니다.");
      System.out.print("운동 종류 (예: 벤치프레스, 러닝): ");
      String exerciseType = iu.readLine();
      if (exerciseType.isEmpty()) {
        System.out.println("운동 종류와 운동 시간은 필수 입력 항목입니다.");
        return;
      }

      cu.showStep(5, "운동 세부 정보를 입력합니다.");
      int exerciseTime = iu.readPositiveInt("운동 시간 (분)");
      int sets         = iu.readPositiveInt("세트 수");
      int reps         = iu.readPositiveInt("반복 횟수");
      totalExerciseTime += exerciseTime;

      cu.showStep(7, "추가 메모 또는 사진을 첨부합니다. (생략 시 Enter)");
      System.out.print("메모: ");       String memo = iu.readLine();
      System.out.print("사진 파일명: "); String photo = iu.readLine();

      sessionRecords.add(new ExerciseRecord(null, memberId, exerciseDate,
          exerciseType, exerciseTime, sets, reps, memo, photo));

      System.out.println("운동을 추가하시겠습니까? (Y: 추가   그 외: 저장)");
      cu.showPrompt();
      if (!iu.readLine().equalsIgnoreCase("Y")) break;
    }

    cu.showStep(8, "운동 기록을 저장합니다.");
    for (ExerciseRecord r : sessionRecords) {
      boolean saved = activity.addExerciseRecord(memberId, r.getExerciseDate(),
          r.getExerciseType(), r.getExerciseTime(),
          r.getSets(), r.getReps(), r.getMemo(), r.getPhoto());
      if (!saved) {
        System.out.println("운동 기록 저장에 실패하였습니다. 잠시 후 다시 시도해주세요.");
        return;
      }
    }

    cu.showStep(9, "포인트 지급 유스케이스(UC10)를 실행합니다.");
    if (activity.hasEarnedPointsToday(memberId)) {
      System.out.println("오늘은 이미 포인트가 적립되었습니다.");
      System.out.println("저장 완료! 지급된 포인트: 0점");
      return;
    }

    if (!activity.isTimeBonusApplicable(totalExerciseTime)) {
      System.out.println("운동 시간이 기준(" + activity.getTimeBonusStandard()
          + "분) 미만으로 기본 포인트만 지급됩니다.");
    }

    int earned = activity.earnPoints(memberId, totalExerciseTime);

    int consecutive = activity.getConsecutiveCount(memberId);
    if (consecutive > 0 && consecutive % activity.getConsecutiveBonusDays() == 0) {
      System.out.printf("%d일 연속 출석 달성! 보너스 포인트 %d점이 추가 적립되었습니다.%n",
          consecutive, activity.getConsecutiveBonusPoints());
    }

    System.out.println("저장 완료! 지급된 포인트: " + Math.max(earned, 0) + "점");
  }

  public void showEarnPoints() {
    String memberId = auth.getCurrentMember().getMemberId();
    System.out.println("포인트는 운동 기록(메뉴 4번) 저장 시 자동으로 지급됩니다.");
    System.out.printf("현재 포인트 잔액: %,d점%n", activity.getPointBalance(memberId));
  }
}
