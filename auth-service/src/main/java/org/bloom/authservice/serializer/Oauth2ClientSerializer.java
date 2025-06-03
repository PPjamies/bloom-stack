package org.bloom.authservice.serializer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.validation.annotation.Validated;

import java.util.Set;
import java.util.stream.Collectors;

@Validated
public class Oauth2ClientSerializer {

    public String serializeClientAuthenticationMethods(@NotEmpty Set<ClientAuthenticationMethod> obj) {
        Set<String> values = obj.stream()
                .map(ClientAuthenticationMethod::getValue)
                .collect(Collectors.toSet());
        return JsonUtils.toJson(values);
    }

    public String serializeAuthorizationGrantTypes(@NotEmpty Set<AuthorizationGrantType> obj) {
        Set<String> values = obj.stream()
                .map(AuthorizationGrantType::getValue)
                .collect(Collectors.toSet());
        return JsonUtils.toJson(values);
    }

    public String serializeScopes(@NotEmpty Set<String> scopes) {
        return JsonUtils.toJson(scopes);
    }

    public String serializeRedirectUris(@NotEmpty Set<String> obj) {
        return JsonUtils.toJson(obj);
    }

    public String serializeClientSettings(@NotNull ClientSettings obj) {
        return JsonUtils.toJson(obj);
    }

    public String serializeTokenSettings(@NotNull TokenSettings obj) {
        return JsonUtils.toJson(obj);
    }
}
