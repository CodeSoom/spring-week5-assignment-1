package com.codesoom.assignment.domain;

/**
 * User 리소스 비즈니스 로직을 정의한다.
 */
public interface UserRepository {
    User save(User user);

    void delete(User user);
}
