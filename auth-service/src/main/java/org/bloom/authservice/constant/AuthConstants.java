package org.bloom.authservice.constant;

public class AuthConstants {
    public static final long ACCESS_TOKEN_EXP_MILLIS = 1000L * 60 * 15;
    public static final long REFRESH_TOKEN_EXP_MILLIS = 1000L * 60 * 60 * 24 * 7;

    public static final String REQUIRED_ACCESS_TOKEN = "Access token required";
    public static final String REQUIRED_REFRESH_TOKEN = "Refresh token required";
    public static final String REQUIRED_USERNAME = "Username required";
    public static final String REQUIRED_PASSWORD = "Password required";
}