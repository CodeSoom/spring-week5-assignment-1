package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    private final User user;


    public UserTest() {
        user = new User();
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {
        @Nested
        @DisplayName("만약 정상적인 유저 정보를 받는다면, ")
        class Context_valid_user {

            @Test
            @DisplayName("업데이트된 유저를 반환한다.")
            void it_return_updated_user() {
                User source = new User();

                user.update(source);

                assertThat(user).usingRecursiveComparison()
                        .isEqualTo(source);
            }
        }
    }
}
