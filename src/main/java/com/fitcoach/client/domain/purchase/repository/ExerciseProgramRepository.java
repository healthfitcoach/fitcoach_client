package com.fitcoach.client.domain.purchase.repository;

import com.fitcoach.client.model.product.ExerciseProgram;
import com.fitcoach.client.model.product.Product.ProductStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseProgramRepository extends JpaRepository<ExerciseProgram, Long> {

  List<ExerciseProgram> findByStatus(ProductStatus status);
}
