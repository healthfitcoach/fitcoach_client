package com.fitcoach.client.domain.pt.repository;

import com.fitcoach.client.model.schedule.Trainer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

  List<Trainer> findBySpecialtyContaining(String keyword);
}
