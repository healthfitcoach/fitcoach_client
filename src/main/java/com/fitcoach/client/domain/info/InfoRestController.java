package com.fitcoach.client.domain.info;

import com.fitcoach.client.global.response.ApiResponse;
import com.fitcoach.client.model.equipment.Apparatus;
import com.fitcoach.client.model.equipment.ExerciseMethod;
import com.fitcoach.client.model.notice.Notice;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/info")
public class InfoRestController implements InfoControllerDocs {

  private final InfoService infoService;

  public InfoRestController(InfoService infoService) {
    this.infoService = infoService;
  }

  @GetMapping("/apparatus")
  public ResponseEntity<ApiResponse<List<Apparatus>>> getApparatus(
      @RequestParam(required = false) String keyword) {
    List<Apparatus> result = (keyword != null && !keyword.isBlank())
        ? infoService.searchApparatus(keyword)
        : infoService.getAllApparatus();
    return ResponseEntity.ok(ApiResponse.ok(result));
  }

  @GetMapping("/apparatus/{apparatusId}")
  public ResponseEntity<ApiResponse<Apparatus>> getApparatusById(@PathVariable Long apparatusId) {
    return ResponseEntity.ok(ApiResponse.ok(infoService.getApparatusById(apparatusId)));
  }

  @GetMapping("/apparatus/{equipmentId}/methods")
  public ResponseEntity<ApiResponse<List<ExerciseMethod>>> getExerciseMethods(
      @PathVariable String equipmentId) {
    return ResponseEntity.ok(ApiResponse.ok(infoService.getExerciseMethodsByEquipmentId(equipmentId)));
  }

  @GetMapping("/notices")
  public ResponseEntity<ApiResponse<List<Notice>>> getNotices() {
    return ResponseEntity.ok(ApiResponse.ok(infoService.getAllNotices()));
  }

  @GetMapping("/notices/{noticeId}")
  public ResponseEntity<ApiResponse<Notice>> getNotice(@PathVariable Long noticeId) {
    return ResponseEntity.ok(ApiResponse.ok(infoService.getNoticeById(noticeId)));
  }
}
