package org.bloom.authenticationserver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bloom.authenticationserver.dto.User;
import org.bloom.authenticationserver.dto.requests.LoginRequest;
import org.bloom.authenticationserver.dto.requests.SignupRequest;
import org.bloom.authenticationserver.dto.responses.LoginResponse;
import org.bloom.authenticationserver.dto.responses.SignupResponse;
import org.bloom.authenticationserver.service.JwtService;
import org.bloom.authenticationserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = userService.getUserAndValidatePassword(request.getUsername(), request.getPassword());

        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        return ResponseEntity.ok(LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        User user = userService.createUser(request.getUsername(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(SignupResponse.builder()
                .username(user.getUsername())
                .build());
    }
}
