package org.example.datasource.repository;

import org.example.datasource.mapper.UserDataMapper;
import org.example.datasource.model.UserEntity;
import org.example.domain.model.User;

public class UserRepository {

    private final UserDataMapper mapper;

    public UserRepository(UserDataMapper mapper) {
        this.mapper = mapper;
    }

    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);

        entity.setId(1L);

        return mapper.toDomain(entity);
    }

    public User findById(Long id) {
        UserEntity entity = new UserEntity();
        entity.setId(id);
        entity.setName("John");

        return mapper.toDomain(entity);
    }
}