package org.example.datasource.mapper;

import org.example.datasource.model.UserEntity;
import org.example.domain.model.User;
import org.example.domain.model.Role;
import org.springframework.stereotype.Component;

@Component
public class UserDataMapper {

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;
        return new User(entity.getId(), entity.getLogin(), entity.getPassword(), entity.getRoles());
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) return null;
        return new UserEntity(domain.getId(), domain.getLogin(), domain.getPassword(), domain.getRoles());
    }
}