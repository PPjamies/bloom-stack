package org.bloom.authservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.bloom.authservice.constant.AuthError;
import org.bloom.authservice.dto.AppUser;
import org.bloom.authservice.exception.AuthException;
import org.bloom.authservice.jwt.JWTAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final JWTAuthority jwtAuthority;

    public String generateToken(@NotNull AppUser user, long exprMillis) {
        List<String> roles = Optional.ofNullable(user.getAuthorities())
                .orElse(List.of())
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

        return jwtAuthority.generateToken(user.getUsername(), claims, exprMillis);
    }

    public String extractSubject(@NotNull String token) {
        Claims claims;
        try {
            claims = jwtAuthority.validateToken(token);
        } catch (ExpiredJwtException e) {
            throw new AuthException(AuthError.JWT_TOKEN_EXPIRED, "Token expired");
        } catch (SignatureException e) {
            throw new AuthException(AuthError.JWT_TOKEN_SIGNATURE_INVALID, "Invalid signature");
        } catch (JwtException e) {
            throw new AuthException(AuthError.JWT_TOKEN_INVALID, "Invalid token: " + e.getMessage());
        }

        if (StringUtils.isEmpty(claims.getSubject())) {
            throw new AuthException(AuthError.JWT_TOKEN_SUBJECT_MISSING, "Missing subject");
        }

        return claims.getSubject();
    }
}
