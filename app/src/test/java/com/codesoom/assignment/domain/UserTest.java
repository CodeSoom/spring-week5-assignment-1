package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User")
public class UserTest {
    @Nested
    @DisplayName("User 빌더는")
    class Describe_UserBuilder{
        @Test
        @DisplayName("User를 생성한다.")
        void it_returns_user(){
            User user = User.builder()
                            .id(1L)
                            .email("honggd@gmail.com")
                            .name("홍길동")
                            .password("password")
                            .build();

            assertThat(user.getId()).isEqualTo(1L);
            assertThat(user.getEmail()).isEqualTo("honggd@gmail.com");
            assertThat(user.getName()).isEqualTo("홍길동");
            assertThat(user.getPassword()).isEqualTo("password");
        }
    }

    @Nested
    @DisplayName("setter는")
    class Describe_setter{
        User user;

        @BeforeEach
        void prepare(){
            user = User.builder()
                    .id(1L)
                    .email("honggd@gmail.com")
                    .name("홍길동")
                    .password("password")
                    .build();
        }

        @Test
        @DisplayName("User의 속성을 변경한다")
        void it_changes_attribute(){
            user.setId(2L);
            user.setEmail("new_honggd@gmail.com");
            user.setName("new_홍길동");
            user.setPassword("new_password");

            assertThat(user.getId()).isEqualTo(2L);
            assertThat(user.getEmail()).isEqualTo("new_honggd@gmail.com");
            assertThat(user.getName()).isEqualTo("new_홍길동");
            assertThat(user.getPassword()).isEqualTo("new_password");
        }
    }

}
