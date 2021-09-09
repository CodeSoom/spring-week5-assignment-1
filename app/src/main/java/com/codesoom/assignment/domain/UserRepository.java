package com.codesoom.assignment.domain;

import java.util.Optional;

/**
 * User 리소스 비즈니스 로직을 정의한다.
 */
public interface UserRepository {
    User save(User user);

    void delete(User user);

    Optional<User> findById(Long id);
}
