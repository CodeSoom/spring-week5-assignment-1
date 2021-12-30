package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("UserTest 클래스")
class UserTest {


    @BeforeEach
    void setUp() {

    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {
        @Test
        @DisplayName("user 생성합니다.")
        void it_creat_user() {
            User user = new User(1L,"삼돌이", "jihwooon@gmail.com", 1234);

            assertThat(user.getId()).isEqualTo(1L);
            assertThat(user.getName()).isEqualTo("삼돌이");
            assertThat(user.getEmail()).isEqualTo("jihwooon@gmail.com");
            assertThat(user.getPassword()).isEqualTo(1234);

        }
    }

}
