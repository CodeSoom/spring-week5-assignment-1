package com.codesoom.assignment.fixtures;

import com.codesoom.assignment.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserFixture {
    private final Long USER_ID = 1L;
    private final String NAME = "Test User";
    private final String EMAIL = "hello@gmail.com";
    private final String PASSWORD = "yahOo~!@12345";
    private final String INVALID_PASSWORD = "123";

    public User user() {
        return User.builder()
                .id(USER_ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    public User userWithoutId() {
        return User.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    public User userWithEmptyName() {
        return User.builder()
                .name(" ")
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    public User userWithInvalidPassword() {
        return User.builder()
                .name(NAME)
                .email(EMAIL)
                .password(INVALID_PASSWORD)
                .build();
    }
}
