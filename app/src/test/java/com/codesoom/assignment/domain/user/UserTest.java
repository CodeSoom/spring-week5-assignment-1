package com.codesoom.assignment.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    private static final Long ID = 1L;
    private static final String NAME = "test";
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "test";

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
}
