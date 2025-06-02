package org.bloom.authservice.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.bloom.authservice.constant.AuthConstants;

@Getter
@Builder
public class RefreshTokenRequest extends Request {

    @NotBlank(message = AuthConstants.REFRESH_TOKEN_REQUIRED)
    private String refreshToken;
}
