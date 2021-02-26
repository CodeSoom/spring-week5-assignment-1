package com.codesoom.assignment.domain;

import java.util.Optional;

/**
 * User 데이터 저장소
 *
 * @see User
 */
public interface UserRepository {
    /**
     * 주어진 id와 일치하는 user를 반환합니다.
     *
     * @param id user의 식별자
     * @return id와 일치하는 user
     */
    Optional<User> findById(Long id);

    /**
     * 주어진 user를 저장한 뒤 저장된 user를 반환합니다.
     *
     * @param user 저장하고자 하는 user
     * @return 저장뙨 user
     */
    User save(User user);

    /**
     * 주어진 user를 삭제합니다.
     *
     * @param user 삭제하고자 하는 user
     */
    void delete(User user);
}
