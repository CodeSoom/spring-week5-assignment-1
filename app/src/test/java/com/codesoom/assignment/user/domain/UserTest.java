package com.codesoom.assignment.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    private static final Long ID = 1L;
    private static final String NAME = "test";
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "test";

    private static final Long UPDATE_ID = 2L;
    private static final String UPDATE_NAME = "new_test";
    private static final String UPDATE_EMAIL = "new@test.com";
    private static final String UPDATE_PASSWORD = "new_test";

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    @DisplayName("getId은 식별자를 리턴한다")
    @Test
    void getId() {
        assertThat(user.getId()).isEqualTo(ID);
    }

    @DisplayName("getName은 이름을 리턴한다")
    @Test
    void getName() {
        assertThat(user.getName()).isEqualTo(NAME);
    }

    @DisplayName("getEmail은 이메일을 리턴한다")
    @Test
    void getMaker() {
        assertThat(user.getEmail()).isEqualTo(EMAIL);
    }

    @DisplayName("getPassword는 비밀번호를 리턴한다")
    @Test
    void getPrice() {
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    void change() {
        User source = User.builder()
                .name(UPDATE_NAME)
                .email(UPDATE_EMAIL)
                .password(UPDATE_PASSWORD)
                .build();

        user.changeWith(source);

        assertThat(user.getName()).isEqualTo(UPDATE_NAME);
        assertThat(user.getEmail()).isEqualTo(UPDATE_EMAIL);
        assertThat(user.getPassword()).isEqualTo(UPDATE_PASSWORD);
    }
}
