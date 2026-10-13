package org.example.security;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.example.domain.model.Role;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

/**
 * Utility class for converting JWT {@link Claims} into a {@link JwtAuthentication} instance.
 */
@Component
public class JwtUtil {

    /**
     * Create a {@link JwtAuthentication} object from JWT claims.
     * The claims are expected to contain the subject (user UUID) and a "roles" claim with a list of role names.
     * Since Role enum now implements GrantedAuthority, we convert the role names to Role enum instances.
     */
    @SuppressWarnings("unchecked")
    public JwtAuthentication createAuthentication(Claims claims) {
        String subject = claims.getSubject();
        UUID userId = subject != null ? UUID.fromString(subject) : null;
        // Extract role names from claims and convert to Role enum (which implements GrantedAuthority)
        List<String> roleNames = claims.get("roles", List.class);
        List<Role> authorities = roleNames != null ? roleNames.stream()
                .map(name -> Role.valueOf(name))
                .collect(Collectors.toList())
                : List.of();
        return new JwtAuthentication(userId, authorities);
    }
}
