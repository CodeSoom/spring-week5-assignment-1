package com.codesoom.assignment.application.interfaces;

import com.codesoom.assignment.domain.entities.User;

import java.util.List;

/**
 * User 타입 조회에 대한 비지니스 로직을 처리한다
 * <p>
 * All Known Implementing Classes:
 * UserCrudService
 * </p>
 */
public interface UserShowService {
    /**
     * 모든 User 엔티티를 List 형태로 반환한다
     * <p>
     * @return User 엔티티를 내부 요소로 하는 List Collection
     * </p>
     */
    List<User> showAll();

    /**
     * 매개변수로 전달 받은 id에 해당하는 User 엔티티를 반환한다
     * <p>
     * @param id User 엔티티의 Id에 해당
     * @return User 엔티티
     * </p>
     */
    User showById(Long id);
}
