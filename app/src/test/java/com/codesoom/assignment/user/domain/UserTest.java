package com.codesoom.assignment.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    private static final Long ID = 1L;
    private static final String NAME = "test";
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "test";

    private static final String UPDATE_NAME = "new_test";
    private static final String UPDATE_EMAIL = "new@test.com";
    private static final String UPDATE_PASSWORD = "new_test";

    @DisplayName("사용자를 생성하고 설정한 값을 리턴한다.")
    @Test
    void create() {
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

    @DisplayName("사용자의 정보를 갱신한다.")
    @Test
    void change() {
        User user = User.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
        User expect = User.builder()
                .name(UPDATE_NAME)
                .email(UPDATE_EMAIL)
                .password(UPDATE_PASSWORD)
                .build();

        user.changeWith(expect);

        assertThat(user.getName()).isEqualTo(expect.getName());
        assertThat(user.getEmail()).isEqualTo(expect.getEmail());
        assertThat(user.getPassword()).isEqualTo(expect.getPassword());
    }
}
