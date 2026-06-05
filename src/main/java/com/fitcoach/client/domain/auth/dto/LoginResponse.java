package com.fitcoach.client.domain.auth.dto;

public record LoginResponse(String token, Long memberId, String name, String nickname) {
}
