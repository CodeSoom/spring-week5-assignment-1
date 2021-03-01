package com.codesoom.assignment.User.application;

import com.codesoom.assignment.common.exception.UserNotFoundException;
import com.codesoom.assignment.user.application.UserService;
import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.domain.UserRepository;
import com.codesoom.assignment.user.dto.UserData;
import com.codesoom.assignment.user.dto.UserUpdate;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceTest {
    private UserService userService;

    private final UserRepository userRepository = mock(UserRepository.class);

    private UserData userData;
    private User user;
    private UserUpdate userUpdate;

    private final Long EXISTING_ID = 1L;
    private final Long NOT_EXISTING_ID = -999L;
    private final String NAME = "올리브";
    private final String EMAIL = "olive@gmail.com";
    private final String PASSWORD = "olivePW";

    private final String NEW_NAME = "새로운올리브";
    private final String NEW_EMAIL = "newOlive@gmail.com";
    private final String NEW_PASSWORD = "newOlivePW";

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        userService = new UserService(mapper, userRepository);
    }

    @Nested
    @DisplayName("createUser()은")
    class Describe_createUser {

        @Nested
        @DisplayName("새로운 회원 정보가 주어지면")
        class Context_add_new_user {
            @BeforeEach
            void setUp() {
                userData = UserData.builder()
                        .name(NAME)
                        .email(EMAIL)
                        .password(PASSWORD)
                        .build();

                given(userRepository.save(any(User.class))).will(invocation -> {
                    User source = invocation.getArgument(0);
                    return User.builder()
                            .name(source.getName())
                            .email(source.getEmail())
                            .password(source.getPassword())
                            .build();
                });
            }

            @Test
            @DisplayName("해당 정보를 저장하고 그 회원을 리턴한다.")
            void it_returns_savedNewUser() {
                user = userService.createUser(userData);

                verify(userRepository).save(any(User.class));

                assertThat(user.getName()).isEqualTo(NAME);
                assertThat(user.getEmail()).isEqualTo(EMAIL);
                assertThat(user.getPassword()).isEqualTo(PASSWORD);
            }
        }
    }

    @Nested
    @DisplayName("updateUser()은")
    class Describe_updateUser {

        @Nested
        @DisplayName("등록된 회원의 id와 수정할 회원 정보가 주어지면")
        class Context_contains_target_id {
            @BeforeEach
            void setUp() {
                user = User.builder()
                        .id(EXISTING_ID)
                        .name(NAME)
                        .email(EMAIL)
                        .password(PASSWORD)
                        .build();

                given(userRepository.findById(EXISTING_ID))
                        .willReturn(Optional.of(user));

                userUpdate = UserUpdate.builder()
                        .name(NEW_NAME)
                        .email(NEW_EMAIL)
                        .password(NEW_PASSWORD)
                        .build();
            }

            @Test
            @DisplayName("회원 정보를 수정한 뒤 리턴한다.")
            void it_returns_updatedUser() {
                user = userService.updateUser(EXISTING_ID, userUpdate);

                assertThat(user.getName()).isEqualTo(NEW_NAME);
                assertThat(user.getEmail()).isEqualTo(NEW_EMAIL);
                assertThat(user.getPassword()).isEqualTo(NEW_PASSWORD);
            }
        }

        @Nested
        @DisplayName("등록되지 않은 회원의 id가 주어지면")
        class Context_not_contains_target_id {

            @BeforeEach
            void setUp() {
                given(userRepository.findById(NOT_EXISTING_ID))
                        .willReturn(Optional.empty());

                userUpdate = UserUpdate.builder()
                        .name(NEW_NAME)
                        .email(NEW_EMAIL)
                        .password(NEW_PASSWORD)
                        .build();
            }

            @Test
            @DisplayName("예외를 던진다.")
            void it_throws_exception () {
                assertThatThrownBy(() -> userService.updateUser(NOT_EXISTING_ID, userUpdate))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteUser()은")
    class Describe_deleteUser {

        @Nested
        @DisplayName("등록된 회원의 id가 주어지면")
        class Context_contains_target_id {
            @BeforeEach
            void setUp() {
                user = User.builder()
                        .id(EXISTING_ID)
                        .name(NAME)
                        .email(EMAIL)
                        .password(PASSWORD)
                        .build();

                given(userRepository.findById(EXISTING_ID))
                        .willReturn(Optional.of(user));
            }

            @Test
            @DisplayName("그 id에 해당하는 회원 정보를 삭제한다.")
            void it_deletes_user() {
                userService.deleteUser(EXISTING_ID);

                verify(userRepository).delete(any(User.class));
            }
        }

        @Nested
        @DisplayName("등록되지 않은 회원이 id가 주어지면")
        class Context_not_contains_target_id {

            @BeforeEach
            void setUp() {
                given(userRepository.findById(NOT_EXISTING_ID))
                        .willReturn(Optional.empty());
            }

            @Test
            @DisplayName("예외를 던진다.")
            void it_throws_exception() {
                assertThatThrownBy(() -> userService.updateUser(NOT_EXISTING_ID, userUpdate))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }

    }

}
