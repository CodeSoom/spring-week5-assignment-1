package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Nested
@DisplayName("User 도메인 모델의")
class UserTest {

    @Nested
    @DisplayName("getter는")
    class DescribeGetter {

        private User user;

        @Nested
        @DisplayName("유저의 속성 값을 가지고 있는 경우")
        class ContextWithValue {

            @BeforeEach
            void modelSetUp() {
                User.UserBuilder userBuilder = new User.UserBuilder();
                user = userBuilder
                        .name("Jack")
                        .email("jack@email.com")
                        .password("asdf1234")
                        .build();
            }

            @Test
            @DisplayName("값을 반환한다")
            void ItReturnsValue() {
                assertThat(user.getName()).isEqualTo("Jack");
                assertThat(user.getEmail()).isEqualTo("jack@email.com");
                assertThat(user.getPassword()).isEqualTo("asdf1234");
            }
        }

        @Nested
        @DisplayName("유저의 속성이 값을 가지고 있지 않은 경우")
        class ContextWithNoValue {

            @BeforeEach
            void modelSetUp() {
                User.UserBuilder userBuilder = new User.UserBuilder();
                user = userBuilder.build();
            }

            @Test
            @DisplayName("null을 반환한다")
            void ItReturnsNull() {
                assertThat(user.getName()).isNull();
                assertThat(user.getEmail()).isNull();
                assertThat(user.getPassword()).isNull();
            }
        }
    }

    @Nested
    @DisplayName("change 메소드는")
    class DescribeChange {

        @Nested
        @DisplayName("유저 속성 값을 넘겨 받으면")
        class ContextWithValues {

            private User user;

            @BeforeEach
            void modelSetUp() {
                User.UserBuilder userBuilder = new User.UserBuilder();
                this.user = userBuilder
                        .name("Jack")
                        .email("jack@email.com")
                        .password("asdf1234")
                        .build();
            }

            @Test
            @DisplayName("유저의 속성을 수정합니다")
            void ItEditsUser() {
                assertThat(this.user.getName()).isEqualTo("Jack");
                assertThat(this.user.getEmail()).isEqualTo("jack@email.com");
                assertThat(this.user.getPassword()).isEqualTo("asdf1234");

                this.user.change(
                        "wilson",
                        "wilson@email.com",
                        "rewq4321"
                );

                assertThat(this.user.getName()).isEqualTo("wilson");
                assertThat(this.user.getEmail()).isEqualTo("wilson@email.com");
                assertThat(this.user.getPassword()).isEqualTo("rewq4321");
            }
        }
    }
}
