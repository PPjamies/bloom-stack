package org.bloom.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bloom.authservice.constant.AuthConstants;
import org.bloom.authservice.dto.AppUser;
import org.bloom.authservice.dto.requests.LoginRequest;
import org.bloom.authservice.dto.requests.SignupRequest;
import org.bloom.authservice.dto.responses.LoginResponse;
import org.bloom.authservice.dto.responses.SignupResponse;
import org.bloom.authservice.service.AppUserService;
import org.bloom.authservice.service.JWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final AppUserService appUserService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        // authenticate the user using spring security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // extract user details
        AppUser user = (AppUser) authentication.getPrincipal();

        return ResponseEntity.ok(LoginResponse.builder()
                .accessToken(jwtService.generateToken(user, AuthConstants.ACCESS_TOKEN_EXP_MILLIS))
                .refreshToken(jwtService.generateToken(user, AuthConstants.REFRESH_TOKEN_EXP_MILLIS))
                .build());
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        AppUser appUser = appUserService.createAppUser(request.getUsername(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(SignupResponse.builder()
                .username(appUser.getUsername())
                .build());
    }
}
