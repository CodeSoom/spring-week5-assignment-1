package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.UserNotFoundException;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    private UserRepository userRepository = mock(UserRepository.class);
    private UserService userService;

    private static final Long USER_ID = 1L;
    private static final String USER_NAME = "김태우";
    private static final String USER_PASSWORD = "1234";
    private static final String USER_EMAIL = "xodnzlzl1597@gmail.com";

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        userService = new UserService(mapper, userRepository);

        User user = User.builder()
                .id(USER_ID)
                .name(USER_NAME)
                .password(USER_PASSWORD)
                .email(USER_EMAIL)
                .build();

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        given(userRepository.save(any(User.class))).will(invocation -> {
            User source = invocation.getArgument(0);
            return User.builder()
                    .id(2L)
                    .name(source.getName())
                    .password(source.getPassword())
                    .email(source.getEmail())
                    .build();
        });
    }

    @Nested
    @DisplayName("createUser 메서드는")
    class Describe_createUser {
        @Nested
        @DisplayName("등록할 user가 주어진다면")
        class Context_with_user {

            @Test
            @DisplayName("user을 생성하고 리턴합니다")
            void it_return_user() {
                UserData userData = UserData.builder()
                        .name(USER_NAME)
                        .password(USER_PASSWORD)
                        .email(USER_EMAIL)
                        .build();

                User user = userService.createUser(userData);

                assertThat(user.getName()).isEqualTo(USER_NAME);
                assertThat(user.getPassword()).isEqualTo(USER_PASSWORD);
                assertThat(user.getEmail()).isEqualTo(USER_EMAIL);
            }
        }
    }

    @Nested
    @DisplayName("updateUser 메서드는")
    class Describe_updateUser {
        @Nested
        @DisplayName("user의 id가 주어진다면")
        class Context_with_userId {

            @Test
            @DisplayName("user을 수정하고 리턴한다.")
            void it_return_user() {
                UserData userData = UserData.builder()
                        .name("코드숨")
                        .password("1597")
                        .email("codesoom@gmail.com")
                        .build();

                User user = userService.updateUser(1L, userData);

                assertThat(user.getName()).isEqualTo("코드숨");
                assertThat(user.getPassword()).isEqualTo("1597");
                assertThat(user.getEmail()).isEqualTo("codesoom@gmail.com");
            }
        }

        @Nested
        @DisplayName("등록되지 않은 User의 id가 주어진다면")
        class Context_withOut_userId {

            @Test
            @DisplayName("User를 찾을 수 없다는 예외를 던진다.")
            void it_return_user() {
                UserData source = new UserData();
                source.setName("코드숨");

                assertThatThrownBy(() -> userService.updateUser(100L, source)).isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_deleteUser {
        @Nested
        @DisplayName("user의 id가 주어진다면")
        class Context_with_userId {

            @Test
            @DisplayName("user를 삭제하고 리턴한다")
            void it_return_user() {
                userService.deleteUser(USER_ID);

                verify(userRepository).delete(any(User.class));
            }
        }

        @Nested
        @DisplayName("등록되지 않은 User의 id가 주어진다면")
        class Context_withOut_userId {

            @Test
            @DisplayName("User를 찾을 수 없다는 예외를 던진다.")
            void it_return_error() {
                assertThatThrownBy(() -> userService.deleteUser(100L)).isInstanceOf(UserNotFoundException.class);

                verify(userRepository).findById(100L);
            }
        }
    }
}
