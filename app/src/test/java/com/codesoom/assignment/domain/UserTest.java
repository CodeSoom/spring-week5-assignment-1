package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Nested
    @DisplayName("User.builder는")
    class Describe_create {
        @Nested
        @DisplayName("사용자를 생성한다면")
        class Context_with_product {

            @Test
            @DisplayName("User가 생성된다.")
            void it_return_user() {
                User user = User.builder()
                        .id(1L)
                        .name("김태우")
                        .password("1234")
                        .email("xodnzlzl1597@gmail.com")
                        .build();

                assertThat(user.getId()).isEqualTo(1L);
                assertThat(user.getName()).isEqualTo("김태우");
                assertThat(user.getPassword()).isEqualTo("1234");
                assertThat(user.getEmail()).isEqualTo("xodnzlzl1597@gmail.com");
            }
        }
    }
}
