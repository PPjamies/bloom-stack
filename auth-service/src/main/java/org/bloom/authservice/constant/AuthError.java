package org.bloom.authservice.constant;

public class AuthError {
    public static final int INVALID_AUTHORIZATION_HEADER = 100;
    public static final int UNSUPPORTED_AUTHORIZATION_TOKEN = 101;
    public static final int MISSING_AUTHORIZATION_TOKEN = 102;
    public static final int AUTHENTICATION_FAILED = 103;
    public static final int INVALID_TOKEN = 104;
    public static final int EXPIRED_TOKEN = 105;
    public static final int USER_NOT_FOUND = 106;
    public static final int USER_ALREADY_EXISTS = 107;
    public static final int PASSWORD_MISMATCH = 108;
}