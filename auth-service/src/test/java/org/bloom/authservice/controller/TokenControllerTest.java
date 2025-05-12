package org.bloom.authservice.controller;

import com.google.gson.Gson;
import org.bloom.authservice.constant.AuthConstants;
import org.bloom.authservice.dto.requests.RefreshTokenRequest;
import org.bloom.authservice.dto.requests.TokenRequest;
import org.bloom.authservice.dto.responses.RefreshTokenResponse;
import org.bloom.authservice.dto.responses.TokenResponse;
import org.bloom.authservice.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TokenControllerTest {

    private final Gson gson = new Gson();

    private MockMvc mockMvc;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private TokenController tokenController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(tokenController).build();
    }

    @Test
    void generateTokens_success() throws Exception {
        String username = "username";
        String accessToken = "access-token";
        String refreshToken = "refresh-token";

        TokenResponse response = TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        when(jwtService.generateToken(username, AuthConstants.ACCESS_TOKEN_EXP_MILLIS)).thenReturn(accessToken);
        when(jwtService.generateToken(username, AuthConstants.REFRESH_TOKEN_EXP_MILLIS)).thenReturn(refreshToken);

        MvcResult result = mockMvc.perform(post("/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(TokenRequest.builder()
                                .username(username)
                                .build())))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        TokenResponse actualResponse = gson.fromJson(responseBody, TokenResponse.class);
        assert (response.getAccessToken().equals(actualResponse.getAccessToken()));
        assert (response.getRefreshToken().equals(actualResponse.getRefreshToken()));
    }

    @Test
    void refreshTokens_success() throws Exception {
        String refreshToken = "refresh-token";
        String newAccessToken = "new-access-token";

        RefreshTokenResponse response = RefreshTokenResponse.builder()
                .accessToken(newAccessToken)
                .build();

        when(jwtService.refreshToken(refreshToken)).thenReturn(newAccessToken);

        MvcResult result = mockMvc.perform(post("/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(RefreshTokenRequest.builder()
                                .refreshToken(refreshToken)
                                .build())))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        RefreshTokenResponse actualResponse = gson.fromJson(responseBody, RefreshTokenResponse.class);
        assert (response.getAccessToken().equals(actualResponse.getAccessToken()));
    }
}