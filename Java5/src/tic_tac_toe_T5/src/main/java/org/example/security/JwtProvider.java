package org.example.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.example.domain.model.Role;
import org.example.domain.model.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

/**
 * Utility class for generating and validating JWT tokens.
 */
@Component
public class JwtProvider {

    // In a real project the secret should be externalized and strong.
    private static final String SECRET_KEY = "my-very-secret-key-which-should-be-long-and-random";

    private static final SecretKey SIGNING_KEY = new SecretKeySpec(
            SECRET_KEY.getBytes(StandardCharsets.UTF_8),
            "HmacSHA256"
    );

    // Token validity periods (in milliseconds)
    private static final long ACCESS_TOKEN_VALIDITY = 15 * 60 * 1000; // 15 minutes
    private static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000; // 7 days

    /**
     * Generates an access token for the given user.
     * The token contains the user's UUID as subject and role names as a "roles" claim.
     */
    public String generateAccessToken(User user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + ACCESS_TOKEN_VALIDITY);
        // Extract role names from Role enum (now implements GrantedAuthority)
        List<String> roleNames = user.getRoles().stream()
                .map(Role::name)
                .collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("roles", roleNames)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SIGNING_KEY)
                .compact();
    }

    /**
     * Generates a refresh token for the given user.
     * The token contains only the user's UUID as subject.
     */
    public String generateRefreshToken(User user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + REFRESH_TOKEN_VALIDITY);
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SIGNING_KEY)
                .compact();
    }

    /**
     * Validates an access token.
     */
    public boolean validateAccessToken(String token) {
        return validateToken(token);
    }

    /**
     * Validates a refresh token.
     */
    public boolean validateRefreshToken(String token) {
        return validateToken(token);
    }

    /**
     * Common validation method for both token types.
     */
    private boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SIGNING_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * Extracts claims from a JWT token.
     */
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
