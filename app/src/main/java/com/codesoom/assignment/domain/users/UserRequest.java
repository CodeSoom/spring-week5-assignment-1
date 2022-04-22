package com.codesoom.assignment.domain.users;

/**
 * 회원 등록 & 수정 공통 요청
 */
public interface UserRequest {

    String getEmail();

    String getName();

    String getPassword();
}
