package org.example.domain.service;

import org.example.domain.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByLogin(String login);
    boolean authenticate(String login, String password);
}