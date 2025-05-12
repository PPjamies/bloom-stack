package org.bloom.authservice.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.bloom.authservice.constant.AuthConstants;

@Getter
@Builder
public class SignupRequest extends Request {

    @NotBlank(message = AuthConstants.REQUIRED_USERNAME)
    private String username;

    @NotBlank(message = AuthConstants.REQUIRED_PASSWORD)
    private String password;
}
