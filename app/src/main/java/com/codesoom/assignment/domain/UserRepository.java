package com.codesoom.assignment.domain;

import java.util.Optional;

/**
 * 사용자 도메인의 퍼시스턴스 레이어.
 */
public interface UserRepository {
    User save(User user);

    void delete(User user);

    Optional<User> findById(Long id);
}
