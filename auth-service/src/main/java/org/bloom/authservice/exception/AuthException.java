package org.bloom.authservice.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {
    private final int errorCode;

    public AuthException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
