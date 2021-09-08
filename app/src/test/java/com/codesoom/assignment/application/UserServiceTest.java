package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.codesoom.assignment.domain.User;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

    @Test
    void create() {
        UserService userService = new UserService();

        User user = userService.createUser(User.builder()
            .name("name")
            .email("email")
            .password("password")
            .build());

        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getEmail()).isEqualTo("email");
        assertThat(user.getPassword()).isEqualTo("password");
    }
}
