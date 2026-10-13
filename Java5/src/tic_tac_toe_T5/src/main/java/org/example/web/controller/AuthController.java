package org.example.web.controller;

import org.example.domain.service.AuthService;
import org.example.web.model.SignUpRequest;
import org.example.web.model.JwtRequest;
import org.example.web.model.JwtResponse;
import org.example.web.model.RefreshJwtRequest;
import org.example.security.JwtAuthentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> login(@RequestBody JwtRequest request) {
        JwtResponse response = authService.login(request);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshJwtRequest request) {
        JwtResponse response = authService.refreshAccessToken(request);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshJwtRequest request) {
        JwtResponse response = authService.refreshRefreshToken(request);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }
    }

    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo() {
        JwtAuthentication auth = authService.getAuthentication();
        if (auth != null) {
            return ResponseEntity.ok(auth.getPrincipal());
        } else {
            return ResponseEntity.status(401).body("Not authenticated");
        }
    }
}