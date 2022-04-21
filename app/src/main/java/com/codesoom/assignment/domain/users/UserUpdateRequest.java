package com.codesoom.assignment.domain.users;

/**
 * 회원 수정 요청
 */
public interface UserUpdateRequest {

    String getUpdateEmail();

    String getUpdateName();

    String getUpdatePassword();
}
