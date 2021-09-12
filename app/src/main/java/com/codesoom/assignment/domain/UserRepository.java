package com.codesoom.assignment.domain;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    void delete(User user);

    Optional<User> findById(Long id);
}
