package org.bloom.authservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.bloom.authservice.constant.AuthError;
import org.bloom.authservice.exception.AuthException;
import org.bloom.authservice.jwt.JWTAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final JWTAuthority jwtAuthority;

    public String generateToken(String username, long exprMillis) {
        return jwtAuthority.generateToken(username, exprMillis);
    }

    public String extractSubject(String token) {
        Claims claims;
        try {
            claims = jwtAuthority.validateAndDecodeToken(token);
        } catch (ExpiredJwtException e) {
            throw new AuthException(AuthError.EXPIRED_TOKEN, "Token expired: " + token);
        } catch (JwtException e) {
            throw new AuthException(AuthError.INVALID_TOKEN, "Invalid token: " + token);
        }

        if (StringUtils.isEmpty(claims.getSubject())) {
            throw new AuthException(AuthError.INVALID_TOKEN, "Token is missing subject");
        }

        return claims.getSubject();
    }
}
