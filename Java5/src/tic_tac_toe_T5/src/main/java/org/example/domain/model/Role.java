package org.example.domain.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enumeration of user roles used in the application.
 * Currently only a single role is required for the bootcamp tasks.
 * Implements GrantedAuthority to integrate with Spring Security.
 */
public enum Role implements GrantedAuthority {
    USER;

    /**
     * Returns the authority string for this role.
     * Prefixes the role name with "ROLE_" as required by Spring Security.
     */
    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
