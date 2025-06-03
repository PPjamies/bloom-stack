package org.bloom.authservice.serializer;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
public class Oauth2ClientDeserializer {

    public Set<ClientAuthenticationMethod> deserializeClientAuthenticationMethods(@NotBlank String json) {
        Set<String> values = JsonUtils.fromJson(json, new TypeReference<Set<String>>() {});
        return values.stream()
                .map(ClientAuthenticationMethod::new)
                .collect(Collectors.toSet());
    }

    public Set<AuthorizationGrantType> deserializeAuthorizationGrantTypes(@NotBlank String json) {
        Set<String> values = JsonUtils.fromJson(json, new TypeReference<Set<String>>() {});
        return values.stream()
                .map(AuthorizationGrantType::new)
                .collect(Collectors.toSet());
    }

    public Set<String> deserializeScopes(@NotBlank String json) {
        return JsonUtils.fromJson(json, new TypeReference<Set<String>>() {});
    }

    public Set<String> deserializeRedirectUris(@NotBlank String json) {
        return JsonUtils.fromJson(json, new TypeReference<Set<String>>() {});
    }

    public ClientSettings deserializeClientSettings(@NotBlank String json) {
        Map<String, Object> map = JsonUtils.fromJson(json, new TypeReference<Map<String, Object>>() {});
        return ClientSettings.withSettings(map).build();

    }

    public TokenSettings deserializeTokenSettings(@NotBlank String json) {
        Map<String, Object> map = JsonUtils.fromJson(json, new TypeReference<Map<String, Object>>() {});
        return TokenSettings.withSettings(map).build();
    }
}
