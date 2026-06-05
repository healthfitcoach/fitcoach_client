package com.fitcoach.client.domain.pt;

import com.fitcoach.client.domain.pt.dto.PTScheduleRequest;
import com.fitcoach.client.global.response.ApiResponse;
import com.fitcoach.client.model.product.PTSubscription;
import com.fitcoach.client.model.schedule.PTSchedule;
import com.fitcoach.client.model.schedule.Trainer;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pt")
public class PTRestController implements PTControllerDocs {

  private final PTService ptService;

  public PTRestController(PTService ptService) {
    this.ptService = ptService;
  }

  @GetMapping("/my")
  public ResponseEntity<ApiResponse<List<PTSubscription>>> getMyActivePTs(
      @AuthenticationPrincipal Long memberId) {
    return ResponseEntity.ok(ApiResponse.ok(ptService.getActivePTsByMember(memberId)));
  }

  @GetMapping("/schedules/availability")
  public ResponseEntity<ApiResponse<Boolean>> checkSlot(
      @RequestParam Long trainerId,
      @RequestParam LocalDate date,
      @RequestParam String time) {
    return ResponseEntity.ok(ApiResponse.ok(ptService.isSlotBooked(trainerId, date, time)));
  }

  @PostMapping("/schedules")
  public ResponseEntity<ApiResponse<PTSchedule>> reserveSchedule(
      @AuthenticationPrincipal Long memberId,
      @Valid @RequestBody PTScheduleRequest request) {
    PTSchedule schedule = ptService.reserveSchedule(
        request.getPtId(), memberId, request.getTrainerId(), request.getDate(), request.getTime());
    return ResponseEntity.ok(ApiResponse.ok("PT 일정이 예약되었습니다.", schedule));
  }

  @GetMapping("/trainers")
  public ResponseEntity<ApiResponse<List<Trainer>>> getTrainers(
      @RequestParam(required = false) String keyword) {
    List<Trainer> result = (keyword != null && !keyword.isBlank())
        ? ptService.filterTrainers(keyword)
        : ptService.getAllTrainers();
    return ResponseEntity.ok(ApiResponse.ok(result));
  }
}
