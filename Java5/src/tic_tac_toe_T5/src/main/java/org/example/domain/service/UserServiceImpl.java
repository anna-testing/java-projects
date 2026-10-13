package org.example.domain.service;

import org.example.datasource.mapper.UserDataMapper;
import org.example.datasource.repository.UserJpaRepository;
import org.example.domain.model.User;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserJpaRepository repository;
    private final UserDataMapper mapper;

    public UserServiceImpl(UserJpaRepository repository, UserDataMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        return mapper.toDomain(repository.save(mapper.toEntity(user)));
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return repository.findByLogin(login).map(mapper::toDomain);
    }

    @Override
    public boolean authenticate(String login, String password) {
        Optional<User> user = findByLogin(login);
        return user.isPresent() && user.get().getPassword().equals(password);
    }
}