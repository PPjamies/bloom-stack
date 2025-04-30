package org.bloom.authorizationserver.dto.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenRequest extends Request {
    private String refreshToken;
}
