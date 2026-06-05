package com.fitcoach.client.domain.pt.repository;

import com.fitcoach.client.model.schedule.PTSchedule;
import com.fitcoach.client.model.schedule.PTSchedule.ScheduleStatus;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PTScheduleRepository extends JpaRepository<PTSchedule, Long> {

  boolean existsByTrainerIdAndScheduleDateAndScheduleTimeAndStatusNot(
      Long trainerId, LocalDate scheduleDate, String scheduleTime, ScheduleStatus status);

  List<PTSchedule> findByTrainerIdAndScheduleDate(Long trainerId, LocalDate scheduleDate);

  List<PTSchedule> findByPtIdOrderByScheduleDateAsc(Long ptId);
}
