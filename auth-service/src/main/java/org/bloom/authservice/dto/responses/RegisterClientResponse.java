package org.bloom.authservice.dto.responses;

import lombok.Builder;
import lombok.Data;
import org.bloom.authservice.dto.Client;

@Data
@Builder
public class RegisterClientResponse extends Response {
    private Client client;
}
