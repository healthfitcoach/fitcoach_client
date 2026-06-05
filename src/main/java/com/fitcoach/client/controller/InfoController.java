package com.fitcoach.client.controller;

import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.equipment.Equipment;
import com.fitcoach.client.model.equipment.ExerciseMethod;
import com.fitcoach.client.model.notice.Notice;
import db.DBA;
import db.dao.InfoDao;

public class InfoController {
  private InfoDao dao;

  public InfoController(DBA dba) {
    this.dao = new InfoDao(dba);
  }

  public boolean init() {
    return dao.init();
  }

  public List<Equipment> getAllEquipments() {
    return dao.findAllEquipments();
  }

  public List<Equipment> searchEquipments(String keyword) {
    return dao.searchEquipments(keyword);
  }

  public List<ExerciseMethod> getExerciseMethodsByEquipment(String equipmentId) {
    return dao.findExerciseMethodsByEquipmentId(equipmentId);
  }

  public boolean initEquipmentDetail(Equipment equipment) {
    return equipment.init();
  }

  public List<Notice> getAllNoticesSorted() {
    List<Notice> sorted = new ArrayList<>(dao.findAllNotices());
    sorted.sort((a, b) -> b.getWriteDate().compareTo(a.getWriteDate()));
    return sorted;
  }

  public boolean initNoticeDetail(Notice notice) {
    return notice.init();
  }

  public void markNoticeAsRead(Notice notice, String memberId) {
    notice.markAsRead(memberId);
    dao.updateNoticeReadStatus(notice.getNoticeId(), memberId);
  }

  public boolean initExerciseMethodDetail(ExerciseMethod method) {
    return method.init();
  }
}
