package com.codesoom.assignment.domain;

import java.util.Optional;

/**
 * 사용자 데이터를 다루는 명령을 정의합니다.
 */
public interface UserRepository {
    /**
     * 해당 식별자의 사용자를 반환합니다.
     * @param id 사용자 식별자
     * @return 사용자
     */
    Optional<User> findById(Long id);

    /**
     * 사용자를 생성하고 반환합니다.
     * @param user 생성할 사용자
     * @return 생성된 사용자
     */
    User save(User user);

    /**
     * 해당 식별자의 사용자를 삭제합니다.
     * @param id 사용자 식별자
     */
    void deleteById(Long id);
}
