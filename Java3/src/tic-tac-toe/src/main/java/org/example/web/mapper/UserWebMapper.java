package org.example.web.mapper;

import org.example.domain.model.User;
import org.example.web.model.UserRequest;
import org.example.web.model.UserResponse;

public class UserWebMapper {

    public User toDomain(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        return user;
    }

    public UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        return response;
    }
}