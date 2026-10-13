package org.example.web.controller;

import org.example.domain.service.AuthService;
import org.example.web.model.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignUpRequest request) {
        boolean success = authService.register(request);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("User already exists");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String authHeader) {
        var userId = authService.authorize(authHeader);
        if (userId.isPresent()) {
            return ResponseEntity.ok(userId.get());
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}