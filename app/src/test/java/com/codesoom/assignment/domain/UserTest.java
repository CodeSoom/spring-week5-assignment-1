package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    private static final String NAME = "양효주";
    private static final String EMAIL = "yhyojoo@codesoom.com";
    private static final String PASSWORD = "112233!!";

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    @Test
    @DisplayName("사용자 정보가 정상적으로 등록되었는지 확인합니다")
    void creationWithBuilder() {
        assertThat(user.getName()).isEqualTo(NAME);
        assertThat(user.getEmail()).isEqualTo(EMAIL);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    @DisplayName("사용자 정보가 정상적으로 변경되었는지 확인합니다")
    void updateWith() {
        user.updateWith(User.builder()
                .name("효주")
                .email("joo@codesoom.com")
                .password("123!")
                .build()
        );

        assertThat(user.getName()).isEqualTo("효주");
        assertThat(user.getEmail()).isEqualTo("joo@codesoom.com");
        assertThat(user.getPassword()).isEqualTo("123!");
    }
}
