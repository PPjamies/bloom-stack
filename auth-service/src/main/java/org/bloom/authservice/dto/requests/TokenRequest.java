package org.bloom.authservice.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.bloom.authservice.constant.AuthConstants;

@Getter
@Builder
public class TokenRequest extends Request {

    @NotBlank(message = AuthConstants.USERNAME_REQUIRED)
    private String username;
}
