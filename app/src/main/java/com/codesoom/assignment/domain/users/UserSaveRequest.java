package com.codesoom.assignment.domain.users;

/**
 * 회원 등록 요청
 */
public interface UserSaveRequest {

    String getEmail();

    String getName();

    String getPassword();
}
