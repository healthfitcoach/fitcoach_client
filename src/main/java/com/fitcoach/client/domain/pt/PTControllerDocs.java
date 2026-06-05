package com.fitcoach.client.domain.pt;

import com.fitcoach.client.domain.pt.dto.PTScheduleRequest;
import com.fitcoach.client.global.response.ApiResponse;
import com.fitcoach.client.model.product.PTSubscription;
import com.fitcoach.client.model.schedule.PTSchedule;
import com.fitcoach.client.model.schedule.Trainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "PT", description = "PT 이용권 조회 및 일정 예약 API")
public interface PTControllerDocs {

  @Operation(summary = "내 활성 PT 이용권 목록")
  ResponseEntity<ApiResponse<List<PTSubscription>>> getMyActivePTs(@AuthenticationPrincipal Long memberId);

  @Operation(summary = "슬롯 예약 가능 여부 확인")
  ResponseEntity<ApiResponse<Boolean>> checkSlot(
      @RequestParam Long trainerId,
      @RequestParam LocalDate date,
      @RequestParam String time);

  @Operation(summary = "PT 일정 예약")
  ResponseEntity<ApiResponse<PTSchedule>> reserveSchedule(
      @AuthenticationPrincipal Long memberId, @RequestBody PTScheduleRequest request);

  @Operation(summary = "트레이너 목록 / 전문분야 검색")
  ResponseEntity<ApiResponse<List<Trainer>>> getTrainers(@RequestParam(required = false) String keyword);
}
