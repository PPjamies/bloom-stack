package org.bloom.authservice.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.bloom.authservice.constant.AuthConstants;

@Getter
@Builder
public class RefreshTokenRequest extends Request {

    @NotBlank(message = AuthConstants.REQUIRED_REFRESH_TOKEN)
    private String refreshToken;
}
