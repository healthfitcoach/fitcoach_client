package com.fitcoach.client.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

  // Auth
  DUPLICATE_LOGIN_ID(HttpStatus.BAD_REQUEST, "이미 사용 중인 아이디입니다."),
  INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),

  // Member
  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
  DUPLICATE_PHONE(HttpStatus.BAD_REQUEST, "이미 사용 중인 전화번호입니다."),

  // Membership
  NO_ACTIVE_MEMBERSHIP(HttpStatus.BAD_REQUEST, "활성 회원권이 없습니다."),

  // Attendance
  ALREADY_CHECKED_IN(HttpStatus.BAD_REQUEST, "오늘 이미 출석했습니다."),

  // Points
  ALREADY_EARNED_POINTS(HttpStatus.BAD_REQUEST, "오늘 이미 포인트를 적립했습니다."),
  INSUFFICIENT_POINTS(HttpStatus.BAD_REQUEST, "포인트가 부족합니다."),

  // PT Schedule
  SLOT_ALREADY_BOOKED(HttpStatus.BAD_REQUEST, "이미 예약된 시간입니다."),
  PT_NOT_FOUND(HttpStatus.NOT_FOUND, "PT 이용권을 찾을 수 없습니다."),

  // Product
  PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),

  // Notice
  NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "공지사항을 찾을 수 없습니다."),

  // Apparatus
  APPARATUS_NOT_FOUND(HttpStatus.NOT_FOUND, "기구를 찾을 수 없습니다."),

  // Common
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

  private final HttpStatus status;
  private final String message;

  ErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }

  public HttpStatus getStatus() { return status; }
  public String getMessage() { return message; }
}
