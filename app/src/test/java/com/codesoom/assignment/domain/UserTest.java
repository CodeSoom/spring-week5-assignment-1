package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    private final Long givenId = 1L;
    private final String givenName = "newoo";
    private final String givenEmail = "newoo@codesoom.com";
    private final String givenPassword = "codesoom123";

    private final String givenChangedName = "newoo2";
    private final String givenChangedEmail = "newoo2@codesoom.com";
    private final String givenChangedPassword = "codesoom789";

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(givenId)
                .name(givenName)
                .email(givenEmail)
                .password(givenPassword)
                .build();
    }

    @Test
    void creationWithBuilder() {
        assertThat(user.getId()).isEqualTo(givenId);
        assertThat(user.getName()).isEqualTo(givenName);
        assertThat(user.getEmail()).isEqualTo(givenEmail);
        assertThat(user.getPassword()).isEqualTo(givenPassword);
    }

    @Test
    void changeWithBuilder() {
        user.changeWith(
                User.builder()
                .name(givenChangedName)
                .email(givenChangedEmail)
                .password(givenChangedPassword)
                .build()
        );

        assertThat(user.getName()).isEqualTo(givenChangedName);
        assertThat(user.getEmail()).isEqualTo(givenChangedEmail);
        assertThat(user.getPassword()).isEqualTo(givenChangedPassword);
    }
}
