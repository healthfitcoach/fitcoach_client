package com.fitcoach.client.domain.auth;

import com.fitcoach.client.domain.auth.dto.LoginRequest;
import com.fitcoach.client.domain.auth.dto.LoginResponse;
import com.fitcoach.client.domain.auth.dto.SignupRequest;
import com.fitcoach.client.global.response.ApiResponse;
import com.fitcoach.client.jwt.JwtTokenProvider;
import com.fitcoach.client.model.member.Member;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController implements AuthControllerDocs {

  private final AuthService authService;
  private final JwtTokenProvider jwtTokenProvider;

  public AuthRestController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
    this.authService = authService;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<Long>> signup(@Valid @RequestBody SignupRequest request) {
    Member member = authService.signup(
        request.getLoginId(), request.getPassword(), request.getName(),
        request.getNickname(), request.getPhone(), request.getBirthDate(),
        request.getBodyInfo(), request.getAddress(), request.getProfileImage());
    return ResponseEntity.ok(ApiResponse.ok("회원가입이 완료되었습니다.", member.getId()));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
    Member member = authService.login(request.getLoginId(), request.getPassword());
    String token = jwtTokenProvider.generateToken(member.getId());
    return ResponseEntity.ok(ApiResponse.ok(
        new LoginResponse(token, member.getId(), member.getName(), member.getNickname())));
  }

  @GetMapping("/check-id")
  public ResponseEntity<ApiResponse<Boolean>> checkLoginId(@RequestParam String loginId) {
    return ResponseEntity.ok(ApiResponse.ok(authService.isDuplicateLoginId(loginId)));
  }
}
