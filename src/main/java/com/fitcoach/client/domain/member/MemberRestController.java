package com.fitcoach.client.domain.member;

import com.fitcoach.client.domain.member.dto.MemberInfoResponse;
import com.fitcoach.client.domain.member.dto.MemberUpdateRequest;
import com.fitcoach.client.global.response.ApiResponse;
import com.fitcoach.client.model.member.Attendance;
import com.fitcoach.client.model.order.Order;
import com.fitcoach.client.model.point.Point;
import com.fitcoach.client.model.point.PointHistory;
import com.fitcoach.client.model.product.Membership;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberRestController implements MemberControllerDocs {

  private final MemberService memberService;

  public MemberRestController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("/me")
  public ResponseEntity<ApiResponse<MemberInfoResponse>> getMyInfo(
      @AuthenticationPrincipal Long memberId) {
    return ResponseEntity.ok(ApiResponse.ok(
        MemberInfoResponse.from(memberService.getMemberInfo(memberId))));
  }

  @PutMapping("/me")
  public ResponseEntity<ApiResponse<Void>> updateMyInfo(
      @AuthenticationPrincipal Long memberId,
      @RequestBody MemberUpdateRequest request) {
    memberService.updateMemberInfo(memberId, request.getNickname(), request.getPhone(),
        request.getBirthDate(), request.getBodyInfo(), request.getAddress(),
        request.getProfileImage(), request.getNewPassword());
    return ResponseEntity.ok(ApiResponse.ok("회원 정보가 수정되었습니다."));
  }

  @GetMapping("/me/membership/active")
  public ResponseEntity<ApiResponse<Membership>> getActiveMembership(
      @AuthenticationPrincipal Long memberId) {
    return ResponseEntity.ok(ApiResponse.ok(memberService.getActiveMembership(memberId)));
  }

  @GetMapping("/me/memberships")
  public ResponseEntity<ApiResponse<List<Membership>>> getMemberships(
      @AuthenticationPrincipal Long memberId) {
    return ResponseEntity.ok(ApiResponse.ok(memberService.getMemberships(memberId)));
  }

  @GetMapping("/me/attendance")
  public ResponseEntity<ApiResponse<List<Attendance>>> getAttendances(
      @AuthenticationPrincipal Long memberId) {
    return ResponseEntity.ok(ApiResponse.ok(memberService.getAttendances(memberId)));
  }

  @GetMapping("/me/orders")
  public ResponseEntity<ApiResponse<List<Order>>> getOrders(
      @AuthenticationPrincipal Long memberId) {
    return ResponseEntity.ok(ApiResponse.ok(memberService.getOrders(memberId)));
  }

  @GetMapping("/me/point")
  public ResponseEntity<ApiResponse<Point>> getPoint(
      @AuthenticationPrincipal Long memberId) {
    return ResponseEntity.ok(ApiResponse.ok(memberService.getPoint(memberId)));
  }

  @GetMapping("/me/point-history")
  public ResponseEntity<ApiResponse<List<PointHistory>>> getPointHistories(
      @AuthenticationPrincipal Long memberId) {
    return ResponseEntity.ok(ApiResponse.ok(memberService.getPointHistories(memberId)));
  }

  @GetMapping("/me/pt/remaining-count")
  public ResponseEntity<ApiResponse<Integer>> getActivePtRemainingCount(
      @AuthenticationPrincipal Long memberId) {
    return ResponseEntity.ok(ApiResponse.ok(memberService.getActivePtRemainingCount(memberId)));
  }
}
