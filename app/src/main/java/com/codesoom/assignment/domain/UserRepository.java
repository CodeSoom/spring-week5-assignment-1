package com.codesoom.assignment.domain;

import java.util.Optional;

/**
 * User 데이터 저장 장소
 */
public interface UserRepository {
    /**
     * 주어진 user 를 저장하고 저장된 user 를 반환
     *
     * @param user 저장할 user
     * @return 저장된 user
     */
    User save(User user);

    /**
     * 주어진 id 와 일치하는 product 반환
     *
     * @param id 식별자
     * @return 주어진 id 와 일치하는 user
     */
    Optional<User> findById(Long id);

    /**
     * 주어진 user 를 삭제
     *
     * @param user 삭제할 user
     */
    void delete(User user);
}
