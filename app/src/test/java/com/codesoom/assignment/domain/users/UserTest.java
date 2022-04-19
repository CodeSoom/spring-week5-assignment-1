package com.codesoom.assignment.domain.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User 클래스")
class UserTest {

    @Nested
    @DisplayName("equals 메소드는")
    class Describe_equals {

        final User user = User.builder()
                .id(1L)
                .email("test@naver.com")
                .name("test")
                .password("1234")
                .build();

        @Nested
        @DisplayName("같은 속성의 객체가 주어졌을 때")
        class Context_objectOfSameAttributes {

            final User sameUser = User.builder()
                    .id(1L)
                    .email("test@naver.com")
                    .name("test")
                    .password("1234")
                    .build();

            @Test
            @DisplayName("true 를 리턴한다.")
            void it_return_true() {
                assertThat(user.equals(sameUser)).isTrue();
            }
        }

        @Nested
        @DisplayName("속성이 다른 객체가 주어졌을 때")
        class Context_objectOfAnotherAttributes {

            final User anotherUser = User.builder()
                    .id(1L)
                    .email("test@naver.com")
                    .name("test_different")
                    .password("1234")
                    .build();

            @Test
            @DisplayName("false 를 리턴한다.")
            void it_return_false() {
                assertThat(user.equals(anotherUser)).isFalse();
            }
        }
    }
}
