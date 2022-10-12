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


    @DisplayName("User class Email validations")
    @Nested
    class Describe_email_validation {

        @DisplayName("if null is given")
        @Nested
        class Context_null_email {

            @DisplayName("throws illegal argument exception if null was passed")
            @Test
            void it_throws_illegal_argument() {
                Throwable throwable = catchThrowable(() -> User.builder().username("usernaem").email(null).password("password").build());
                assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
                assertThat(throwable).hasMessage(UserErrorMessage.EMAIL_NOT_NULL);
            }
        }


        @DisplayName("if an empty string is given")
        @Nested
        class Context_empty_email {

            @DisplayName("throws illegal argument exception")
            @Test
            void it_throws_illegal_argument() {
                Throwable throwable = catchThrowable(() -> User.builder().username("username").email("").password("password").build());
                assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
                assertThat(throwable).hasMessage(UserErrorMessage.EMAIL_NOT_BLANK);
            }
        }


    }

    @DisplayName("Username validation")
    @Nested
    class Describe_username {

        @DisplayName("if null is given")
        @Nested
        class Context_no_username {

            @DisplayName("throws illegal argument exception")
            @Test
            void it_throws_illegal_argument() {
                Throwable throwable = catchThrowable(() -> User.builder().username(null).email("email@gmail.com").password("password").build());
                assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
                assertThat(throwable).hasMessage(UserErrorMessage.USERNAME_NOT_NULL);

            }
        }

        @DisplayName("if empty username is given")
        @Nested
        class Context_empty_username {

            @DisplayName("throws illegal argument exception")
            @Test
            void it_throws_illegal_argument() {
                Throwable throwable = catchThrowable(() -> User.builder().username(null).email("email@gmail.com").password("password").build());
                assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
                assertThat(throwable).hasMessage(UserErrorMessage.USERNAME_NOT_BLANK);
            }
        }

    }


}
