package org.bloom.authorizationserver.service;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.bloom.authorizationserver.constant.AuthorizationConstants;
import org.bloom.authorizationserver.exception.AuthorizationException;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7;
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000L * 60 * 15;

    private final KeyPair keyPair;

    public String generateAccessToken(String subject) {
        return generateToken(subject, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String generateRefreshToken(String subject) {
        return generateToken(subject, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    private String generateToken(String subject, long exprMillis) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(exprMillis)))
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.ES256)
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            validateToken(token);
            return true;
        } catch (Exception e) {
            return false; //todo: ew
        }
    }

    private Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(keyPair.getPublic())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new AuthorizationException(AuthorizationConstants.TOKEN_EXPIRED, "Token expired: " + token);
        } catch (JwtException e) {
            throw new AuthorizationException(AuthorizationConstants.TOKEN_INVALID, "Invalid token: " + token);
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(keyPair.getPublic())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
