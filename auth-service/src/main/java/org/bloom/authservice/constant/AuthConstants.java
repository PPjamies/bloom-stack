package org.bloom.authservice.constant;

public class AuthConstants {
    public static final long ACCESS_TOKEN_EXP_MILLIS = 1000L * 60 * 15;
    public static final long REFRESH_TOKEN_EXP_MILLIS = 1000L * 60 * 60 * 24 * 7;

    public static final String REFRESH_TOKEN_REQUIRED = "Refresh token required";
    public static final String USERNAME_REQUIRED = "Username required";
    public static final String PASSWORD_REQUIRED = "Password required";
}