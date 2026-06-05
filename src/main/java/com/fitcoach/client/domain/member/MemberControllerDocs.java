package com.fitcoach.client.domain.member;

import com.fitcoach.client.domain.member.dto.MemberInfoResponse;
import com.fitcoach.client.domain.member.dto.MemberUpdateRequest;
import com.fitcoach.client.global.response.ApiResponse;
import com.fitcoach.client.model.member.Attendance;
import com.fitcoach.client.model.order.Order;
import com.fitcoach.client.model.point.Point;
import com.fitcoach.client.model.point.PointHistory;
import com.fitcoach.client.model.product.Membership;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Member", description = "회원 정보 관리 API")
public interface MemberControllerDocs {

  @Operation(summary = "내 정보 조회")
  ResponseEntity<ApiResponse<MemberInfoResponse>> getMyInfo(@AuthenticationPrincipal Long memberId);

  @Operation(summary = "내 정보 수정")
  ResponseEntity<ApiResponse<Void>> updateMyInfo(
      @AuthenticationPrincipal Long memberId, @RequestBody MemberUpdateRequest request);

  @Operation(summary = "활성 회원권 조회")
  ResponseEntity<ApiResponse<Membership>> getActiveMembership(@AuthenticationPrincipal Long memberId);

  @Operation(summary = "회원권 목록 조회")
  ResponseEntity<ApiResponse<List<Membership>>> getMemberships(@AuthenticationPrincipal Long memberId);

  @Operation(summary = "출석 기록 조회")
  ResponseEntity<ApiResponse<List<Attendance>>> getAttendances(@AuthenticationPrincipal Long memberId);

  @Operation(summary = "주문 목록 조회")
  ResponseEntity<ApiResponse<List<Order>>> getOrders(@AuthenticationPrincipal Long memberId);

  @Operation(summary = "포인트 조회")
  ResponseEntity<ApiResponse<Point>> getPoint(@AuthenticationPrincipal Long memberId);

  @Operation(summary = "포인트 내역 조회")
  ResponseEntity<ApiResponse<List<PointHistory>>> getPointHistories(@AuthenticationPrincipal Long memberId);

  @Operation(summary = "PT 잔여 횟수 합계")
  ResponseEntity<ApiResponse<Integer>> getActivePtRemainingCount(@AuthenticationPrincipal Long memberId);
}
