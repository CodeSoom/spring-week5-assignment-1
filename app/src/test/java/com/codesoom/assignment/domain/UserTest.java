package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User 클래스")
class UserTest {
    private final Long USER_ID = 1L;
    private final String USER_NAME = "양홍석";
    private final String USER_EMAIL = "davidyang2149@gmail.com";
    private final String USER_PASSWORD = "YouHaveNoIdea";


    @Test
    @DisplayName("빌더로 사용자 엔티티를 생성할 수 있다")
    void creationWithBuilder() {
        User user = User.builder()
                .id(USER_ID)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();

        assertThat(user.getId()).isEqualTo(USER_ID);
        assertThat(user.getName()).isEqualTo(USER_NAME);
        assertThat(user.getEmail()).isEqualTo(USER_EMAIL);
        assertThat(user.getPassword()).isEqualTo(USER_PASSWORD);
    }
}
