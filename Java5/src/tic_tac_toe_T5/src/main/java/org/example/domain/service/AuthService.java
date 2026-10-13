package org.example.domain.service;

import org.example.web.model.SignUpRequest;
import org.example.web.model.JwtRequest;
import org.example.web.model.JwtResponse;
import org.example.web.model.RefreshJwtRequest;
import org.example.security.JwtAuthentication;

public interface AuthService {
    boolean register(SignUpRequest request);

    /**
     * Authenticate a user using login and password and return JWT tokens.
     */
    JwtResponse login(JwtRequest request);

    /**
     * Refresh the access token using a valid refresh token.
     */
    JwtResponse refreshAccessToken(RefreshJwtRequest request);

    /**
     * Refresh the refresh token using a valid refresh token.
     */
    JwtResponse refreshRefreshToken(RefreshJwtRequest request);

    /**
     * Retrieve the current JWT authentication from the security context.
     */
    JwtAuthentication getAuthentication();
}