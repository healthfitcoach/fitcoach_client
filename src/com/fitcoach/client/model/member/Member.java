package com.fitcoach.client.model.member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Member {

    private String memberId;
    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String birthDate;
    private String physicalInfo;
    private String address;
    private String profilePicture;
    private LocalDate joinDate;
    private List<Attendance> attendances;

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

    public boolean init() {
        return true;
    }

    public void signUp() {}

    public boolean login(String loginId, String password) {
        return false;
    }

    public boolean checkDuplicateId(String loginId) {
        return false;
    }

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
