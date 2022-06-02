package com.codesoom.assignment.fixtures;

import com.codesoom.assignment.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserFixture {
    private final Long USER_ID = 1L;
    private final String USER_NAME = "Test User";
    private final String USER_EMAIL = "hello@gmail.com";
    private final String USER_PASSWORD = "yahOo~!@12345";

    public User user() {
        return User.builder()
                .id(USER_ID)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();
    }

    public User userWithoutId() {
        return User.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();
    }

    public User userWithEmptyName() {
        return User.builder()
                .name(" ")
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();
    }
}
