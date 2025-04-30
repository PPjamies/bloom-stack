package org.bloom.authenticationserver.controller;

import lombok.RequiredArgsConstructor;
import org.bloom.authenticationserver.dto.User;
import org.bloom.authenticationserver.dto.requests.LoginRequest;
import org.bloom.authenticationserver.dto.requests.SignupRequest;
import org.bloom.authenticationserver.dto.responses.LoginResponse;
import org.bloom.authenticationserver.dto.responses.SignupResponse;
import org.bloom.authenticationserver.exception.AuthenticationException;
import org.bloom.authenticationserver.service.UserService;
import org.bloom.authorizationserver.dto.requests.TokenRequest;
import org.bloom.authorizationserver.dto.responses.TokenResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final PasswordEncoder passwordEncoder;

    private final RestTemplateBuilder restTemplateBuilder;

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.getUserByUsername(request.getUsername());

            if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new AuthenticationException("Invalid username or password");
            }

            // calls authorization server for access/refresh tokens
            TokenRequest tokenRequest = TokenRequest.builder()
                    .username(request.getUsername())
                    .build();

            ResponseEntity<TokenResponse> responseEntity = restTemplateBuilder.build().postForEntity(
                    "http://authorization-server:8081/token",
                    tokenRequest,
                    TokenResponse.class
            );

            if (!responseEntity.hasBody())
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); //todo: handle better

            TokenResponse tokenResponse = responseEntity.getBody();
            return ResponseEntity.ok(LoginResponse.builder()
                    .accessToken(tokenResponse.getAccessToken())
                    .refreshToken(tokenResponse.getRefreshToken())
                    .build());

        } catch (Exception e) { //todo: handle better
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(LoginResponse.builder()
                            .accessToken(null)
                            .refreshToken(null)
                            .build());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        if (request.getUsername() == null || request.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new SignupResponse());
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = userService.saveUser(request.getUsername(), hashedPassword);
        if (user == null) return null; // todo: actually handle null case

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SignupResponse());
    }
}
