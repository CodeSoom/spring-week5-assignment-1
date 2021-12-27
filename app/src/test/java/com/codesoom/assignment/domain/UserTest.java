package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User 클래스")
class UserTest {
    @Nested
    @DisplayName("빌더를 사용해 생성자를 만든다면")
    class Describe_create {
        @Test
        @DisplayName("user가 생성된다.")
        void it_return_user() {
            User user = User.builder()
                    .id(1L)
                    .name("코드숨")
                    .password("1234")
                    .email("codesoom@gmail.com")
                    .build();
            assertThat(user.getId()).isEqualTo(1L);
            assertThat(user.getName()).isEqualTo("코드숨");
            assertThat(user.getPassword()).isEqualTo("1234");
            assertThat(user.getEmail()).isEqualTo("codesoom@gmail.com");
        }
    }

    @Nested
    @DisplayName("change 메서드는")
    class Decribe_change {
        @Test
        @DisplayName("사용자 정보를 바꿔준다.")
        void it_change_user() {
            User user = User.builder()
                    .id(1L)
                    .name("코드숨")
                    .password("1234")
                    .email("codesoom@gmail.com")
                    .build();

            user.changeWith(User.builder()
                    .name("코드숨1")
                    .password("5678")
                    .email("codesoom1@gmail.com")
                    .build());

            assertThat(user.getName()).isEqualTo("코드숨1");
            assertThat(user.getPassword()).isEqualTo("5678");
            assertThat(user.getEmail()).isEqualTo("codesoom1@gmail.com");
        }
    }
}
