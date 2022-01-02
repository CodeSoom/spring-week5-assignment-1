package com.codesoom.assignment.domain;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 데이터를 관리합니다.
 */
public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    void delete(User user);

    boolean existsByEmail(String email);
}
