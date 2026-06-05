package com.fitcoach.client.domain.purchase.repository;

import com.fitcoach.client.model.product.Membership;
import com.fitcoach.client.model.product.Membership.MembershipStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

  List<Membership> findByMemberId(Long memberId);

  Optional<Membership> findFirstByMemberIdAndStatusOrderByStartDateDesc(
      Long memberId, MembershipStatus status);
}
