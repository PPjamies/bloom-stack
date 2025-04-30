package org.bloom.authorizationserver.exception;

import lombok.Getter;

@Getter
public class AuthorizationException extends RuntimeException {
    private final int code;

    public AuthorizationException(int code, String message) {
        super(message);
        this.code = code;
    }
}
