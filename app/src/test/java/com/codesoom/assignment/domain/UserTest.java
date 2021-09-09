package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codesoom.assignment.constant.UserConstant.NAME;
import static com.codesoom.assignment.constant.UserConstant.EMAIL;
import static com.codesoom.assignment.constant.UserConstant.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Test
    @DisplayName("유저 생성")
    void createUser(){
        // when
        User user = User.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        // then
        assertThat(user.name()).isEqualTo(NAME);
        assertThat(user.email()).isEqualTo(EMAIL);
    }
}
