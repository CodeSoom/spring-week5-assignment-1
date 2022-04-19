package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;

/**
 * 회원 생성
 */
public interface UserSaveService {

    /**
     * 회원을 생성합니다.
     *
     * @param userSaveRequest 사용자가 입력한 회원 정보
     * @return 저장된 회원 정보
     */
    User saveUser(UserSaveRequest userSaveRequest);

}
