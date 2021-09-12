package com.codesoom.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import static com.codesoom.assignment.constants.UserConstants.NAME;
import static com.codesoom.assignment.constants.UserConstants.EMAIL;
import static com.codesoom.assignment.constants.UserConstants.PASSWORD;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Nested
@DisplayName("User 클래스")
public class UserTest {
    private final User user;

    public UserTest() {
        user = User.builder()
            .name(NAME)
            .email(EMAIL)
            .password(PASSWORD)
            .build();
    }

    @Nested
    @DisplayName("update 메서드는")
    public class Describe_update {
        private User source;

        private void subject() {
            user.update(source);
        }

        @ParameterizedTest(name = "주어진 데이터를 업데이트한다.")
        @CsvSource(
            value = {
                "null, 'name', 'password'",
                "'email', null, 'password",
                "'email', 'name', null"
            },
            nullValues = {"null"}
        )
        public void it_updates_a_user(
            final String email, final String name, final String password
        ) {
            source = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();


            subject();

            assertThat(user)
                .matches(output -> email != null ? email.equals(output.getEmail()) : EMAIL.equals(output.getEmail()))
                .matches(output -> name != null ? name.equals(output.getName()) : NAME.equals(output.getName()))
                .matches(output -> password != null ? password.equals(output.getPassword()) : PASSWORD.equals(output.getPassword()));
        }
    }
}
