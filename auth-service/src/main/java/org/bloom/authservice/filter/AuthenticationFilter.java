package org.bloom.authservice.filter;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bloom.authservice.constant.AuthError;
import org.bloom.authservice.dto.User;
import org.bloom.authservice.exception.AuthException;
import org.bloom.authservice.service.JWTService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER_TOKEN = "Bearer ";

    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.isEmpty(authHeader)) {
            throw new AuthException(AuthError.INVALID_AUTHORIZATION_HEADER, "Authorization header is missing");
        }

        if (!authHeader.startsWith(BEARER_TOKEN)) {
            throw new AuthException(AuthError.UNSUPPORTED_AUTHORIZATION_TOKEN, "Unsupported token type"); // todo: return list of supported token types
        }

        String token = authHeader.substring(BEARER_TOKEN.length());

        if (StringUtils.isEmpty(token)) {
            throw new AuthException(AuthError.MISSING_AUTHORIZATION_TOKEN, "Token is missing");
        }

        User user;
        try {
            user = jwtService.extractUserFromToken(token);
        } catch (AuthException e) {
            throw new AuthException(AuthError.AUTHENTICATION_FAILED, "Authentication failed");
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user.getUsername(), null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
