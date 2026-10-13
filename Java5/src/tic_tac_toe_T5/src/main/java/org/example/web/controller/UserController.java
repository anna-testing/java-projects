package org.example.web.controller;

import java.util.UUID;

import org.example.domain.model.User;
import org.example.domain.service.UserService;
import org.example.security.JwtAuthentication;
import org.example.web.mapper.UserWebMapper;
import org.example.web.model.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserWebMapper mapper;

    public UserController(UserService userService, UserWebMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    /**
     * Helper method to extract the current user's UUID from SecurityContextHolder.
     */
    private UUID getCurrentUserId() {
        JwtAuthentication auth = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UUID) {
            return (UUID) auth.getPrincipal();
        }
        throw new RuntimeException("Not authenticated");
    }

    /**
     * Get user information by userId.
     * This endpoint is accessible to any authenticated user.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(mapper.toResponse(user));
    }

    /**
     * Get current authenticated user's information.
     */
    @GetMapping("/current")
    public ResponseEntity<UserResponse> getCurrentUser() {
        UUID currentUserId = getCurrentUserId();
        User user = userService.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(mapper.toResponse(user));
    }
}