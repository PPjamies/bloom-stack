package org.bloom.authenticationserver.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse extends Response {
    private String accessToken;
    private String refreshToken;
}
