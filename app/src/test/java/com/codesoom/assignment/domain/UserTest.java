package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private static final Long ID = 1L;
    private static final String NAME = "Min";
    private static final String EMAIL = "min@gmail.com";
    private static final String PASSWORD = "1q2w3e!";

    @Test
    @DisplayName("사용자를 생성하고 입력된 값을 리턴한다")
    void createWithBuilder() {
        User user = User.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        assertThat(user.getId()).isEqualTo(ID);
        assertThat(user.getName()).isEqualTo(NAME);
        assertThat(user.getEmail()).isEqualTo(EMAIL);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
    }
}
