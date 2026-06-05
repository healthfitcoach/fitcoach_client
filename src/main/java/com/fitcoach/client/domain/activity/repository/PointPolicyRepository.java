package com.fitcoach.client.domain.activity.repository;

import com.fitcoach.client.model.point.PointPolicy;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointPolicyRepository extends JpaRepository<PointPolicy, String> {

  Optional<PointPolicy> findFirstByOrderByPolicyIdDesc();
}
