package org.example.domain.service;

import org.example.web.model.SignUpRequest;
import java.util.Optional;
import java.util.UUID;

public interface AuthService {
    boolean register(SignUpRequest request);
    Optional<UUID> authorize(String authHeader); // authHeader = "Basic base64(login:password)"
}