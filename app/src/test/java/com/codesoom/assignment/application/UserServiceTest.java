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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DataJpaTest
@DisplayName("UserService의")
class UserServiceTest {
    private final Long givenSavedId = 1L;
    private final Long givenUnsavedId = 100L;
    private final String givenName = "newoo";
    private final String givenEmail = "newoo@codesoom.com";
    private final String givenPassword = "codesoom123";

    private final String givenChangedName = "newoo2";
    private final String givenChangedEmail = "newoo2@codesoom.com";
    private final String givenChangedPassword = "codesoom789";

    private UserService userService;
    private UserRepository userRepository = mock(UserRepository.class);
    private User user;
    private UserData userData;

    @BeforeEach
    void setUp() {
        final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        userService = new UserService(mapper, userRepository);

        userData = UserData.builder()
                .id(givenSavedId)
                .name(givenName)
                .email(givenEmail)
                .password(givenPassword)
                .build();

        user = mapper.map(userData, User.class);
    }

    void assertUser(User user) {
        assertThat(user.getClass()).isEqualTo(User.class);
        assertThat(user.getName()).isEqualTo(givenName);
        assertThat(user.getEmail()).isEqualTo(givenEmail);
        assertThat(user.getPassword()).isEqualTo(givenPassword);
    }

    @Nested
    @DisplayName("getUsers 메서드는")
    class Describe_getUsers {
        private List<User> subject() {
            return userService.getUsers();
        }

        @Nested
        @DisplayName("저장된 user가 없다면")
        class Context_without_any_saved_user {
            @BeforeEach
            void setEmptyList() {
                given(userRepository.findAll())
                        .willReturn(List.of());
            }

            @Test
            @DisplayName("비어있는 리스트를 리턴한다.")
            void it_return_empty_list() {
                assertThat(subject()).isEmpty();
            }
        }

        @Nested
        @DisplayName("저장된 user가 있다면")
        class Context_with_any_saved_user {
            private List<User> givenUserList;

            @BeforeEach
            void setSavedUser() {
                givenUserList = List.of(user);

                given(userRepository.findAll())
                        .willReturn(givenUserList);
            }

            @Test
            @DisplayName("user 리스트를 리턴한다.")
            void it_return_user_list() {
                assertThat(subject()).isEqualTo(givenUserList);
            }
        }
    }

    @Nested
    @DisplayName("getUser 메소드는")
    class Describe_getUser {
        private Long givenId;

        private User subject(Long id) {
            return userService.getUser(givenId);
        }

        @Nested
        @DisplayName("저장된 user의 id를 가지고 있다면")
        class Context_with_saved_id {
            private User savedUser;

            @BeforeEach
            void setSavedId() {
                givenId = givenSavedId;

                given(userRepository.findById(givenId))
                        .willReturn(Optional.of(user));
            }

            @Test
            @DisplayName("user를 리턴한다.")
            void it_return_user() {
                savedUser = subject(givenId);

                verify(userRepository).findById(givenId);

                assertUser(savedUser);
            }
        }

        @Nested
        @DisplayName("저장되지 않은 user를 찾으려고하면")
        class Context_when_find_unsaved_user {
            @BeforeEach
            void setUnsavedId() {
                givenId = givenUnsavedId;
            }

            @Test
            @DisplayName("user를 찾을 수 없다는 exception을 던진다.")
            void it_throw_user_not_found_exception() {
                assertThatThrownBy(
                        () -> subject(givenId),
                        "user를 찾을 수 없다는 예외를 던져야 합니다."
                ).isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("createUser 메소드는")
    class Describe_createUser {
        private User created;

        private User subject(UserData userData) {
            return userService.createUser(userData);
        }

        @BeforeEach
        void setGivenUser() {
            given(userRepository.save(any(User.class)))
                    .will(invocation -> {
                        return invocation.getArgument(0);
                    });
        }

        @Test
        @DisplayName("user를 추가하고, 추가된 user를 리턴한다.")
        void it_create_user_and_return_created_user() {
            created = subject(userData);

            verify(userRepository).save(any(User.class));

            assertUser(created);
        }
    }

    @Nested
    @DisplayName("updateUser 메소드는")
    class Describe_updateUser {
        private Long givenId;
        private User modifying;
        private UserData modifyingUserData;

        private User subject(UserData userData) {
            return userService.updateUser(userData);
        }

        private void setModifyingUserData() {
            modifyingUserData = UserData.builder()
                    .id(givenId)
                    .name(givenChangedName)
                    .email(givenChangedEmail)
                    .password(givenChangedPassword)
                    .build();
        }

        private void setModifyingUserByMapper() {
            final Mapper mapper = DozerBeanMapperBuilder.buildDefault();
            modifying = mapper.map(modifyingUserData, User.class);
        }

        @Nested
        @DisplayName("저장된 user의 id를 가지고 있다면")
        class Context_with_saved_user_id {
            private User modified;

            @BeforeEach
            void setSavedId() {
                givenId = givenSavedId;

                setModifyingUserData();
                setModifyingUserByMapper();

                given(userRepository.findById(givenId))
                        .willReturn(Optional.of(modifying));
            }

            @Test
            @DisplayName("user를 수정하고, 수정된 user를 리턴한다.")
            void it_modified_user_and_return_modified_user() {
                modified = subject(modifyingUserData);

                assertThat(modified.getClass()).isEqualTo(User.class);
                assertThat(modified.getName()).isEqualTo(givenChangedName);
                assertThat(modified.getEmail()).isEqualTo(givenChangedEmail);
                assertThat(modified.getPassword()).isEqualTo(givenChangedPassword);
            }
        }

        @Nested
        @DisplayName("저장되지 않은 user를 수정하려고하면")
        class Context_when_update_unsaved_user {
            @BeforeEach
            void setUnsavedId() {
                givenId = givenUnsavedId;

                setModifyingUserData();
                setModifyingUserByMapper();

                given(userRepository.findById(givenId))
                        .willThrow(UserNotFoundException.class);
            }

            @Test
            @DisplayName("user를 찾을 수 없다는 exception을 던진다.")
            void it_throw_user_not_found_exception() {
                assertThatThrownBy(
                        () -> subject(modifyingUserData),
                        "user를 찾을 수 없다는 예외를 던져야 합니다."
                ).isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteUser 메소드는")
    class Describe_deleteUser {
        private Long givenId;

        private User subject(Long id) {
            return userService.deleteUser(id);
        }

        @Nested
        @DisplayName("저장된 user의 id를 가지고 있다면")
        class Context_with_saved_user_id {
            @BeforeEach
            void setSavedId() {
                givenId = givenSavedId;

                given(userRepository.findById(givenId))
                        .willReturn(Optional.of(user));
            }

            @Test
            @DisplayName("user를 삭제히고, 삭제한 user를 반환한다.")
            void it_delete_user_and_return_deleted_user() {
                final User deleted = subject(givenId);

                assertUser(deleted);
            }
        }

        @Nested
        @DisplayName("저장되지 않은 user의 id를 가지고 있다면")
        class Context_with_unsaved_user_id {
            @BeforeEach
            void setUnsavedId() {
                givenId = givenUnsavedId;
            }

            @Test
            @DisplayName("user를 찾을 수 없다는 예외를 던집니다.")
            void it_throw_user_not_found_exception() {
                assertThatThrownBy(
                        () -> subject(givenId),
                        "user를 찾을 수 없다는 예외를 던져야 합니다."
                ).isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
