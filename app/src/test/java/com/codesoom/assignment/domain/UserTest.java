package com.codesoom.assignment.domain;

import com.codesoom.assignment.Exceptions.UserErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class UserTest {

    private String email = "kc@gmail.com";

    private String username = "username";

    private final String password = "12345Passwrod";


    @DisplayName("Email 필드 유효성 검사")
    @Nested
    class Describe_email_validation {

        @DisplayName("null 들어올때")
        @Nested
        class Context_null_email {

            @DisplayName("llegalArgumentException를 던진다")
            @Test
            void it_throws_illegal_argument() {
                Throwable throwable = catchThrowable(() -> User.builder().username("usernaem").email(null).password("password").build());
                assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
                assertThat(throwable).hasMessage(UserErrorMessage.EMAIL_NULL);
            }
        }


        @DisplayName("공백이 들어올때")
        @Nested
        class Context_empty_email {

            @DisplayName("llegalArgumentException를 던진다")
            @Test
            void it_throws_illegal_argument() {
                Throwable throwable = catchThrowable(() -> User.builder().username("username").email("").password("password").build());
                assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
                assertThat(throwable).hasMessage(UserErrorMessage.EMAIL_BLANK);
            }
        }


    }

    @DisplayName("Username 필드 유효성 검사")
    @Nested
    class Describe_username {

        @DisplayName("null 들어올때")
        @Nested
        class Context_no_username {

            @DisplayName("IllegalArgumentException를 던진다")
            @Test
            void it_throws_illegal_argument() {
                Throwable throwable = catchThrowable(() -> User.builder().username(null).email("email@gmail.com").password("password").build());
                assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
                assertThat(throwable).hasMessage(UserErrorMessage.USERNAME_NULL);

            }
        }

        @DisplayName("공백으로 들어올때")
        @Nested
        class Context_empty_username {

            @DisplayName("llegalArgumentException를 던진다")
            @Test
            void it_throws_illegal_argument() {
                Throwable throwable = catchThrowable(() -> User.builder().username("").email("email@gmail.com").password("password").build());
                assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
                assertThat(throwable).hasMessage(UserErrorMessage.USERNAME_BLANK);
            }
        }

    }

    @DisplayName("Password 필드 유효성 검사")
    @Nested
    class Describe_password {

        @DisplayName("null 들어올때")
        @Nested
        class Context_null_Password {

            @DisplayName("llegalArgumentException를 던진다")
            @Test
            void it_throws_illegal_argument() {
                Throwable throwable = catchThrowable(() -> User.builder().username("usernaem").email("email@mail.com").password(null).build());
                assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
                assertThat(throwable).hasMessage(UserErrorMessage.PASSWORD_NULL);
            }
        }

        @DisplayName("공백으로 들어올때")
        @Nested
        class Context_EmptyL_Password {

            @DisplayName("llegalArgumentException를 던진다")
            @Test
            void it_throws_illegal_argument() {
                Throwable throwable = catchThrowable(() -> User.builder().username("usernaem").email("email@mail.com").password("").build());
                assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
                assertThat(throwable).hasMessage(UserErrorMessage.PASSWORD_BLANK);
            }
        }
    }


}
