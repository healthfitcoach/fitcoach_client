package com.fitcoach.client.domain.auth.repository;

import com.fitcoach.client.model.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByLoginId(String loginId);

  boolean existsByLoginId(String loginId);

  boolean existsByPhoneAndIdNot(String phone, Long id);
}
