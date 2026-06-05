package com.fitcoach.client.view;

import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.controller.AuthController;
import com.fitcoach.client.controller.InfoController;
import com.fitcoach.client.model.equipment.Equipment;
import com.fitcoach.client.model.equipment.ExerciseMethod;
import com.fitcoach.client.model.notice.Notice;
import com.fitcoach.client.util.ConsoleUtil;
import com.fitcoach.client.util.InputUtil;

public class InfoView {
  private InputUtil iu;
  private ConsoleUtil cu;
  private AuthController auth;
  private InfoController info;

  public InfoView(InputUtil iu, ConsoleUtil cu, AuthController auth, InfoController info) {
    this.iu = iu;
    this.cu = cu;
    this.auth = auth;
    this.info = info;
  }

  public void showSearchEquipment() {
    cu.showStep(2, "기구 전체 목록을 출력합니다.");
    List<Equipment> displayList = info.getAllEquipments();
    printEquipmentList(displayList);
    System.out.println("F. 검색/카테고리 필터   0. 돌아가기");

    while (true) {
      cu.showPrompt();
      String input = iu.readLine();
      if (input.equals("0")) return;

      if (input.equalsIgnoreCase("F")) {
        System.out.print("기구명 또는 카테고리(유산소/근력/스트레칭) 입력: ");
        String keyword = iu.readLine();
        displayList = info.searchEquipments(keyword);
        if (displayList.isEmpty()) {
          System.out.println("검색 결과가 없습니다. 전체 목록을 표시합니다.");
          displayList = info.getAllEquipments();
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

        cu.showStep(4, "선택한 기구의 상세 정보를 출력합니다.");
        if (!info.initEquipmentDetail(selected)) {
          System.out.println("기구 상세 정보를 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
          return;
        }
        cu.showSeparator();
        System.out.println("기구명   : " + selected.getName());
        System.out.println("카테고리 : " + selected.getCategory());
        System.out.println("설명     : " + selected.getDescription());
        System.out.println("상태     : " + selected.getStatus());

        cu.showSeparator();
        List<ExerciseMethod> methods = selected.getExerciseMethods();
        if (methods.isEmpty()) {
          System.out.println("등록된 운동 방법이 없습니다.");
        } else {
          System.out.println("[운동 방법 목록]");
          for (int i = 0; i < methods.size(); i++) {
            System.out.printf("%d. %s (%s)%n",
                i + 1, methods.get(i).getExerciseName(), methods.get(i).getDifficulty());
          }
          System.out.println("번호 입력: 운동방법 상세 조회   0. 돌아가기");
          cu.showPrompt();
          String methodInput = iu.readLine();
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

  public void showViewExerciseMethod() {
    showSearchEquipment();
  }

  public void showViewNotice() {
    cu.showStep(2, "공지사항 목록을 최신순으로 출력합니다.");
    List<Notice> sorted = info.getAllNoticesSorted();
    if (sorted.isEmpty()) {
      System.out.println("공지사항을 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
      return;
    }

    String memberId = auth.isLoggedIn() ? auth.getCurrentMember().getMemberId() : "";

    int idx = 0;
    while (true) {
      cu.showSeparator();
      for (int i = 0; i < sorted.size(); i++) {
        Notice n = sorted.get(i);
        String mark = n.isReadBy(memberId) ? "[읽음]  " : "[미읽음]";
        System.out.printf("%d. %s [%s] %s (%s)%n",
            i + 1, mark, n.getCategory(), n.getTitle(), n.getWriteDate());
      }
      System.out.println("0. 돌아가기");
      cu.showPrompt();

      String input = iu.readLine();
      if (input.equals("0")) return;

      try {
        idx = Integer.parseInt(input) - 1;
        if (idx < 0 || idx >= sorted.size()) {
          System.out.println("올바른 번호를 입력해주세요.");
          continue;
        }

        cu.showStep(5, "선택한 공지사항의 상세 내용을 출력합니다.");
        Notice selected = sorted.get(idx);
        if (!info.initNoticeDetail(selected)) {
          System.out.println("해당 공지사항을 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
          return;
        }
        printNoticeDetail(selected);
        if (!memberId.isEmpty()) info.markNoticeAsRead(selected, memberId);
        cu.showStep(6, "읽음 처리가 완료되었습니다.");

        while (true) {
          System.out.println("P. 이전 공지   N. 다음 공지   D. 첨부파일 다운로드   0. 목록으로");
          cu.showPrompt();
          String nav = iu.readLine();
          if (nav.equals("0")) break;

          if (nav.equalsIgnoreCase("P")) {
            if (idx == 0) System.out.println("이전 공지사항이 없습니다.");
            else {
              idx--;
              selected = sorted.get(idx);
              printNoticeDetail(selected);
              if (!memberId.isEmpty()) info.markNoticeAsRead(selected, memberId);
            }
          } else if (nav.equalsIgnoreCase("N")) {
            if (idx == sorted.size() - 1) System.out.println("다음 공지사항이 없습니다.");
            else {
              idx++;
              selected = sorted.get(idx);
              printNoticeDetail(selected);
              if (!memberId.isEmpty()) info.markNoticeAsRead(selected, memberId);
            }
          } else if (nav.equalsIgnoreCase("D")) {
            if ("없음".equals(selected.getAttachment()) || selected.getAttachment().isEmpty()) {
              System.out.println("첨부파일이 없습니다.");
            } else {
              System.out.println("첨부파일 '" + selected.getAttachment() + "' 다운로드가 완료되었습니다.");
            }
          }
        }
        cu.showStep(10, "공지사항 목록으로 돌아갑니다.");
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

  private void showExerciseMethodDetail(ExerciseMethod method) {
    cu.showStep(1, "운동 방법 상세 정보를 출력합니다.");
    if (!info.initExerciseMethodDetail(method)) {
      System.out.println("운동방법 정보를 불러오는 데 실패하였습니다. 잠시 후 다시 시도해주세요.");
      return;
    }
    cu.showSeparator();
    System.out.println("운동명    : " + method.getExerciseName());
    System.out.println("대상 부위 : " + method.getTargetBodyPart());
    System.out.println("난이도    : " + method.getDifficulty());
    System.out.println("준비 자세 : " + method.getPreparationPose());
    System.out.println("[단계별 운동 방법]");
    System.out.println(method.getStepByStepMethod());
    cu.showSeparator();
    System.out.println("참고 이미지: " + method.getImage());
    System.out.println("영상 URL   : " + method.getVideoUrl());
    cu.showSeparator();
  }

  private void printNoticeDetail(Notice n) {
    cu.showSeparator();
    System.out.println("제목     : " + n.getTitle());
    System.out.println("카테고리 : " + n.getCategory());
    System.out.println("작성일   : " + n.getWriteDate());
    System.out.println("첨부파일 : " + n.getAttachment());
    System.out.println("[내용]");
    System.out.println(n.getContent());
    cu.showSeparator();
  }
}
