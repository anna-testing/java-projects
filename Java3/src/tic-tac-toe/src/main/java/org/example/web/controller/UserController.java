package org.example.web.controller;

import org.example.domain.model.User;
import org.example.domain.service.UserService;
import org.example.web.mapper.UserWebMapper;
import org.example.web.model.UserRequest;
import org.example.web.model.UserResponse;
import org.example.web.mapper.UserWebMapper;

public class UserController {

    private final UserService userService;
    private final UserWebMapper mapper;

    public UserController(UserService userService, UserWebMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    public UserResponse createUser(UserRequest request) {
        User user = mapper.toDomain(request);
        User saved = userService.createUser(user);
        return mapper.toResponse(saved);
    }

    public UserResponse getUser(Long id) {
        User user = userService.getUser(id);
        return mapper.toResponse(user);
    }
}