package com.fitcoach.client.domain.activity;

import com.fitcoach.client.domain.activity.dto.ExerciseRecordRequest;
import com.fitcoach.client.global.response.ApiResponse;
import com.fitcoach.client.model.member.Attendance;
import com.fitcoach.client.model.member.ExerciseRecord;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Activity", description = "출석 / 운동 기록 / 포인트 적립 API")
public interface ActivityControllerDocs {

  @Operation(summary = "출석 체크인")
  ResponseEntity<ApiResponse<Attendance>> checkIn(@AuthenticationPrincipal Long memberId);

  @Operation(summary = "운동 기록 등록")
  ResponseEntity<ApiResponse<ExerciseRecord>> recordExercise(
      @AuthenticationPrincipal Long memberId, @RequestBody ExerciseRecordRequest request);

  @Operation(summary = "포인트 적립", description = "오늘 운동 기록 기반으로 포인트를 적립합니다.")
  ResponseEntity<ApiResponse<Integer>> earnPoints(
      @AuthenticationPrincipal Long memberId, @RequestParam int exerciseTime);
}
