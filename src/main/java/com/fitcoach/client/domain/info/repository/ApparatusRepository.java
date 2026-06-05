package com.fitcoach.client.domain.info.repository;

import com.fitcoach.client.model.equipment.Apparatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApparatusRepository extends JpaRepository<Apparatus, Long> {

  List<Apparatus> findByNameContainingOrCategoryContaining(String name, String category);
}
