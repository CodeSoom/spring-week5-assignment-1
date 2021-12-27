package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("UserService 클래스")
class UserServiceTest {
    private UserService userService;

    private UserRepository userRepository = mock(UserRepository.class);

    private static final String USER_NAME = "코드숨";
    private static final String USER_PASSWORD = "1234";
    private static final String USER_EMAIL = "codesoom@gmail.com";

    private static final String NEW_NAME = "스프링";
    private static final String NEW_PASSWORD = "5678";
    private static final String NEW_EMAIL = "spring@gmail.com";

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        userService = new UserService(mapper, userRepository);
    }

    @Nested
    @DisplayName("getUsers 메소드는")
    class Describe_getUsers {
        @Nested
        @DisplayName("등록된 사용자가 있으면")
        class Context_has_user {
            @BeforeEach
            void setUp() {
                User user = User.builder()
                        .id(1L)
                        .name(USER_NAME)
                        .password(USER_PASSWORD)
                        .email(USER_EMAIL)
                        .build();

                given(userRepository.findAll()).willReturn(List.of(user));
            }

            @Test
            @DisplayName("전체 목록을 리턴한다.")
            void it_return_users() {
                List<User> users = userService.getUsers();

                assertThat(users).isNotEmpty();
            }
        }
        @Nested
        @DisplayName("등록된 사용자가 없으면")
        class Context_hasnot_user {
            @BeforeEach
            void setUp() {
                given(userRepository.findAll()).willReturn(List.of());
            }

            @Test
            @DisplayName("빈 목록을 리턴한다.")
            void it_return_users() {
                assertThat(userService.getUsers()).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("getUser 메소드는")
    class Describe_getUser {
        @Nested
        @DisplayName("id에 해당하는 사용자가 있으면")
        class Context_With_Exist_userId {
            @BeforeEach
            void setUp() {
                User user = User.builder()
                        .id(1L)
                        .name(USER_NAME)
                        .password(USER_PASSWORD)
                        .email(USER_EMAIL)
                        .build();

                given(userRepository.findById(1L)).willReturn(Optional.of(user));
            }

            @Test
            @DisplayName("사용자 정보를 리턴한다.")
            void it_return_user() {
                User user = userService.getUser(1L);

                assertThat(user).isNotNull();
                assertThat(user.getName()).isEqualTo(USER_NAME);
            }
        }
        @Nested
        @DisplayName("등록된 사용자가 없으면")
        class Context_hasnot_user {
            @Test
            @DisplayName("id에 해당하는 사용자를 찾을 수 없다는 예외를 던진다.")
            void it_return_users() {
                assertThatThrownBy(() -> userService.getUser(1000L))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
