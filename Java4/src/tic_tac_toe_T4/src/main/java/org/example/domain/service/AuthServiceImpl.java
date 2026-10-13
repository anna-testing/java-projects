package org.example.domain.service;

import org.example.web.model.SignUpRequest;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean register(SignUpRequest request) {
        if (userService.findByLogin(request.getLogin()).isPresent()) {
            return false;
        }
        UUID id = UUID.randomUUID();
        userService.save(new org.example.domain.model.User(id, request.getLogin(), request.getPassword()));
        return true;
    }

    @Override
    public Optional<UUID> authorize(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            return Optional.empty();
        }
        String base64Credentials = authHeader.substring("Basic".length()).trim();
        byte[] decoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(decoded);
        String[] parts = credentials.split(":", 2);
        if (parts.length != 2) return Optional.empty();
        String login = parts[0];
        String password = parts[1];
        if (userService.authenticate(login, password)) {
            return userService.findByLogin(login).map(org.example.domain.model.User::getId);
        }
        return Optional.empty();
    }
}