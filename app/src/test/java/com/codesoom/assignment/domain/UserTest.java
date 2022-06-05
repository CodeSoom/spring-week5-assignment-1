package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UserTest {
    private final Long ID = 1L;
    private final String NAME = "name";
    private final String EMAIL = "email@example.email";
    private final String PASSWORD = "1234";

    private final String NEW_NAME = "name1";
    private final String NEW_PASSWORD = "password12!#";

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

    @Test
    @DisplayName("회원을 생성한다.")
    void createWithBuilder() {
        assertAll(
                () -> assertThat(user.getId()).isEqualTo(ID),
                () -> assertThat(user.getName()).isEqualTo(NAME),
                () -> assertThat(user.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(user.getPassword()).isEqualTo(PASSWORD)
        );
    }

    @Test
    @DisplayName("회원을 수정한다.")
    void updateUser() {
        User source = User.builder()
                .name(NEW_NAME)
                .password(NEW_PASSWORD)
                .build();

        User changedUser = user.changeWith(source);

        assertAll(
                () -> assertThat(changedUser.getName()).isEqualTo(source.getName()),
                () -> assertThat(changedUser.getPassword()).isEqualTo(source.getPassword())
        );
    }
}
