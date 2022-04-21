package com.codesoom.assignment.domain.users;

/**
 * 회원 등록 요청
 */
public interface UserSaveRequest {

    String getSaveEmail();

    String getSaveName();

    String getSavePassword();
}
