package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@DisplayName("UserRegisterService 클래스")
public class UserRegisterServiceTest {
    @Autowired
    private UserRegisterService registerService;


    @Nested
    @DisplayName("execute 메소드는 ")
    class Describe_execute {

        @Nested
        @DisplayName("이름, 이메일, 비밀번호를 인자로 받아 ")
        class Context_normal {

            @Test
            @DisplayName("새로운 유저를 생성한다.")
            void it_creates_new_user() {
                User user = registerService.execute("쥐돌이", "mouse@gmail.com", "1234");

                assertThat(user).isNotNull();
                assertThat(user.getName()).isEqualTo("쥐돌이");
                assertThat(user.getEmail()).isEqualTo("mouse@gmail.com");
                assertThat(user.getPassword()).isEqualTo("1234");
            }
        }

        @Nested
        @DisplayName("필드의 일부가 null이면")
        class Context_null {
            @Test
            @DisplayName("NullPointerException 예외를 던진다.")
            void it_throws_exception() {
                assertThatThrownBy(() -> registerService.execute(null, "mouse@gmail.com", "1234"))
                        .isInstanceOf(NullPointerException.class);

                assertThatThrownBy(() -> registerService.execute("쥐돌이" , null, "1234"))
                        .isInstanceOf(NullPointerException.class);


                assertThatThrownBy(() -> registerService.execute("쥐돌이", "mouse@gmail.com", null))
                        .isInstanceOf(NullPointerException.class);

            }
        }
    }
}
