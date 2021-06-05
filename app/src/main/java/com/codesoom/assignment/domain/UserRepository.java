package com.codesoom.assignment.domain;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findUserById(Long id);

    List<User> findAll();

    User save(User user);

    @Transactional
    void deleteUserById(Long id);
}
