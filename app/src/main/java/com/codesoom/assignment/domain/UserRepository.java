package com.codesoom.assignment.domain;

import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(Long id);

    Long nextId();
    void delete(User user);
}
