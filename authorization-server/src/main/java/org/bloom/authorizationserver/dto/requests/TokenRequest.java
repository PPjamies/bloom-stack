package org.bloom.authorizationserver.dto.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRequest extends Request {
    private String username;
}
