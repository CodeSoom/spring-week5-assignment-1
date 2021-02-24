package com.codesoom.assignment.User.domain;

import com.codesoom.assignment.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private final Long ID = 1L;
    private final String NAME = "올리브";
    private final String EMAIL = "olive@gmail.com";
    private final String PASSWORD = "olivePW";

    private final String NEW_NAME = "new올리브";
    private final String NEW_PASSWORD = "newOlivePW";

    @Test
    @DisplayName("회원을 생성한다.")
    void creationWithBuilder() {
        User user = User.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("올리브");
        assertThat(user.getEmail()).isEqualTo("olive@gmail.com");
        assertThat(user.getPassword()).isEqualTo("olivePW");
    }

    @Test
    @DisplayName("회원 정보를 수정한다.")
    void updateUser() {
        User user = User.builder()
                .id(1L)
                .name(NAME)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        user.changeWith(User.builder()
                .name(NEW_NAME)
                .password(NEW_PASSWORD)
                .build());

        assertThat(user.getName()).isEqualTo(NEW_NAME);
        assertThat(user.getPassword()).isEqualTo(NEW_PASSWORD);
    }
}
