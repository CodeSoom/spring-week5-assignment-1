package com.codesoom.assignment.application.interfaces;

import com.codesoom.assignment.domain.entities.User;

/**
 * 사용자 생성에 대한 비지니스 로직을 처리한다
 * <p>
 * All Known Implementing Classes:
 * UserCrudService
 * </p>
 */
public interface UserCreateService {
    /**
     * User 엔티티 객체 생성하고, 생성된 객체를 반환한다.
     * <p>
     * @param user User 객체
     * @return User 객체
     * </p>
     */
    User create(User user);
}
