package com.codesoom.assignment.domain;

/**
 * 사용자 생성 요청.
 */
public interface UserCreateRequest {
    /**
     * 이메일 주소를 리턴합니다.
     * @return 이메일 주소
     */
    String getEmail();
    /**
     * 사용자 이름을 리턴합니다.
     * @return 사용자 이름
     */
    String getName();

    /**
     * 비밀번호를 리턴합니다.
     * @return 비밀번호
     */
    String getPassword();
}
