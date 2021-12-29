package com.codesoom.assignment.domain;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    void delete(User user);
}
