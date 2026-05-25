package com.fitcoach.client.model.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member {

  @Id
  @Column(name = "member_id")
  private String memberId;

  @Column(name = "login_id")
  private String loginId;

  @Column(name = "password")
  private String password;

  @Column(name = "name")
  private String name;

  @Column(name = "nickname")
  private String nickname;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "birth_date")
  private String birthDate;

  @Column(name = "physical_info")
  private String physicalInfo;

  @Column(name = "address")
  private String address;

  @Column(name = "profile_picture")
  private String profilePicture;

  @Column(name = "join_date")
  private LocalDate joinDate;

  @Transient  // Aggregation 필드 — DB 컬럼 아님
  private List<Attendance> attendances;

  public Member() {  // JPA 필수 no-arg 생성자
    this.attendances = new ArrayList<>();
  }

  public Member(String memberId, String loginId, String password, String name,
      String nickname, String phoneNumber, String birthDate,
      String physicalInfo, String address, String profilePicture,
      LocalDate joinDate) {
    this.memberId = memberId;
    this.loginId = loginId;
    this.password = password;
    this.name = name;
    this.nickname = nickname;
    this.phoneNumber = phoneNumber;
    this.birthDate = birthDate;
    this.physicalInfo = physicalInfo;
    this.address = address;
    this.profilePicture = profilePicture;
    this.joinDate = joinDate;
    this.attendances = new ArrayList<>();
  }

  public boolean init() { return true; }

  public void signUp() {}

  public boolean login(String loginId, String password) { return false; }

  public boolean checkDuplicateId(String loginId) { return false; }

  public void updateInfo() {}

  public void getInfo() {}

  public void withdraw() {}

  // Getters & Setters
  public String getMemberId() { return memberId; }
  public String getLoginId() { return loginId; }
  public String getPassword() { return password; }
  public String getName() { return name; }
  public String getNickname() { return nickname; }
  public void setNickname(String nickname) { this.nickname = nickname; }
  public String getPhoneNumber() { return phoneNumber; }
  public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
  public String getBirthDate() { return birthDate; }
  public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
  public String getPhysicalInfo() { return physicalInfo; }
  public void setPhysicalInfo(String physicalInfo) { this.physicalInfo = physicalInfo; }
  public String getAddress() { return address; }
  public void setAddress(String address) { this.address = address; }
  public String getProfilePicture() { return profilePicture; }
  public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }
  public LocalDate getJoinDate() { return joinDate; }
  public void setPassword(String password) { this.password = password; }
  public List<Attendance> getAttendances() { return attendances; }
  public void addAttendance(Attendance attendance) { this.attendances.add(attendance); }
}
