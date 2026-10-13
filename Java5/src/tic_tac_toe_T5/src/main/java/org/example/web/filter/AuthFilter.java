package org.example.web.filter;

import java.io.IOException;
import java.util.UUID;

import org.example.security.JwtAuthentication;
import org.example.security.JwtProvider;
import org.example.security.JwtUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;
    private final JwtUtil jwtUtil;

    public AuthFilter(JwtProvider jwtProvider, JwtUtil jwtUtil) {
        this.jwtProvider = jwtProvider;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        // Allow access to auth endpoints without authentication
        if (path.equals("/auth/register") || path.equals("/auth/login") || path.equals("/auth/refresh") || path.equals("/auth/refresh-token")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7); // Remove "Bearer " prefix
        
        if (!jwtProvider.validateAccessToken(token)) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            return;
        }

        try {
            Claims claims = jwtProvider.getClaims(token);
            // Set userId as request attribute for use in controllers
            httpRequest.setAttribute("userId", UUID.fromString(claims.getSubject()));
            JwtAuthentication authentication = jwtUtil.createAuthentication(claims);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            chain.doFilter(request, response);
        } catch (io.jsonwebtoken.JwtException | IllegalArgumentException e) {
            SecurityContextHolder.clearContext();
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Failed to authenticate token");
        }
    }
}
