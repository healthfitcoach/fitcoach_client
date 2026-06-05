package com.fitcoach.client.domain.info;

import com.fitcoach.client.global.response.ApiResponse;
import com.fitcoach.client.model.equipment.Apparatus;
import com.fitcoach.client.model.equipment.ExerciseMethod;
import com.fitcoach.client.model.notice.Notice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Info", description = "기구 검색 / 운동방법 / 공지사항 API (비로그인 허용)")
public interface InfoControllerDocs {

  @Operation(summary = "기구 목록 조회 / 검색", description = "keyword 없으면 전체 조회, 있으면 이름·카테고리 검색")
  ResponseEntity<ApiResponse<List<Apparatus>>> getApparatus(@RequestParam(required = false) String keyword);

  @Operation(summary = "기구 상세 조회")
  ResponseEntity<ApiResponse<Apparatus>> getApparatusById(@PathVariable Long apparatusId);

  @Operation(summary = "기구별 운동방법 조회")
  ResponseEntity<ApiResponse<List<ExerciseMethod>>> getExerciseMethods(@PathVariable String equipmentId);

  @Operation(summary = "공지사항 목록 조회")
  ResponseEntity<ApiResponse<List<Notice>>> getNotices();

  @Operation(summary = "공지사항 상세 조회")
  ResponseEntity<ApiResponse<Notice>> getNotice(@PathVariable Long noticeId);
}
