package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UserTest {

    private static final Long ID = 1L;
    private static final String NAME = "홍길동";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    @DisplayName("기본 생성자로 사용자 엔티티를 생성할 수 있다.")
    @Test
    void createNoArgsConstructor () {
        assertThat(new User()).isNotNull();
    }

    @DisplayName("생성자로 사용자 엔티티를 생성할 수 있다.")
    @Test
    void createWithAllArgsConstructorTest() {
        final User user = new User(ID, NAME, EMAIL, PASSWORD);

        assertThat(user).isNotNull();
        assertAll(() -> {
           assertThat(user.getId()).isEqualTo(ID);
           assertThat(user.getName()).isEqualTo(NAME);
           assertThat(user.getEmail()).isEqualTo(EMAIL);
           assertThat(user.getPassword()).isEqualTo(PASSWORD);
        });
    }

}
