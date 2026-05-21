package com.fitcoach.client.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.member.Member;

public class AuthController {
  private List<Member> members;
  private Member currentMember;

  public AuthController() {
    this.members = new ArrayList<>();
    this.currentMember = null;
  }

  public boolean init() {
    return true;
  }

  public boolean isDuplicateLoginId(String loginId) {
    return members.stream().anyMatch(m -> m.getLoginId().equals(loginId));
  }

  public boolean createMember(String loginId, String password, String name,
      String nickname, String phoneNumber, String birthDate,
      String physicalInfo, String address, String profilePicture) {
    String memberId = "member-" + String.format("%03d", members.size() + 1);
    Member newMember = new Member(memberId, loginId, password, name, nickname,
        phoneNumber, birthDate, physicalInfo, address, profilePicture, LocalDate.now());
    if (!newMember.init()) return false;
    members.add(newMember);
    return true;
  }

  public Member validateLogin(String loginId, String password) {
    for (Member m : members) {
      if (m.getLoginId().equals(loginId) && m.getPassword().equals(password)) {
        return m;
      }
    }
    return null;
  }

  public boolean login(Member member) {
    if (!member.init()) return false;
    currentMember = member;
    return true;
  }

  public void logout() {
    currentMember = null;
  }

  public boolean isLoggedIn() {
    return currentMember != null;
  }

  public Member getCurrentMember() {
    return currentMember;
  }

  public List<Member> getMembers() {
    return members;
  }
}
