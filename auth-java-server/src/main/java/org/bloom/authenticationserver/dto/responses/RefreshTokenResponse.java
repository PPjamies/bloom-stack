package org.bloom.authenticationserver.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenResponse extends Response {
    private String accessToken;
}
