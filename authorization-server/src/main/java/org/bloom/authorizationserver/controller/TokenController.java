package org.bloom.authorizationserver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bloom.authorizationserver.dto.requests.TokenRequest;
import org.bloom.authorizationserver.dto.responses.TokenResponse;
import org.bloom.authorizationserver.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private JwtService jwtService;

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> generateTokens(@Valid @RequestBody TokenRequest request) {
        return ResponseEntity.ok(TokenResponse.builder()
                .accessToken(jwtService.generateAccessToken(request.getUsername()))
                .refreshToken(jwtService.generateRefreshToken(request.getUsername()))
                .build());
    }
}
