package org.bloom.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bloom.authservice.constant.AuthConstants;
import org.bloom.authservice.dto.User;
import org.bloom.authservice.dto.requests.RefreshTokenRequest;
import org.bloom.authservice.dto.requests.TokenRequest;
import org.bloom.authservice.dto.responses.RefreshTokenResponse;
import org.bloom.authservice.dto.responses.TokenResponse;
import org.bloom.authservice.service.JWTService;
import org.bloom.authservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final JWTService jwtService;
    private final UserService userService;

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> generateTokens(@Valid @RequestBody TokenRequest request) {
        return ResponseEntity.ok(TokenResponse.builder()
                .accessToken(jwtService.generateToken(request.getUsername(), AuthConstants.ACCESS_TOKEN_EXP_MILLIS))
                .refreshToken(jwtService.generateToken(request.getUsername(), AuthConstants.REFRESH_TOKEN_EXP_MILLIS))
                .build());
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshTokens(@Valid @RequestBody RefreshTokenRequest request) {
        User user = userService.getUserFromToken(request.getRefreshToken());
        return ResponseEntity.ok(RefreshTokenResponse.builder()
                .accessToken(jwtService.generateToken(user.getUsername(), AuthConstants.ACCESS_TOKEN_EXP_MILLIS))
                .build());
    }
}
