package com.fitcoach.client.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.controller.AuthController;
import com.fitcoach.client.controller.PTController;
import com.fitcoach.client.model.product.MemberProduct;
import com.fitcoach.client.model.schedule.Trainer;
import com.fitcoach.client.util.ConsoleUtil;
import com.fitcoach.client.util.InputUtil;

public class PTView {
  private InputUtil iu;
  private ConsoleUtil cu;
  private AuthController auth;
  private PTController pt;

  public PTView(InputUtil iu, ConsoleUtil cu, AuthController auth, PTController pt) {
    this.iu = iu;
    this.cu = cu;
    this.auth = auth;
    this.pt = pt;
  }

  public void showSchedulePT() {
    String memberId = auth.getCurrentMember().getMemberId();

    List<MemberProduct> myPTs = pt.getActivePTsByMember(memberId);
    if (myPTs.isEmpty()) {
      System.out.println("등록된 PT 이용권이 없습니다. PT를 먼저 구매해주세요.");
      return;
    }

    MemberProduct selectedPT;
    if (myPTs.size() == 1) {
      selectedPT = myPTs.get(0);
    } else {
      cu.showStep(1, "예약할 PT 이용권을 선택합니다.");
      for (int i = 0; i < myPTs.size(); i++) {
        MemberProduct p = myPTs.get(i);
        Trainer t = pt.findTrainer(p.getTrainerId());
        String tName = (t != null) ? t.getName() : "미정";
        System.out.printf("%d. %s | 트레이너: %s | 잔여: %d회%n",
            i + 1, p.getProductName(), tName, p.getRemainingCount());
      }
      cu.showPrompt();
      try {
        int idx = Integer.parseInt(iu.readLine()) - 1;
        if (idx < 0 || idx >= myPTs.size()) {
          System.out.println("올바른 번호를 입력해주세요.");
          return;
        }
        selectedPT = myPTs.get(idx);
      } catch (NumberFormatException e) {
        System.out.println("올바른 번호를 입력해주세요.");
        return;
      }
    }

    Trainer trainer = pt.findTrainer(selectedPT.getTrainerId());
    if (trainer == null) {
      System.out.println("트레이너 스케줄 정보를 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
      return;
    }

    cu.showStep(2, trainer.getName() + " 트레이너의 예약 가능한 스케줄을 출력합니다.");
    System.out.printf("이용권: %s | 잔여 횟수: %d회%n",
        selectedPT.getProductName(), selectedPT.getRemainingCount());

    LocalTime[] timeSlots = {LocalTime.of(10, 0), LocalTime.of(14, 0), LocalTime.of(18, 0)};
    LocalDate today = LocalDate.now();

    cu.showSeparator();
    System.out.println("No.  날짜          시간     상태");
    List<LocalDate> slotDates = new ArrayList<>();
    List<LocalTime> slotTimes = new ArrayList<>();
    int slotNo = 1;

    for (int d = 1; d <= 7; d++) {
      LocalDate date = today.plusDays(d);
      for (LocalTime time : timeSlots) {
        boolean booked = pt.isSlotBooked(trainer.getTrainerId(), date, time);
        String status = booked ? "[예약됨]  " : "[예약 가능]";
        System.out.printf("%2d.  %s  %s  %s%n", slotNo, date, time, status);
        slotDates.add(date);
        slotTimes.add(time);
        slotNo++;
      }
    }
    cu.showSeparator();
    System.out.println("0. 돌아가기");

    LocalDate chosenDate = null;
    LocalTime chosenTime = null;
    while (chosenDate == null) {
      cu.showPrompt();
      String input = iu.readLine();
      if (input.equals("0")) return;
      try {
        int idx = Integer.parseInt(input) - 1;
        if (idx < 0 || idx >= slotDates.size()) {
          System.out.println("올바른 번호를 입력해주세요.");
          continue;
        }
        LocalDate cDate = slotDates.get(idx);
        LocalTime cTime = slotTimes.get(idx);
        if (pt.isSlotBooked(trainer.getTrainerId(), cDate, cTime)) {
          System.out.println("선택하신 시간은 이미 예약되어 있습니다.");
          continue;
        }
        chosenDate = cDate;
        chosenTime = cTime;
      } catch (NumberFormatException e) {
        System.out.println("올바른 번호를 입력해주세요.");
      }
    }

    cu.showStep(4, "예약 확인 화면을 출력합니다.");
    cu.showSeparator();
    System.out.println("[예약 확인]");
    System.out.println("트레이너 : " + trainer.getName());
    System.out.println("날짜     : " + chosenDate);
    System.out.println("시간     : " + chosenTime);
    cu.showSeparator();
    System.out.println("[예약 확정] 진행하시겠습니까? (Y/N)");
    cu.showPrompt();
    if (!iu.readLine().equalsIgnoreCase("Y")) return;

    boolean reserved = pt.reserveSchedule(selectedPT.getMemberProductId(), memberId,
        trainer.getTrainerId(), chosenDate, chosenTime);
    if (!reserved) {
      System.out.println("예약 처리 중 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.");
      return;
    }
    selectedPT.setRemainingCount(selectedPT.getRemainingCount() - 1);
    if (selectedPT.getRemainingCount() == 0) selectedPT.setStatus("COMPLETED");
    pt.updateMemberProduct(selectedPT);
    System.out.println("[알림] " + trainer.getName() + " 트레이너에게 신규 예약 알림을 전송했습니다.");
    cu.showStep(7, "PT 일정이 예약되었습니다.");
  }
}
