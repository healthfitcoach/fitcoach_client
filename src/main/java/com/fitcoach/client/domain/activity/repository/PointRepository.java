package com.fitcoach.client.domain.activity.repository;

import com.fitcoach.client.model.point.Point;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, String> {

  Optional<Point> findByMemberId(String memberId);
}
