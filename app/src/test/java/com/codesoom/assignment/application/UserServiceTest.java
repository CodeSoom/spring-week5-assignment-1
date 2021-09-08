package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);

        given(userRepository.existsById(1L))
            .willReturn(Boolean.TRUE);

        given(userRepository.existsById(9999L))
            .willReturn(Boolean.FALSE);

        given(userRepository.save(any(User.class)))
            .will(invocation -> invocation.getArgument(0));
    }

    @Test
    void create() {
        User user = userService.createUser(User.builder()
            .name("name")
            .email("email")
            .password("password")
            .build());

        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getEmail()).isEqualTo("email");
        assertThat(user.getPassword()).isEqualTo("password");
    }

    @Nested
    @DisplayName("updateUser 메서드는")
    class Describe_updateUser {

        @Nested
        @DisplayName("존재하는 id인 경우")
        class Context_existId {

            private Long existId;

            @BeforeEach
            void setUp() {
                assertThat(userRepository.existsById(1L))
                    .isTrue();

                existId = 1L;
            }

            @Test
            @DisplayName("수정한 유저를 리턴한다")
            void it_returns_updated_user() {
                User source = User.builder()
                    .name("name2")
                    .email("email2")
                    .password("password2")
                    .build();
                User updatedUser = userService.updateUser(existId, source);

                assertThat(updatedUser.getName()).isEqualTo("name2");
                assertThat(updatedUser.getEmail()).isEqualTo("email2");
                assertThat(updatedUser.getPassword()).isEqualTo("password2");

                verify(userRepository).findById(existId);
                verify(userRepository).save(source);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 id인 경우")
        class Context_notExistId {

            private Long notExistId;

            @BeforeEach
            void setUp() {
                assertThat(userRepository.existsById(9999L))
                    .isFalse();

                notExistId = 9999L;
            }

            @Test
            @DisplayName("에러를 던진다")
            void it_throws() {
                User source = User.builder().build();

                assertThatThrownBy(() -> {
                    userService.updateUser(notExistId, source);
                }).isInstanceOf(UserNotFoundException.class);

                verify(userRepository).findById(notExistId);
                verify(userRepository, never()).save(source);
            }
        }
    }
}
