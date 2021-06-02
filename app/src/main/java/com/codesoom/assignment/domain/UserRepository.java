package com.codesoom.assignment.domain;

import java.util.Optional;

/**
 * 저장소의 사용자 정보 관리를 담당합니다.
 */
public interface UserRepository {
    User save(User user);

    void delete(User user);

    Optional<User> findById(Long id);
}
