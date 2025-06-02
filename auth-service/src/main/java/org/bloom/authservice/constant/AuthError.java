package org.bloom.authservice.constant;

public class AuthError {
    public static final int JWT_TOKEN_EXPIRED = 110;
    public static final int JWT_TOKEN_INVALID = 111;
    public static final int JWT_TOKEN_SIGNATURE_INVALID = 112;
    public static final int JWT_TOKEN_SUBJECT_MISSING = 113;

    public static final int APP_USER_NOT_FOUND = 120;
    public static final int APP_USER_ALREADY_EXISTS = 121;
}