package com.fitcoach.client.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class SignupRequest {

  @NotBlank(message = "아이디를 입력해주세요.")
  private String loginId;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
  private String password;

  @NotBlank(message = "이름을 입력해주세요.")
  private String name;

  @NotBlank(message = "닉네임을 입력해주세요.")
  private String nickname;

  @NotBlank(message = "전화번호를 입력해주세요.")
  private String phone;

  private LocalDate birthDate;
  private String bodyInfo;
  private String address;
  private String profileImage;

  public String getLoginId() { return loginId; }
  public String getPassword() { return password; }
  public String getName() { return name; }
  public String getNickname() { return nickname; }
  public String getPhone() { return phone; }
  public LocalDate getBirthDate() { return birthDate; }
  public String getBodyInfo() { return bodyInfo; }
  public String getAddress() { return address; }
  public String getProfileImage() { return profileImage; }
}
