package org.example.web.mapper;

import org.example.domain.model.User;
import org.example.web.model.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserWebMapper {

    public UserResponse toResponse(User user) {
        if (user == null) return null;
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setLogin(user.getLogin());
        return response;
    }
}