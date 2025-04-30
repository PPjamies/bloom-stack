package org.bloom.authorizationserver.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.bloom.authorizationserver.constant.AuthorizationConstants;

@Data
@Builder
public class TokenRequest extends Request {

    @NotBlank(message = AuthorizationConstants.REQUIRED_USERNAME)
    private String username;
}
