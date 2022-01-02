package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
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

    final String givenName = "Hyuk";
    final String givenPassword = "!234";
    final String givenEmail = "pjh0819@naver.com";

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class User를_생성하려면 {

        @Test
        void builder로_생성한다() {
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

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class User를_삭제하려면 {

        User givenUser;

        @BeforeEach
        void prepare() {
            givenUser = User.builder()
                    .name(givenName)
                    .password(givenPassword)
                    .email(givenEmail)
                    .build();
        }

        @Test
        void destory_메서드를_호출한다() {
            givenUser.destory();

            assertThat(givenUser.isDeleted()).isTrue();
        }
    }
}
