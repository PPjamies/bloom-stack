package org.bloom.authservice.converter;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.NotNull;
import org.bloom.authservice.dto.AppUser;
import org.bloom.authservice.repository.jpa.DBAppUser;
import org.bloom.authservice.repository.jpa.DBClient;
import org.bloom.authservice.serializer.Oauth2ClientSerializer;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;

@Validated
public class AuthConverter {

    public static AppUser convert(@NotNull DBAppUser from) {
        return AppUser.builder()
                .username(from.getUsername())
                .password(from.getPassword())
                .build();
    }

    public static DBAppUser convert(@NotNull AppUser from) {
        return DBAppUser.builder()
                .username(from.getUsername())
                .password(from.getPassword())
                .build();
    }

    public static DBClient convert(@NotNull Oauth2ClientSerializer serializer, @NotNull RegisteredClient from) {
        DBClient to = new DBClient();
        to.setRegisteredClientId(from.getId());
        to.setName(from.getClientName());
        to.setClientId(from.getClientId());

        to.setClientAuthenticationMethods(serializer.serializeClientAuthenticationMethods(from.getClientAuthenticationMethods()));
        to.setAuthorizationGrantTypes(serializer.serializeAuthorizationGrantTypes(from.getAuthorizationGrantTypes()));
        to.setScopes(serializer.serializeScopes(from.getScopes()));

        if (StringUtils.isNotBlank(from.getClientSecret())) {
            to.setClientSecret(from.getClientSecret());
        }

        if (from.getClientSecretExpiresAt() != null) {
            to.setClientSecretExpiresAt(from.getClientSecretExpiresAt());
        }

        if (!ObjectUtils.isEmpty(from.getRedirectUris())) {
            to.setRedirectUris(serializer.serializeRedirectUris(from.getRedirectUris()));
        }

        if (from.getClientSettings() != null) {
            to.setClientSettings(serializer.serializeClientSettings(from.getClientSettings()));
        }

        if (from.getTokenSettings() != null) {
            to.setTokenSettings(serializer.serializeTokenSettings(from.getTokenSettings()));
        }

        return to;
    }

//    public static RegisteredClient convertToRegisterClient(DBClient from) {
//        Set<ClientAuthenticationMethod> authMethods = parseClientAuthenticationMethods(dbClient.getClientAuthenticationMethods());
//        Set<AuthorizationGrantType> grantTypes = parseAuthorizationGrantTypes(dbClient.getAuthorizationGrantTypes());
//        Set<String> scopes = parseScopes(dbClient.getScopes());
//
//        return RegisteredClient.withId(from.getRe)
//                .clientId(dbClient.getClientId())
//                .clientSecret(dbClient.getClientSecret())
//                .clientAuthenticationMethods(authMethods)
//                .authorizationGrantTypes(grantTypes)
//                .scopes(scopes)
//                .clientSecretExpiresAt(dbClient.getClientSecretExpiresAt())
//                .redirectUris(parseRedirectUris(dbClient.getRedirectUris()))
//                .clientSettings(ClientSettings.parse(dbClient.getClientSettings()))
//                .tokenSettings(TokenSettings.parse(dbClient.getTokenSettings()))
//                .build();
//    }
}
