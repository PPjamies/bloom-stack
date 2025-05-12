package org.bloom.authservice.controller;

import com.google.gson.Gson;
import org.bloom.authservice.constant.AuthConstants;
import org.bloom.authservice.dto.User;
import org.bloom.authservice.dto.requests.LoginRequest;
import org.bloom.authservice.dto.requests.SignupRequest;
import org.bloom.authservice.dto.responses.LoginResponse;
import org.bloom.authservice.dto.responses.SignupResponse;
import org.bloom.authservice.service.JwtService;
import org.bloom.authservice.service.UserService;
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
public class AuthControllerTest {

    private final Gson gson = new Gson();

    private MockMvc mockMvc;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void loginTest() throws Exception {
        String username = "username";
        String password = "password";
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        LoginResponse response = LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        when(userService.getUserAndValidatePassword(username, password)).thenReturn(User.builder()
                .username(username)
                .password(password)
                .build());
        when(jwtService.generateToken(username, AuthConstants.ACCESS_TOKEN_EXP_MILLIS)).thenReturn(accessToken);
        when(jwtService.generateToken(username, AuthConstants.REFRESH_TOKEN_EXP_MILLIS)).thenReturn(refreshToken);

        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(LoginRequest.builder()
                                .username(username)
                                .password(password)
                                .build())))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assert (gson.toJson(response).equals(responseBody));
    }

    @Test
    void signupTest() throws Exception {
        String username = "username";
        String password = "password";

        SignupResponse response = SignupResponse.builder()
                .username(username)
                .build();

        when(userService.createUser(username, password)).thenReturn(User.builder()
                .username(username)
                .password(password)
                .build());

        MvcResult result = mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(SignupRequest.builder()
                                .username(username)
                                .password(password)
                                .build())))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assert (gson.toJson(response).equals(responseBody));
    }
}