package org.bloom.authenticationserver.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {
    private final int code;

    public AuthException(int code, String message) {
        super(message);
        this.code = code;
    }
}
