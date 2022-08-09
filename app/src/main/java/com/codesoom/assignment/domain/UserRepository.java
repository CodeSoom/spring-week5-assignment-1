package com.codesoom.assignment.domain;

import java.util.Optional;

/**
 * 회원 정보를 관리하는 저장소 입니다.
 */
public interface UserRepository {
    /**
     * 회원 식별자로 회원을 조회합니다
     * @param id 회원 식별자
     * @return 조회된 회원
     */
    Optional<User> findById(Long id);

    /**
     * 회원을 저장합니다
     * @param user 저장할 회원
     * @return 저정된 회원
     */
    User save(User user);

    /**
     * 회원을 삭제합니다
     * @param user 삭제할 회원
     */
    void delete(User user);

    /**
     * 모든 회원을 삭제합니다
     */
    void deleteAll();
}
