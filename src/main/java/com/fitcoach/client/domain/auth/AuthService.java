package com.fitcoach.client.domain.auth;

import com.fitcoach.client.domain.auth.repository.MemberRepository;
import com.fitcoach.client.global.exception.CustomException;
import com.fitcoach.client.global.exception.ErrorCode;
import com.fitcoach.client.model.member.Member;
import java.time.LocalDate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
    this.memberRepository = memberRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public boolean isDuplicateLoginId(String loginId) {
    return memberRepository.existsByLoginId(loginId);
  }

  @Transactional
  public Member signup(String loginId, String password, String name, String nickname,
      String phone, LocalDate birthDate, String bodyInfo,
      String address, String profileImage) {
    if (memberRepository.existsByLoginId(loginId)) {
      throw new CustomException(ErrorCode.DUPLICATE_LOGIN_ID);
    }
    Member member = new Member(
        loginId, passwordEncoder.encode(password), name, nickname,
        phone, birthDate, bodyInfo, address, profileImage, LocalDate.now()
    );
    return memberRepository.save(member);
  }

  @Transactional(readOnly = true)
  public Member login(String loginId, String password) {
    Member member = memberRepository.findByLoginId(loginId)
        .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIALS));
    if (!passwordEncoder.matches(password, member.getPassword())) {
      throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
    }
    return member;
  }

  @Transactional(readOnly = true)
  public Member findById(Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
  }
}
