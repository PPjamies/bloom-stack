package org.bloom.authenticationserver.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.bloom.authenticationserver.constant.AuthConstants;

@Data
@Builder
public class TokenRequest extends Request {

    @NotBlank(message = AuthConstants.REQUIRED_USERNAME)
    private String username;
}
