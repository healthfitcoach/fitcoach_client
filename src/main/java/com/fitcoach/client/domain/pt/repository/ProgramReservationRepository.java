package com.fitcoach.client.domain.pt.repository;

import com.fitcoach.client.model.schedule.ProgramReservation;
import com.fitcoach.client.model.schedule.ProgramReservation.ReservationStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramReservationRepository extends JpaRepository<ProgramReservation, Long> {

  List<ProgramReservation> findByMemberIdAndStatus(Long memberId, ReservationStatus status);
}
