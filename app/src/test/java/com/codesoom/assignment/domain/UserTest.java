package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User 엔티티에서")
class UserTest {
    private final static String USERNAME = "username1";
    private final static String EMAIL = "example@example.com";
    private final static String PASSWORD = "password";

    @Nested
    @DisplayName("from() 정적 팩토리 메소드는")
    class Describe_of_static_factory_method {

        @Nested
        @DisplayName("매개 변수를 받아서")
        class Context_with_dto {
            private final UserData userData = UserData.builder()
                    .username(USERNAME)
                    .email(EMAIL)
                    .password(PASSWORD)
                    .build();

            @Test
            @DisplayName("User 인스턴스로 변환할 수 있다")
            void it_return_user() {
                User user = User.from(userData);

                assertThat(user).isInstanceOf(User.class);
            }
        }
    }
}
