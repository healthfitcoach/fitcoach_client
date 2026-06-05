package com.fitcoach.client.domain.auth;

import com.fitcoach.client.domain.auth.dto.LoginRequest;
import com.fitcoach.client.domain.auth.dto.LoginResponse;
import com.fitcoach.client.domain.auth.dto.SignupRequest;
import com.fitcoach.client.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Auth", description = "회원가입 / 로그인 API")
public interface AuthControllerDocs {

  @Operation(summary = "회원가입", description = "새 회원을 등록합니다.")
  @ApiResponses({
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "가입 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "아이디 중복 또는 입력값 오류")
  })
  ResponseEntity<ApiResponse<Long>> signup(@Valid @RequestBody SignupRequest request);

  @Operation(summary = "로그인", description = "로그인 성공 시 JWT 토큰을 반환합니다.")
  @ApiResponses({
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "아이디 또는 비밀번호 오류")
  })
  ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request);

  @Operation(summary = "아이디 중복 확인", description = "true 면 이미 사용 중인 아이디입니다.")
  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "중복 여부 반환")
  ResponseEntity<ApiResponse<Boolean>> checkLoginId(@RequestParam String loginId);
}
