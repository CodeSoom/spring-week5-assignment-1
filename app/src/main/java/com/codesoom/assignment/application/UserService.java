package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 생성, 수정, 삭제 기능을 담당하는 클래스
 */
public interface UserService {

    /**
     * 사용자를 생성하고, 생성된 사용자 정보를 리턴합니다.
     * @param source 생성할 사용자 정보
     * @return 생성된 사용자
     */
    User createUser(UserData source) throws Exception;

    /**
     * 사용자 정보를 수정하고 수정된 사용자 정보를 리턴합니다.
     * @param id 사용자 id
     * @param source 수정할 사용자 정보
     * @return 수정된 사용자
     */
    User updateUser(Long id, UserData source);

    /**
     * 사용자를 삭제합니다.
     * @param id 삭제할 사용자 id
     */
    void deleteUser(Long id);

    Optional<User> emailCheck(String mail) throws Exception;

}

