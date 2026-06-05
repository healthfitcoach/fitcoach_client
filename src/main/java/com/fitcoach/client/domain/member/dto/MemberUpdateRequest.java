package com.fitcoach.client.domain.member.dto;

import java.time.LocalDate;

public class MemberUpdateRequest {

  private String nickname;
  private String phone;
  private LocalDate birthDate;
  private String bodyInfo;
  private String address;
  private String profileImage;
  private String newPassword;

  public String getNickname() { return nickname; }
  public String getPhone() { return phone; }
  public LocalDate getBirthDate() { return birthDate; }
  public String getBodyInfo() { return bodyInfo; }
  public String getAddress() { return address; }
  public String getProfileImage() { return profileImage; }
  public String getNewPassword() { return newPassword; }
}
