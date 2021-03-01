package com.codesoom.assignment.user.domain;

import java.util.List;
import java.util.Optional;

/**
 * 사용자의 정보 저장소.
 */
public interface UserRepository {
    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    void delete(User user);

    void deleteAll();
}
