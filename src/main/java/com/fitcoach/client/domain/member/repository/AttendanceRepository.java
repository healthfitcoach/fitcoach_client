package com.fitcoach.client.domain.member.repository;

import com.fitcoach.client.model.member.Attendance;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

  List<Attendance> findByMemberIdOrderByAttendanceDateTimeDesc(Long memberId);

  boolean existsByMemberIdAndAttendanceDateTimeBetween(
      Long memberId, LocalDateTime start, LocalDateTime end);
}
