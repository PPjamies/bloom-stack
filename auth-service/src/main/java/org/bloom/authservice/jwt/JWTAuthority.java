package org.bloom.authservice.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JWTAuthority {

    private final KeyPair keyPair;

    public String generateToken(String subject, long exprMillis) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(exprMillis)))
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.ES256)
                .compact();
    }

    public Claims validateAndDecodeToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(keyPair.getPublic())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
