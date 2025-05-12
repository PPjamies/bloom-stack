package org.bloom.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bloom.authservice.constant.AuthConstants;
import org.bloom.authservice.dto.User;
import org.bloom.authservice.dto.requests.LoginRequest;
import org.bloom.authservice.dto.requests.SignupRequest;
import org.bloom.authservice.dto.responses.LoginResponse;
import org.bloom.authservice.dto.responses.SignupResponse;
import org.bloom.authservice.service.JwtService;
import org.bloom.authservice.service.UserService;
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

        String accessToken = jwtService.generateToken(user.getUsername(), AuthConstants.ACCESS_TOKEN_EXP_MILLIS);
        String refreshToken = jwtService.generateToken(user.getUsername(), AuthConstants.REFRESH_TOKEN_EXP_MILLIS);

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
