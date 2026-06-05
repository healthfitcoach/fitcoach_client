package com.fitcoach.client.domain.activity;

import com.fitcoach.client.domain.activity.dto.ExerciseRecordRequest;
import com.fitcoach.client.global.exception.CustomException;
import com.fitcoach.client.global.exception.ErrorCode;
import com.fitcoach.client.global.response.ApiResponse;
import com.fitcoach.client.model.member.Attendance;
import com.fitcoach.client.model.member.ExerciseRecord;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activity")
public class ActivityRestController implements ActivityControllerDocs {

  private final ActivityService activityService;

  public ActivityRestController(ActivityService activityService) {
    this.activityService = activityService;
  }

  @PostMapping("/attendance")
  public ResponseEntity<ApiResponse<Attendance>> checkIn(@AuthenticationPrincipal Long memberId) {
    if (activityService.hasCheckedInToday(memberId)) {
      throw new CustomException(ErrorCode.ALREADY_CHECKED_IN);
    }
    return ResponseEntity.ok(ApiResponse.ok("출석이 완료되었습니다.", activityService.checkIn(memberId)));
  }

  @PostMapping("/exercise-records")
  public ResponseEntity<ApiResponse<ExerciseRecord>> recordExercise(
      @AuthenticationPrincipal Long memberId,
      @Valid @RequestBody ExerciseRecordRequest request) {
    ExerciseRecord record = activityService.addExerciseRecord(memberId, request.getDate(),
        request.getExerciseType(), request.getExerciseTime(),
        request.getSets(), request.getReps(), request.getMemo(), request.getPhoto());
    return ResponseEntity.ok(ApiResponse.ok("운동 기록이 저장되었습니다.", record));
  }

  @PostMapping("/points/earn")
  public ResponseEntity<ApiResponse<Integer>> earnPoints(
      @AuthenticationPrincipal Long memberId,
      @RequestParam int exerciseTime) {
    if (activityService.hasEarnedPointsToday(memberId)) {
      throw new CustomException(ErrorCode.ALREADY_EARNED_POINTS);
    }
    int earned = activityService.earnPoints(memberId, exerciseTime);
    return ResponseEntity.ok(ApiResponse.ok(earned + " 포인트가 적립되었습니다.", earned));
  }
}
