package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserCreateRequest;
import com.codesoom.assignment.dto.UserUpdateRequest;

public interface UserService {

    /**
     * 회원을 생성해 반환합니다.
     * @param user 생성할 회원
     * @return 생성된 회원
     */
    User create(UserCreateRequest user);

    /**
     * 회원을 수정해 반환합니다.
     * @param id 수정할 회원 아이디
     * @param user 수정할 회원
     * @return 수정된 회원
     */
    User update(Long id, UserUpdateRequest user);

    /**
     * 회원을 삭제합니다.
     * @param id 삭제할 회원 아이디
     */
    void delete(Long id);
}
