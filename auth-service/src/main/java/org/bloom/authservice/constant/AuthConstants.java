package org.bloom.authservice.constant;

public class AuthConstants {
    public static final long ACCESS_TOKEN_EXP_MILLIS = 1000L * 60 * 15; // 15 minutes
    public static final long REFRESH_TOKEN_EXP_MILLIS = 1000L * 60 * 60 * 24 * 7; // 7 days
    public static final long CLIENT_SECRET_EXP_MILLIS = 1000L * 60 * 60 * 24 * 30 * 3; // ~3 months

    public static final String USERNAME_REQUIRED = "Username required";
    public static final String PASSWORD_REQUIRED = "Password required";
    public static final String REFRESH_TOKEN_REQUIRED = "Refresh token required";
    public static final String CLIENT_NAME_REQUIRED = "Client name required";
    public static final String CLIENT_ID_REQUIRED = "Client id required";
    public static final String CLIENT_SECRET_REQUIRED = "Client secret required";
}