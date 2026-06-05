package com.fitcoach.client.domain.pt;

import com.fitcoach.client.domain.pt.repository.PTScheduleRepository;
import com.fitcoach.client.domain.pt.repository.TrainerRepository;
import com.fitcoach.client.domain.purchase.repository.PTSubscriptionRepository;
import com.fitcoach.client.global.exception.CustomException;
import com.fitcoach.client.global.exception.ErrorCode;
import com.fitcoach.client.model.product.PTSubscription;
import com.fitcoach.client.model.product.PTSubscription.PTStatus;
import com.fitcoach.client.model.schedule.PTSchedule;
import com.fitcoach.client.model.schedule.PTSchedule.ScheduleStatus;
import com.fitcoach.client.model.schedule.Trainer;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PTService {

  private final TrainerRepository trainerRepository;
  private final PTSubscriptionRepository ptSubscriptionRepository;
  private final PTScheduleRepository ptScheduleRepository;

  public PTService(TrainerRepository trainerRepository,
      PTSubscriptionRepository ptSubscriptionRepository,
      PTScheduleRepository ptScheduleRepository) {
    this.trainerRepository = trainerRepository;
    this.ptSubscriptionRepository = ptSubscriptionRepository;
    this.ptScheduleRepository = ptScheduleRepository;
  }

  @Transactional(readOnly = true)
  public List<Trainer> getAllTrainers() {
    return trainerRepository.findAll();
  }

  @Transactional(readOnly = true)
  public List<Trainer> filterTrainers(String keyword) {
    return trainerRepository.findBySpecialtyContaining(keyword);
  }

  @Transactional(readOnly = true)
  public Trainer findTrainer(Long trainerId) {
    return trainerRepository.findById(trainerId)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public List<PTSubscription> getActivePTsByMember(Long memberId) {
    return ptSubscriptionRepository.findByMemberIdAndStatus(memberId, PTStatus.ACTIVE);
  }

  @Transactional(readOnly = true)
  public boolean isSlotBooked(Long trainerId, LocalDate date, String time) {
    return ptScheduleRepository.existsByTrainerIdAndScheduleDateAndScheduleTimeAndStatusNot(
        trainerId, date, time, ScheduleStatus.CANCELLED);
  }

  @Transactional
  public PTSchedule reserveSchedule(Long ptId, Long memberId, Long trainerId,
      LocalDate date, String time) {
    if (isSlotBooked(trainerId, date, time)) {
      throw new CustomException(ErrorCode.SLOT_ALREADY_BOOKED);
    }
    PTSubscription pt = ptSubscriptionRepository.findById(ptId)
        .orElseThrow(() -> new CustomException(ErrorCode.PT_NOT_FOUND));

    pt.setRemainingCount(pt.getRemainingCount() - 1);
    if (pt.getRemainingCount() == 0) {
      pt.setStatus(PTStatus.COMPLETED);
    }
    ptSubscriptionRepository.save(pt);

    PTSchedule schedule = new PTSchedule(ptId, trainerId, memberId, date, time, ScheduleStatus.SCHEDULED);
    return ptScheduleRepository.save(schedule);
  }

  @Transactional(readOnly = true)
  public List<PTSchedule> getSchedulesByTrainerAndDate(Long trainerId, LocalDate date) {
    return ptScheduleRepository.findByTrainerIdAndScheduleDate(trainerId, date);
  }
}
