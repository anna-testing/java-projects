package org.example.web.controller;

import org.example.domain.model.User;
import org.example.domain.service.UserService;
import org.example.web.mapper.UserWebMapper;
import org.example.web.model.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserWebMapper mapper;

    public UserController(UserService userService, UserWebMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(mapper.toResponse(user));
    }
}