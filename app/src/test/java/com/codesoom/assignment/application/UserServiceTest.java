package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.infra.JpaUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@DisplayName("UserService 클래스")
public class UserServiceTest {
    @Autowired
    private UserService service;


    @Nested
    @DisplayName("register 메소드는 ")
    class Describe_register {

        @Nested
        @DisplayName("이름, 이메일, 비밀번호를 인자로 받아 ")
        class Context_normal {

            @Test
            @DisplayName("새로운 유저를 생성한다.")
            void it_creates_new_user() {
                User user = service.register("쥐돌이", "mouse@gmail.com", "1234");

                assertThat(user).isNotNull();
                assertThat(user.getName()).isEqualTo("쥐돌이");
                assertThat(user.getEmail()).isEqualTo("mouse@gmail.com");
                assertThat(user.getPassword()).isEqualTo("1234");
            }
        }
    }
}
