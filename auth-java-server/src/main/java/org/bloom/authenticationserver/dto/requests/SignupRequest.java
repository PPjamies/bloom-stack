package org.bloom.authenticationserver.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.bloom.authenticationserver.constant.AuthConstants;

@Getter
public class SignupRequest extends Request {

    @NotBlank(message = AuthConstants.REQUIRED_USERNAME)
    private String username;

    @NotBlank(message = AuthConstants.REQUIRED_PASSWORD)
    private String password;
}
