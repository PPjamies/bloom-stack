package org.bloom.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bloom.authservice.dto.Client;
import org.bloom.authservice.dto.requests.RegisterClientRequest;
import org.bloom.authservice.dto.responses.RegisterClientResponse;
import org.bloom.authservice.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/register-client")
    public ResponseEntity<RegisterClientResponse> register(@Valid @RequestBody RegisterClientRequest request) {
        Client client = clientService.registerClient(request.getName(), request.getClientId(), request.getClientSecret());
        return ResponseEntity.ok(RegisterClientResponse.builder()
                .client(client)
                .build());
    }
}
