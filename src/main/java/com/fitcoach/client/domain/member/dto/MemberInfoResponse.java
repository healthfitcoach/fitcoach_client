package com.fitcoach.client.domain.member.dto;

import com.fitcoach.client.model.member.Member;
import java.time.LocalDate;

public record MemberInfoResponse(
    Long id,
    String loginId,
    String name,
    String nickname,
    String phone,
    LocalDate birthDate,
    String bodyInfo,
    String address,
    String profileImage,
    LocalDate joinDate
) {
  public static MemberInfoResponse from(Member member) {
    return new MemberInfoResponse(
        member.getId(), member.getLoginId(), member.getName(), member.getNickname(),
        member.getPhone(), member.getBirthDate(), member.getBodyInfo(),
        member.getAddress(), member.getProfileImage(), member.getJoinDate());
  }
}
