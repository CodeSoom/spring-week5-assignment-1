package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("UserService 클래스")
class UserServiceTest {

    private UserService userService;

    private UserRepository userRepository = mock(UserRepository.class);

    private List<User> users;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);

        user1 = User.builder()
                .id(1L)
                .name("이름")
                .email("이메일")
                .password("password")
                .build();

        user2 = User.builder()
                .id(2L)
                .name("이름2")
                .email("이메일2")
                .password("password2")
                .build();
    }

    @Nested
    @DisplayName("getUsers")
    class Describe_getUsers {
        @Nested
        @DisplayName("저장된 회원이 여러명 있다면")
        class Context_with_users {
            @BeforeEach
            void setUp() {
                users = List.of(user1, user2);

                given(userRepository.findAll())
                        .willReturn(users);
            }

            @Test
            @DisplayName("모든 회원 목록을 리턴한다.")
            void it_returns_all_user_list() {
                assertThat(userService.getUsers()).hasSize(2);
            }
        }
    }

}
