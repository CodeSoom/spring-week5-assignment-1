package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("User 클래스")
class UserTest {

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class User_Builder는 {

        final String givenName = "Hyuk";
        final String givenPassword = "!234";
        final String givenEmail = "pjh0819@naver.com";

        @Test
        void User_객체를_생성한다() {
            User user = User.builder()
                            .name(givenName)
                            .password(givenPassword)
                            .email(givenEmail)
                            .build();

            assertThat(user).isNotNull();
            assertThat(user.getName()).isEqualTo(givenName);
            assertThat(user.getPassword()).isEqualTo(givenPassword);
            assertThat(user.getEmail()).isEqualTo(givenEmail);
        }
    }
}
