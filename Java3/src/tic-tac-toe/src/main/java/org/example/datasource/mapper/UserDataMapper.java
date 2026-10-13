package org.example.datasource.mapper;

import org.example.datasource.model.UserEntity;
import org.example.domain.model.User;

public class UserDataMapper {

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;

        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        return user;
    }

    public UserEntity toEntity(User user) {
        if (user == null) return null;

        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        return entity;
    }
}