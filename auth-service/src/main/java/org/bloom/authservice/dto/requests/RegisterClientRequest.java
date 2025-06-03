package org.bloom.authservice.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.bloom.authservice.constant.AuthConstants;

@Getter
@Builder
public class RegisterClientRequest extends Request {

    @NotBlank(message = AuthConstants.CLIENT_NAME_REQUIRED)
    private String name;
}
