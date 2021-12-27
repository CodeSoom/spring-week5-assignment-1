package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("User 클래스")
class UserTest {
    @Nested
    @DisplayName("빌더를 사용해 생성자를 만든다면")
    class Describe_create {
        @Test
        @DisplayName("user가 생성된다.")
        void it_return_user() {
            User user = User.builder()
                    .id(1L)
                    .name("코드숨")
                    .password("1234")
                    .email("codesoom@gmail.com")
                    .build();
        }
    }
}
