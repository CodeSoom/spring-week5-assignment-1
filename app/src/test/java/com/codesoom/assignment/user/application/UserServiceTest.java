package com.codesoom.assignment.user.application;

import com.codesoom.assignment.user.adapter.out.persistence.FakeInMemoryUserPersistenceAdapter;
import com.codesoom.assignment.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.codesoom.assignment.support.UserFixture.USER_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("UserService 테스트")
class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUpVariable() {
        FakeInMemoryUserPersistenceAdapter fakeUserRepository = new FakeInMemoryUserPersistenceAdapter();
        userService = new UserService(fakeUserRepository);
    }

    /*
      CreateUser 메서드는
       - 새로운 회원이 주어지면
            - 회원을 저장하고 리턴한다.
            - 회원이 1 증가한다.
       - null이 주어지면
            - 예외를 던진다
     */
    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class createUser_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 새로운_회원이_주어지면 {
            @Test
            @DisplayName("회원을 저장하고 리턴한다")
            void it_returns_user() {
                User user = userService.createUser(USER_1.생성_요청_데이터_생성());

                assertThat(user.getName()).isEqualTo(USER_1.NAME());
                assertThat(user.getEmail()).isEqualTo(USER_1.EMAIL());
                assertThat(user.getPassword()).isEqualTo(USER_1.PASSWORD());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class null이_주어지면 {
            @Test
            @DisplayName("예외를 던진다")
            void it_returns_null_pointer_exception() {
                assertThatThrownBy(() -> userService.createUser(null))
                        .isInstanceOf(NullPointerException.class);
            }
        }
    }
}
