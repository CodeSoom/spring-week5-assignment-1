package com.codesoom.assignment.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("UserService 클래스")
public class UserServiceTest {
    private UserService service;

    @BeforeEach
    void setUp() {
        this.service = new UserService();
    }

    @Nested
    @DisplayName("register 메소드는 ")
    class Describe_register {

        @Nested
        @DisplayName("이름, 이메일, 비밀번호를 인자로 받아 ")
        class Context_normal {

            @Test
            @DisplayName("새로운 유저를 생성한다.")
            void it_creates_new_user() {
                service.register("쥐돌이", "mouse@gmail.com", "1234");
            }
        }
    }
}
