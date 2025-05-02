package org.bloom.authenticationserver.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.bloom.authenticationserver.constant.AuthConstants;

@Getter
public class RefreshTokenRequest extends Request {

    @NotBlank(message = AuthConstants.REQUIRED_REFRESH_TOKEN)
    private String refreshToken;
}
