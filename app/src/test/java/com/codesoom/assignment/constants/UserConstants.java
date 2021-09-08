package com.codesoom.assignment.constants;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;

/**
 * User관련 테스트에서 반복적으로 사용되는 데이터 정의
 */
public interface UserConstants {
    final Long ID = 1L;
    final String NAME = "테스트 유저";
    final String PASSWORD = "password";
    final String EMAIL = "test@test.com";
    final User USER = User.builder()
        .email(EMAIL)
        .name(NAME)
        .password(PASSWORD)
        .build();
    final UserData USER_DATA = UserData.builder()
        .email(EMAIL)
        .name(NAME)
        .password(PASSWORD)
        .build();
}
