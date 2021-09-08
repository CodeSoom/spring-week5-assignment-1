package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User 테스트")
class UserTest {
    private final Long ID = 1L;
    private final String NAME = "이름";
    private final String PASSWORD = "비밀번호";
    private final String EMAIL = "이메일";

    @Test
    @DisplayName("builder 메소드는 입력된 정보로 User를 생성합니다.")
    void builder() {
        User user = User.builder()
                .id(ID)
                .name(NAME)
                .password(PASSWORD)
                .email(EMAIL)
                .build();

        assertThat(user.getId()).isEqualTo(ID);
        assertThat(user.getName()).isEqualTo(NAME);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
        assertThat(user.getEmail()).isEqualTo(EMAIL);
    }

    @Test
    @DisplayName("update 메서드는 주어진 user의 name, password, email로 데이터를 변경합니다.")
    void update() {
        User user = new User();
        User inputUser = new User(ID, NAME, PASSWORD, EMAIL);

        user.update(inputUser);

        assertThat(user.getName()).isEqualTo(NAME);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
        assertThat(user.getEmail()).isEqualTo(EMAIL);
    }
}
