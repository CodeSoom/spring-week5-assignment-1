package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserService 테스트")
class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository;

    private final Long EXISTED_ID = 1L;
    private final Long NOT_EXISTED_ID = 2L;

    private final String CREATE_USER_NAME = "createdName";
    private final String CREATE_USER_EMAIL = "createdEmail";
    private final String CREATE_USER_PASSWORD = "createdPassword";

    private final String UPDATE_USER_NAME = "updatedName";
    private final String UPDATE_USER_EMAIL = "updatedEmail";
    private final String UPDATE_USER_PASSWORD = "updatedPassword";

    private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();
    private List<User> users;
    private User setUpUser;


    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);

        setUpUser = User.builder()
                .id(EXISTED_ID)
                .name(CREATE_USER_NAME)
                .email(CREATE_USER_EMAIL)
                .password(CREATE_USER_PASSWORD)
                .build();

        users = List.of(setUpUser);
    }

    @Nested
    @DisplayName("getUser 메서드는")
    class Describe_getUser {
        @Nested
        @DisplayName("만약 저장되어 있는 사용자의 아이디가 주어진다면")
        class Context_WithExistedId {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("주어진 아이디에 해당하는 사용자를 리턴한다")
            void itReturnsUser() {
                given(userRepository.findById(givenExistedId)).willReturn(Optional.of(setUpUser));

                User user = userService.getUser(givenExistedId);

                assertThat(user.getId()).isEqualTo(setUpUser.getId());

                verify(userRepository).findById(givenExistedId);
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않은 사용자의 아이디가 주어진다면")
        class Context_WithNotExistedId {
            private final Long givenNotExistedId = NOT_EXISTED_ID;

            @Test
            @DisplayName("유저를 찾을 수 없다는 메세지를 리턴한다")
            void itReturnsUserNotFoundMessage() {
                given(userRepository.findById(givenNotExistedId))
                        .willThrow(new UserNotFoundException(givenNotExistedId));

                assertThatThrownBy(() -> userRepository.findById(givenNotExistedId))
                        .isInstanceOf(UserNotFoundException.class)
                        .hasMessageContaining("User not found");

                verify(userRepository).findById(givenNotExistedId);
            }
        }
    }

    @Nested
    @DisplayName("createUser 메서드는")
    class Describe_createUser {
        @Nested
        @DisplayName("만약 사용자가 주어진다면")
        class Context_WithUser {
            private UserData userSource;
            private User savedUser;

            @BeforeEach
            void setUp() {
                userSource = UserData.builder()
                        .name(CREATE_USER_NAME)
                        .email(CREATE_USER_EMAIL)
                        .password(CREATE_USER_PASSWORD)
                        .build();

                savedUser = mapper.map(userSource, User.class);
            }

            @Test
            @DisplayName("주어진 사용자를 저장하고 저장된 사용자를 리턴한다")
            void itSavesUserAndReturnsSavedUser() {
                given(userRepository.save(any(User.class))).willReturn(savedUser);

                User createdUser = userService.createUser(userSource);

                assertThat(createdUser.getName()).isEqualTo(userSource.getName());
                assertThat(createdUser.getEmail()).isEqualTo(userSource.getEmail());
                assertThat(createdUser.getPassword()).isEqualTo(userSource.getPassword());

                verify(userRepository).save(any(User.class));
            }
        }
    }

    @Nested
    @DisplayName("updateUser 메서드는")
    class Describe_updateUser {
        @Nested
        @DisplayName("만약 저장되어 있는 사용자의 아이디와 수정 할 사용자가 주어진다면")
        class Context_WithExistedIdAndUser {
            private final Long givenExistedId = EXISTED_ID;
            private UserData userSource;

            @BeforeEach
            void setUp() {
                userSource = UserData.builder()
                        .name(UPDATE_USER_NAME)
                        .email(UPDATE_USER_EMAIL)
                        .password(UPDATE_USER_PASSWORD)
                        .build();
            }

            @Test
            @DisplayName("사용자를 수정하고 수정된 사용자를 리턴한다")
            void itUpdatesUserAndReturnsUpdatedUser() {
                given(userRepository.findById(givenExistedId)).willReturn(Optional.of(setUpUser));

                User updatedUser = userService.updateUser(givenExistedId, userSource);

                assertThat(updatedUser.getName()).isEqualTo(userSource.getName());
                assertThat(updatedUser.getEmail()).isEqualTo(userSource.getEmail());
                assertThat(updatedUser.getPassword()).isEqualTo(userSource.getPassword());
                
                verify(userRepository).findById(givenExistedId);
            }
        }
    }

    @Nested
    @DisplayName("deleteUser 메서드는")
    class Describe_delete {
        @Nested
        @DisplayName("만약 저장되어 있는 사용자의 아이디가 주어진다묜")
        class Context_WithExistedId {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("주어진 아이디에 해당하는 사용자를 삭제하고 삭제된 사용자를 리턴한다")
            void itDeletesUserAndReturnsDeletedUser() {
                given(userRepository.findById(givenExistedId)).willReturn(Optional.of(setUpUser));

                User user = userService.deleteUser(givenExistedId);

                assertThat(user.getId()).isEqualTo(setUpUser.getId());
                
                verify(userRepository).findById(givenExistedId);
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않은 사용자의 아이디가 주어진다면")
        class Context_WithNotExistedId {
            private final Long givenNotExistedId = NOT_EXISTED_ID;

            @Test
            @DisplayName("사용자를 찾을 수 없다는 메세지를 리턴한다")
            void itReturnsUserNotFoundMessage() {
                given(userRepository.findById(givenNotExistedId))
                        .willThrow(new UserNotFoundException(givenNotExistedId));

                assertThatThrownBy(() -> userService.deleteUser(givenNotExistedId))
                        .isInstanceOf(UserNotFoundException.class)
                        .hasMessageContaining("User not found");

                verify(userRepository).findById(givenNotExistedId);
            }
        }
    }
}
