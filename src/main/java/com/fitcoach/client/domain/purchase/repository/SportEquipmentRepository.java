package com.fitcoach.client.domain.purchase.repository;

import com.fitcoach.client.model.product.SportEquipment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SportEquipmentRepository extends JpaRepository<SportEquipment, Long> {

  @Query("SELECT s FROM SportEquipment s WHERE s.name LIKE %:keyword% OR s.category LIKE %:keyword%")
  List<SportEquipment> searchByKeyword(@Param("keyword") String keyword);
}
