package org.bloom.authservice.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.bloom.authservice.constant.AuthConstants;

@Getter
public class TokenRequest extends Request {

    @NotBlank(message = AuthConstants.REQUIRED_USERNAME)
    private String username;
}
