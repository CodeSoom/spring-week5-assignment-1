package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);

    User save(User user);

    void delete(User user);
}
