package org.bloom.authservice.repository.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DBClient.TABLE_NAME)
public class DBClient {
    public static final String TABLE_NAME = "clients";
    public static final String ID = "id";

    // spring oauth2 details
    public static final String REGISTERED_CLIENT_ID = "registeredClientId";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_NAME = "client_name";
    public static final String CLIENT_AUTHENTICATION_METHODS = "client_authentication_methods";
    public static final String AUTHORIZATION_GRANT_TYPES = "authorization_grant_types";
    public static final String SCOPES = "scopes";

    // client authentication
    public static final String CLIENT_SECRET = "client_secret";
    public static final String CLIENT_SECRET_EXPIRES_AT = "client_secret_expires_at";

    // third-party authentication
    public static final String REDIRECT_URIS = "redirect_uris";

    // spring oauth2 configs
    public static final String CLIENT_SETTINGS = "client_settings";
    public static final String TOKEN_SETTINGS = "token_settings";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private Long id;

    @Column(name = REGISTERED_CLIENT_ID, nullable = false, unique = true)
    private String registeredClientId;

    @Column(name = CLIENT_ID, nullable = false, unique = true)
    private String clientId;

    @Column(name = CLIENT_NAME, nullable = false)
    private String clientName;

    @Column(name = CLIENT_AUTHENTICATION_METHODS, columnDefinition = "TEXT", nullable = false)
    private String clientAuthenticationMethods;

    @Column(name = AUTHORIZATION_GRANT_TYPES, columnDefinition = "TEXT", nullable = false)
    private String authorizationGrantTypes;

    @Column(name = SCOPES, columnDefinition = "TEXT", nullable = false)
    private String scopes;

    @Column(name = CLIENT_SECRET)
    private String clientSecret;

    @Column(name = CLIENT_SECRET_EXPIRES_AT)
    private Instant clientSecretExpiresAt;

    @Column(name = REDIRECT_URIS, columnDefinition = "TEXT")
    private String redirectUris;

    @Column(name = CLIENT_SETTINGS, columnDefinition = "TEXT")
    private String clientSettings;

    @Column(name = TOKEN_SETTINGS, columnDefinition = "TEXT")
    private String tokenSettings;
}
