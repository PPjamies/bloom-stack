package org.bloom.authservice.service;

import ch.qos.logback.core.util.StringUtil;
import io.jsonwebtoken.Claims;
import org.bloom.authservice.constant.AuthConstants;
import org.bloom.authservice.constant.AuthError;
import org.bloom.authservice.dto.User;
import org.bloom.authservice.exception.AuthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.ECGenParameterSpec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private KeyPair keyPair;

    @Mock
    private UserService userService;

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setup() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1")); // P-256
        keyPair = keyPairGenerator.generateKeyPair();

        jwtService = new JwtService(keyPair, userService);
    }

    @Test
    void generateTokenTest() {
        String subject = "token";

        String token = jwtService.generateToken(subject, 5000L);
        assert (StringUtil.notNullNorEmpty(token));
        assert (token.split("\\.").length == 3);
    }

    @Test
    void validateAndDecodeTokenTest() {
        String subject = "token";

        String accessToken = jwtService.generateToken(subject, AuthConstants.ACCESS_TOKEN_EXP_MILLIS);

        Claims tokenClaims = jwtService.validateAndDecodeToken(accessToken);
        assert (!tokenClaims.isEmpty());

        String tokenSubject = tokenClaims.getSubject();
        assert (subject.equals(tokenSubject));

        System.out.println("Token: " + accessToken);
        System.out.println("Subject: " + tokenSubject);
        System.out.println("Issued At: " + tokenClaims.getIssuedAt());
        System.out.println("Expiration At: " + tokenClaims.getExpiration());
    }

    @Test
    void validateAndDecodeTokenTest_ExpiredToken() {
        String subject = "expiredToken";

        String shortAccessToken = jwtService.generateToken(subject, 1L);
        AuthException exception = assertThrows(AuthException.class, () -> {
            jwtService.validateAndDecodeToken(shortAccessToken);
        });

        assertEquals(AuthError.EXPIRED_TOKEN, exception.getErrorCode());
    }

    @Test
    void validateAndDecodeTokenTest_InvalidToken() {
        String subject = "invalidToken";

        String invalidAccessToken = "wrong." + jwtService.generateToken(subject, 5000L);
        AuthException exception = assertThrows(AuthException.class, () -> {
            jwtService.validateAndDecodeToken(invalidAccessToken);
        });

        assertEquals(AuthError.INVALID_TOKEN, exception.getErrorCode());
    }

    @Test
    void refreshTokenTest() {
        String username = "username";

        String accessToken = jwtService.generateToken(username, AuthConstants.ACCESS_TOKEN_EXP_MILLIS);
        String refreshToken = jwtService.generateToken(username, AuthConstants.REFRESH_TOKEN_EXP_MILLIS);

        when(userService.isExistingUser(username)).thenReturn(true);
        when(userService.getUser(username)).thenReturn(User.builder()
                .username(username)
                .password("test-pw")
                .build());

        User user = jwtService.extractUser(accessToken);
        assert (username.equals(user.getUsername()));

        String newAccessToken = jwtService.refreshToken(refreshToken);
        assert (!accessToken.equals(newAccessToken));
    }
}
