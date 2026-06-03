package com.fitcoach.client.controller;

import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.equipment.Equipment;
import com.fitcoach.client.model.equipment.ExerciseMethod;
import com.fitcoach.client.model.notice.Notice;

public class InfoController {
  private List<Equipment> equipments;
  private List<Notice> notices;

  public InfoController() {
    this.equipments = new ArrayList<>();
    this.notices = new ArrayList<>();
  }

  public boolean init() {
    return true;
  }

  public List<Equipment> getEquipments() { return equipments; }
  public List<Notice> getNotices() { return notices; }

  public List<Equipment> getAllEquipments() {
    return new ArrayList<>(equipments);
  }

  public List<Equipment> searchEquipments(String keyword) {
    List<Equipment> result = new ArrayList<>();
    for (Equipment eq : equipments) {
      if (eq.getName().contains(keyword) || eq.getCategory().contains(keyword)) {
        result.add(eq);
      }
    }
    return result;
  }

  public boolean initEquipmentDetail(Equipment equipment) {
    return equipment.init();
  }

  public List<Notice> getAllNoticesSorted() {
    List<Notice> sorted = new ArrayList<>(notices);
    sorted.sort((a, b) -> b.getWriteDate().compareTo(a.getWriteDate()));
    return sorted;
  }

  public boolean initNoticeDetail(Notice notice) {
    return notice.init();
  }

  public void markNoticeAsRead(Notice notice, String memberId) {
    notice.markAsRead(memberId);
  }

  public boolean initExerciseMethodDetail(ExerciseMethod method) {
    return method.init();
  }
}
