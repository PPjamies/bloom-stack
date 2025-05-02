package org.bloom.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bloom.authservice.dto.requests.RefreshTokenRequest;
import org.bloom.authservice.dto.requests.TokenRequest;
import org.bloom.authservice.dto.responses.RefreshTokenResponse;
import org.bloom.authservice.dto.responses.TokenResponse;
import org.bloom.authservice.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final JwtService jwtService;

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> generateTokens(@Valid @RequestBody TokenRequest request) {
        return ResponseEntity.ok(TokenResponse.builder()
                .accessToken(jwtService.generateAccessToken(request.getUsername()))
                .refreshToken(jwtService.generateRefreshToken(request.getUsername()))
                .build());
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshTokens(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(RefreshTokenResponse.builder()
                .accessToken(jwtService.refreshToken(request.getRefreshToken()))
                .build());
    }
}
