package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    public static final String EMAIl = "kimchi@joa.com";
    public static final String NAME = "갓김치";
    public static final String PASSWORD = "1234567";

    @Test
    @DisplayName("유저 생성")
    void creation() {
        User user = new User(EMAIl, NAME, PASSWORD);

        assertThat(user.getName()).isEqualTo(EMAIl);
        assertThat(user.getEmail()).isEqualTo(EMAIl);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
    }
}
