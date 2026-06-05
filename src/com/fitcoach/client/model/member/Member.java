package com.fitcoach.client.model.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "login_id")
  private String loginId;

  @Column(name = "password")
  private String password;

  @Column(name = "name")
  private String name;

  @Column(name = "nickname")
  private String nickname;

  @Column(name = "phone")
  private String phone;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Column(name = "body_info")
  private String bodyInfo;

  @Column(name = "address")
  private String address;

  @Column(name = "profile_image")
  private String profileImage;

  @Column(name = "join_date")
  private LocalDate joinDate;

  @Transient
  private List<Attendance> attendances;

  public Member() {
    this.attendances = new ArrayList<>();
  }

  public Member(String loginId, String password, String name, String nickname,
      String phone, LocalDate birthDate, String bodyInfo,
      String address, String profileImage, LocalDate joinDate) {
    this.loginId = loginId;
    this.password = password;
    this.name = name;
    this.nickname = nickname;
    this.phone = phone;
    this.birthDate = birthDate;
    this.bodyInfo = bodyInfo;
    this.address = address;
    this.profileImage = profileImage;
    this.joinDate = joinDate;
    this.attendances = new ArrayList<>();
  }

  public boolean init() { return true; }

  public Long getId() { return id; }
  public String getLoginId() { return loginId; }
  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
  public String getName() { return name; }
  public String getNickname() { return nickname; }
  public void setNickname(String nickname) { this.nickname = nickname; }
  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }
  public LocalDate getBirthDate() { return birthDate; }
  public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
  public String getBodyInfo() { return bodyInfo; }
  public void setBodyInfo(String bodyInfo) { this.bodyInfo = bodyInfo; }
  public String getAddress() { return address; }
  public void setAddress(String address) { this.address = address; }
  public String getProfileImage() { return profileImage; }
  public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
  public LocalDate getJoinDate() { return joinDate; }
  public List<Attendance> getAttendances() { return attendances; }
  public void addAttendance(Attendance attendance) { this.attendances.add(attendance); }
}
