package distbuted_programming.domain.member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Member {

  private String memberId;
  private String name;
  private String nickname;
  private String loginId;
  private String password;
  private String phone;
  private LocalDate birthDate;
  private String bodyInfo;
  private String address;
  private String profileImage;
  private LocalDate joinDate;

  private static final List<Member> members = new ArrayList<>();

  public Member() {}

  public Member(String memberId, String name, String nickname, String loginId,
      String password, String phone, LocalDate birthDate, String bodyInfo,
      String address, String profileImage, LocalDate joinDate) {
    this.memberId = memberId;
    this.name = name;
    this.nickname = nickname;
    this.loginId = loginId;
    this.password = password;
    this.phone = phone;
    this.birthDate = birthDate;
    this.bodyInfo = bodyInfo;
    this.address = address;
    this.profileImage = profileImage;
    this.joinDate = joinDate;
  }

  public Member register(String name, String nickname, String loginId, String password,
      String phone, LocalDate birthDate, String bodyInfo, String address,
      String profileImage) {
    Member m = new Member(UUID.randomUUID().toString(), name, nickname, loginId,
        password, phone, birthDate, bodyInfo, address, profileImage, LocalDate.now());
    members.add(m);
    return m;
  }

  public Member login(String loginId, String password) {
    for (Member m : members) {
      if (m.loginId.equals(loginId) && m.password.equals(password)) {
        return m;
      }
    }
    return null;
  }

  public boolean checkLoginIdDuplicate(String loginId) {
    for (Member m : members) {
      if (m.loginId.equals(loginId)) {
        return true;
      }
    }
    return false;
  }

  public Member getInfo() {
    return this;
  }

  public void updateInfo(String nickname, String phone, String password,
      String profileImage, String bodyInfo, String address) {
    if (nickname != null && !nickname.isEmpty()) this.nickname = nickname;
    if (phone != null && !phone.isEmpty()) this.phone = phone;
    if (password != null && !password.isEmpty()) this.password = password;
    if (profileImage != null && !profileImage.isEmpty()) this.profileImage = profileImage;
    if (bodyInfo != null && !bodyInfo.isEmpty()) this.bodyInfo = bodyInfo;
    if (address != null && !address.isEmpty()) this.address = address;
  }

  public void withdraw() {
    members.remove(this);
  }

  public String getMemberId() { return memberId; }
  public String getName() { return name; }
  public String getNickname() { return nickname; }
  public String getLoginId() { return loginId; }
  public String getPassword() { return password; }
  public String getPhone() { return phone; }
  public LocalDate getBirthDate() { return birthDate; }
  public String getBodyInfo() { return bodyInfo; }
  public String getAddress() { return address; }
  public String getProfileImage() { return profileImage; }
  public LocalDate getJoinDate() { return joinDate; }

  public static List<Member> getAll() { return members; }

  @Override
  public String toString() {
    return "이름: " + name + " | 닉네임: " + nickname
        + " | 아이디: " + loginId + " | 전화번호: " + phone;
  }
}
