package org.bloom.authservice.service;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.bloom.authservice.constant.AuthConstants;
import org.bloom.authservice.converter.AuthConverter;
import org.bloom.authservice.dto.Client;
import org.bloom.authservice.repository.ClientRepository;
import org.bloom.authservice.serializer.Oauth2ClientSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    @Value("${oauth.token.endpoint}")
    private String tokenEndpoint;

    private final PasswordEncoder passwordEncoder;
    private final RegisteredClientRepository registeredClientRepository;
    private final ClientRepository clientRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    public Client registerClient(@NotBlank String name) {

        String registeredClientId = UUID.randomUUID().toString();
        String clientId = UUID.randomUUID().toString();
        String clientSecret = new BigInteger(256, secureRandom).toString(32);

        // create spring oauth2 registered client
        RegisteredClient registeredClient = RegisteredClient
                .withId(registeredClientId)
                .clientName(name)
                .clientId(clientId)
                .clientSecret(passwordEncoder.encode(clientSecret))
                .clientSecretExpiresAt(Instant.now().plusMillis(AuthConstants.CLIENT_SECRET_EXP_MILLIS))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scope("read")
                .scope("write")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMillis(AuthConstants.ACCESS_TOKEN_EXP_MILLIS))
                        .refreshTokenTimeToLive(Duration.ofMillis(AuthConstants.REFRESH_TOKEN_EXP_MILLIS))
                        .build())
                .build();

        // register the client with spring's authorization server
        registeredClientRepository.save(registeredClient);

        // save client to our db
        clientRepository.save(AuthConverter.convert(new Oauth2ClientSerializer(), registeredClient));

        return Client.builder()
                .name(name)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tokenEndpoint(tokenEndpoint)
                .scopes(List.of("read", "write"))
                .build();
    }
}
