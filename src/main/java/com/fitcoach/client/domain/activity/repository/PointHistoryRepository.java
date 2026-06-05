package com.fitcoach.client.domain.activity.repository;

import com.fitcoach.client.model.point.PointHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository extends JpaRepository<PointHistory, String> {

  List<PointHistory> findByMemberIdOrderByDateDesc(String memberId);
}
