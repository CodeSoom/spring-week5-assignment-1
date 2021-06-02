package com.codesoom.assignment.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findUserById(Long id);

    List<User> findAll();

    User save(User user);

    void deleteUserById(Long id);
}
