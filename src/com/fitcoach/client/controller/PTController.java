package com.fitcoach.client.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import com.fitcoach.client.model.product.PT;
import com.fitcoach.client.model.schedule.PTSchedule;
import com.fitcoach.client.model.schedule.Trainer;
import db.DBA;
import db.dao.PTDao;

public class PTController {
  private PTDao dao;

  public PTController(DBA dba) {
    this.dao = new PTDao(dba);
  }

  public boolean init() {
    return dao.init();
  }

  public List<Trainer> getAllTrainers() {
    return dao.findAllTrainers();
  }

  public List<Trainer> filterTrainers(String keyword) {
    return dao.searchTrainersBySpecialty(keyword);
  }

  public Trainer findTrainer(String trainerId) {
    return dao.findTrainerById(trainerId);
  }

  public List<PT> getActivePTsByMember(String memberId) {
    return dao.findActivePTsByMemberId(memberId);
  }

  public boolean isSlotBooked(String trainerId, LocalDate date, LocalTime time) {
    return dao.isSlotBooked(trainerId, LocalDateTime.of(date, time));
  }

  public boolean reserveSchedule(String ptId, String memberId, String trainerId,
      LocalDate date, LocalTime time) {
    String scheduleId = "sched-" + UUID.randomUUID().toString().substring(0, 8);
    PTSchedule schedule = new PTSchedule(scheduleId, ptId, memberId, trainerId,
        date, time, "RESERVED");
    if (!schedule.init()) return false;
    return dao.savePTSchedule(schedule);
  }

  public boolean updatePT(PT pt) {
    return dao.updatePT(pt);
  }
}
