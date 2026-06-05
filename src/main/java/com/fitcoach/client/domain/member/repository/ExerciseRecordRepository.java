package com.fitcoach.client.domain.member.repository;

import com.fitcoach.client.model.member.ExerciseRecord;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecord, String> {

  List<ExerciseRecord> findByMemberId(String memberId);

  boolean existsByMemberIdAndExerciseDate(String memberId, LocalDate date);
}
