package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User 엔티티에서")
class UserTest {
    private final static String USERNAME = "username1";
    private final static String EMAIL = "example@example.com";
    private final static String PASSWORD = "password";

    @Test
    @DisplayName("빌더를 사용하여 사용자 엔티티를 생성할 수 있다")
    void createWithBuilder() {
        User user = User.builder()
                .username(USERNAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        assertThat(user).isNotNull();
    }

    @Test
    @DisplayName("생성자로 사용자 인스턴스를 리턴한다")
    void createWithArgsConstructor() {
        User user = new User(USERNAME, EMAIL, PASSWORD);

        assertThat(user).isNotNull();
    }
}
