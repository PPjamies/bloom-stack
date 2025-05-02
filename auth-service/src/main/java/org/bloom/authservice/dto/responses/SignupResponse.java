package org.bloom.authservice.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupResponse extends Response {
    private String username;
}
