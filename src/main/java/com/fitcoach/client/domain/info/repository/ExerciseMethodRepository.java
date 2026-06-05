package com.fitcoach.client.domain.info.repository;

import com.fitcoach.client.model.equipment.ExerciseMethod;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseMethodRepository extends JpaRepository<ExerciseMethod, String> {

  List<ExerciseMethod> findByEquipmentId(String equipmentId);
}
