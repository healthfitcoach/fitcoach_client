package com.fitcoach.client.domain.purchase.repository;

import com.fitcoach.client.model.product.PTSubscription;
import com.fitcoach.client.model.product.PTSubscription.PTStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PTSubscriptionRepository extends JpaRepository<PTSubscription, Long> {

  List<PTSubscription> findByMemberIdAndStatus(Long memberId, PTStatus status);

  @Query("SELECT COALESCE(SUM(p.remainingCount), 0) FROM PTSubscription p " +
      "WHERE p.memberId = :memberId AND p.status = :status")
  int sumRemainingCountByMemberIdAndStatus(Long memberId, PTStatus status);
}
