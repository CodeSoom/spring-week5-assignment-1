package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User 엔티티")
class UserTest {

    @Nested
    @DisplayName("빌더 객체는")
    class Describe_of_builder {

        private User.UserBuilder builder;

        @BeforeEach
        void setup() {
            this.builder = User.builder();
        }

        @Nested
        @DisplayName("유저 정보가 주어지면")
        class Context_of_user {

            private Long givenId = 1L;
            private String givenName= "name";
            private String givenEmail = "email";
            private String givenPassword = "password";

            @BeforeEach
            void setup() {
                builder = builder.id(givenId)
                        .name(givenName)
                        .email(givenEmail)
                        .password(givenPassword);
            }

            @Test
            @DisplayName("유저를 생성할 수 있다")
            void it_can_build_with_builder() {
                User user = builder.build();

                assertThat(user.getId()).isEqualTo(givenId);
                assertThat(user.getName()).isEqualTo(givenName);
                assertThat(user.getEmail()).isEqualTo(givenEmail);
                assertThat(user.getPassword()).isEqualTo(givenPassword);
            }

            @Test
            @DisplayName("문자열로 표현할 수 있다")
            void it_can_be_present_to_string() {
                assertThat(builder.toString())
                        .isEqualTo(String.format("User.UserBuilder(id=%d, name=%s, email=%s, password=%s)",
                                givenId,
                                givenName,
                                givenEmail,
                                givenPassword));
            }
        }
    }
}
