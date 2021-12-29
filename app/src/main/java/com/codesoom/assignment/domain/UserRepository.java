package com.codesoom.assignment.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    User save(User user);

    Optional<User> findById(Long id);

    void delete(User user);
}
