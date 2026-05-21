package com.fitcoach.client.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.product.PT;
import com.fitcoach.client.model.schedule.PTSchedule;
import com.fitcoach.client.model.schedule.Trainer;

public class PTController {
  private List<Trainer> trainers;
  private List<PTSchedule> ptSchedules;
  private List<PT> memberPTs;

  public PTController() {
    this.trainers = new ArrayList<>();
    this.ptSchedules = new ArrayList<>();
    this.memberPTs = new ArrayList<>();
  }

  public boolean init() {
    return true;
  }

  public List<Trainer> getAllTrainers() {
    return new ArrayList<>(trainers);
  }

  public List<Trainer> filterTrainers(String keyword) {
    List<Trainer> result = new ArrayList<>();
    for (Trainer t : trainers) {
      if (t.getSpecialty().contains(keyword)) result.add(t);
    }
    return result;
  }

  public Trainer findTrainer(String trainerId) {
    for (Trainer t : trainers) {
      if (t.getTrainerId().equals(trainerId)) return t;
    }
    return null;
  }

  public List<PT> getActivePTsByMember(String memberId) {
    List<PT> result = new ArrayList<>();
    for (PT pt : memberPTs) {
      if (pt.getMemberId().equals(memberId)
          && "ACTIVE".equals(pt.getStatus())
          && pt.getRemainingCount() > 0) {
        result.add(pt);
      }
    }
    return result;
  }

  public boolean isSlotBooked(String trainerId, LocalDate date, LocalTime time) {
    for (PTSchedule s : ptSchedules) {
      if (s.getTrainerId().equals(trainerId)
          && s.getDate().equals(date)
          && s.getTime().equals(time)) {
        return true;
      }
    }
    return false;
  }

  public boolean reserveSchedule(String ptId, String memberId, String trainerId,
      LocalDate date, LocalTime time) {
    String scheduleId = "sched-" + String.format("%03d", ptSchedules.size() + 1);
    PTSchedule schedule = new PTSchedule(scheduleId, ptId, memberId, trainerId,
        date, time, "RESERVED");
    if (!schedule.init()) return false;
    ptSchedules.add(schedule);
    return true;
  }

  public boolean addMemberPT(PT newPT) {
    if (!newPT.init()) return false;
    memberPTs.add(newPT);
    return true;
  }

  public List<Trainer> getTrainers() { return trainers; }
  public List<PT> getMemberPTs() { return memberPTs; }
  public List<PTSchedule> getPtSchedules() { return ptSchedules; }
}
