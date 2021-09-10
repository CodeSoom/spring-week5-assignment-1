package com.codesoom.assignment.domain;

/**
 * 사용자 모델의 메소드를 정의합니다.
 */
public interface UserModel {
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
