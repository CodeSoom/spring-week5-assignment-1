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
        User user = User.builder()
                .email(EMAIl)
                .name(NAME)
                .password(PASSWORD)
                .build();

        assertThat(user.getName()).isEqualTo(NAME);
        assertThat(user.getEmail()).isEqualTo(EMAIl);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    @DisplayName("유저 정보 변경")
    void updateInfo() {
        User user = User.builder()
                .email(EMAIl)
                .name(NAME)
                .password(PASSWORD)
                .build();

        user.updateInfo("배추김치", "1234");

        assertThat(user.getName()).isEqualTo("배추김치");
        assertThat(user.getPassword()).isEqualTo("1234");
    }
}
