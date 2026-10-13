package org.example.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

/**
 * Custom {@link Authentication} implementation that holds JWT authentication data.
 */
public class JwtAuthentication implements Authentication {

    private final UUID principal;
    private final Collection<? extends GrantedAuthority> authorities;
    private boolean authenticated = true;

    public JwtAuthentication(UUID principal, Collection<? extends GrantedAuthority> authorities) {
        this.principal = principal;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return principal != null ? principal.toString() : null;
    }
}
