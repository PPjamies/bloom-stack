package org.bloom.authservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Client {
    private String name;
    private String clientId;
    private String clientSecret;
    private String tokenEndpoint;
    private List<String> scopes;
}
