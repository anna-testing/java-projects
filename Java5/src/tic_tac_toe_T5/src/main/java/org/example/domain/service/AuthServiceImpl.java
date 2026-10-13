package org.example.domain.service;

import org.example.web.model.SignUpRequest;
import org.example.web.model.JwtRequest;
import org.example.web.model.JwtResponse;
import org.example.web.model.RefreshJwtRequest;
import org.example.security.JwtProvider;
import org.example.security.JwtUtil;
import org.example.security.JwtAuthentication;
import org.example.domain.model.Role;
import org.example.domain.model.User;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserService userService, JwtProvider jwtProvider, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean register(SignUpRequest request) {
        if (userService.findByLogin(request.getLogin()).isPresent()) {
            return false;
        }
        UUID id = UUID.randomUUID();
        // New users get the USER role by default.
        User newUser = new User(id, request.getLogin(), request.getPassword(), List.of(Role.USER));
        userService.save(newUser);
        return true;
    }

    @Override
    public JwtResponse login(JwtRequest request) {
        String login = request.getLogin();
        String password = request.getPassword();
        if (!userService.authenticate(login, password)) {
            // In a real application you would throw an exception or return an error response.
            return null;
        }
        User user = userService.findByLogin(login).orElseThrow();
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        return new JwtResponse("Bearer", accessToken, refreshToken);
    }

    @Override
    public JwtResponse refreshAccessToken(RefreshJwtRequest request) {
        String refreshToken = request.getRefreshToken();
        if (!jwtProvider.validateRefreshToken(refreshToken)) {
            return null;
        }
        var claims = jwtProvider.getClaims(refreshToken);
        UUID userId = UUID.fromString(claims.getSubject());
        User user = userService.findById(userId).orElseThrow();
        String newAccessToken = jwtProvider.generateAccessToken(user);
        return new JwtResponse("Bearer", newAccessToken, refreshToken);
    }

    @Override
    public JwtResponse refreshRefreshToken(RefreshJwtRequest request) {
        String refreshToken = request.getRefreshToken();
        if (!jwtProvider.validateRefreshToken(refreshToken)) {
            return null;
        }
        var claims = jwtProvider.getClaims(refreshToken);
        UUID userId = UUID.fromString(claims.getSubject());
        User user = userService.findById(userId).orElseThrow();
        String newAccessToken = jwtProvider.generateAccessToken(user);
        String newRefreshToken = jwtProvider.generateRefreshToken(user);
        return new JwtResponse("Bearer", newAccessToken, newRefreshToken);
    }

    @Override
    public JwtAuthentication getAuthentication() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthentication) {
            return (JwtAuthentication) auth;
        }
        return null;
    }
}