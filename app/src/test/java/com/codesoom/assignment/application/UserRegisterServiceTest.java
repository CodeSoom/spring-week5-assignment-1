package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@DisplayName("UserService 클래스")
public class UserRegisterServiceTest {
    @Autowired
    private UserRegisterService service;


    @Nested
    @DisplayName("register 메소드는 ")
    class Describe_register {

        @Nested
        @DisplayName("이름, 이메일, 비밀번호를 인자로 받아 ")
        class Context_normal {

            @Test
            @DisplayName("새로운 유저를 생성한다.")
            void it_creates_new_user() {
                User user = service.execute("쥐돌이", "mouse@gmail.com", "1234");

                assertThat(user).isNotNull();
                assertThat(user.getName()).isEqualTo("쥐돌이");
                assertThat(user.getEmail()).isEqualTo("mouse@gmail.com");
                assertThat(user.getPassword()).isEqualTo("1234");
            }
        }
//        @Nested
//        @DisplayName("이름, 이메일, 비밀번호를 인자로 받아 ")
//        class Context_duplicate_email {
//
//            @BeforeEach
//            void setUp() {
//                service.register("쥐돌이", "
    }
}
