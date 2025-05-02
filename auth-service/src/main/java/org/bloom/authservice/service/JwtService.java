package org.bloom.authservice.service;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.bloom.authservice.constant.AuthConstants;
import org.bloom.authservice.constant.AuthError;
import org.bloom.authservice.dto.User;
import org.bloom.authservice.exception.AuthException;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final KeyPair keyPair;
    private final UserService userService;

    private String generateToken(String subject, long exprMillis) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(exprMillis)))
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.ES256)
                .compact();
    }

    public String generateAccessToken(String subject) {
        return generateToken(subject, AuthConstants.ACCESS_TOKEN_EXP_MILLIS);
    }

    public String generateRefreshToken(String subject) {
        return generateToken(subject, AuthConstants.REFRESH_TOKEN_EXP_MILLIS);
    }

    public Claims validateAndDecodeToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(keyPair.getPublic())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new AuthException(AuthError.EXPIRED_TOKEN, "Token expired: " + token);
        } catch (JwtException e) {
            throw new AuthException(AuthError.INVALID_TOKEN, "Invalid token: " + token);
        }
    }

    public User extractUser(String token) {
        String username = validateAndDecodeToken(token).getSubject();
        if (!userService.isExistingUser(username)) {
            throw new AuthException(AuthError.USER_NOT_FOUND, "User not found: " + username);
        }
        return userService.getUser(username);
    }

    public String refreshToken(String refreshToken) {
        User user = extractUser(refreshToken);
        return generateAccessToken(user.getUsername());
    }
}
