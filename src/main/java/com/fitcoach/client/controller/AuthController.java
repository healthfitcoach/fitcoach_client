package com.fitcoach.client.controller;

import java.time.LocalDate;
import java.util.UUID;
import com.fitcoach.client.model.member.Member;
import db.DBA;
import db.dao.AuthDao;

public class AuthController {
  private AuthDao dao;
  private Member currentMember;

  public AuthController(DBA dba) {
    this.dao = new AuthDao(dba);
    this.currentMember = null;
  }

  public boolean init() {
    return dao.init();
  }

  public boolean isDuplicateLoginId(String loginId) {
    return dao.existsByLoginId(loginId);
  }

  public boolean createMember(String loginId, String password, String name,
      String nickname, String phoneNumber, String birthDate,
      String physicalInfo, String address, String profilePicture) {
    String memberId = "member-" + UUID.randomUUID().toString().substring(0, 8);
    Member newMember = new Member(memberId, loginId, password, name, nickname,
        phoneNumber, birthDate, physicalInfo, address, profilePicture, LocalDate.now());
    if (!newMember.init()) return false;
    return dao.save(newMember);
  }

  public Member validateLogin(String loginId, String password) {
    Member member = dao.findByLoginId(loginId);
    if (member != null && member.getPassword().equals(password)) return member;
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
}
