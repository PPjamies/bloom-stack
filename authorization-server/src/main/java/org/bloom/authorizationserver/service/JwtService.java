package org.bloom.authorizationserver.service;

import io.jsonwebtoken.*;
import org.bloom.authorizationserver.exception.TokenValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7;
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000L * 60 * 15;

    @Autowired
    private KeyPair keyPair;

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

    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(keyPair.getPublic())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenValidationException("Token expired");
        } catch (JwtException e) {
            throw new TokenValidationException("Invalid token");
        }
    }
}
