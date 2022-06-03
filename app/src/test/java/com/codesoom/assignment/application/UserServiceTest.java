package com.codesoom.assignment.application;

import com.codesoom.assignment.DuplicateUserException;
import com.codesoom.assignment.InvalidEmailException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserService 클래스")
class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository = mock(UserRepository.class);

    private Long STORED_ID;
    private final Long NOT_STORED_ID = 100L;

    private final String NAME = "name";
    private final String EMAIL = "email@example.email";
    private final String PASSWORD = "password12!@";

    private final String UPDATE_NAME = "name1";
    private final String UPDATE_PASSWORD = "password12!#";

    private final String CREATE_NAME = "name2";
    private final String CREATE_EMAIL = "email2@example.email";
    private final String CREATE_PASSWORD = "password13!@";

    private final String INVALID_EMAIL = "email";

    private User user;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);

        user = User.builder()
                .id(1L)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        STORED_ID = user.getId();

        given(userRepository.findById(STORED_ID))
                .willReturn(Optional.of(user));
    }

    @Nested
    @DisplayName("getUser")
    class Describe_getUser {

        @Nested
        @DisplayName("저장된 사용자의 id가 주어지면")
        class Context_with_an_stored_id {

            @Test
            @DisplayName("id에 해당하는 사용자를 리턴한다")
            void it_returns_user() {
                given(userRepository.findById(STORED_ID)).willReturn(Optional.of(user));

                User user = userService.getUser(STORED_ID);

                assertThat(user.getName()).isEqualTo(NAME);
                assertThat(user.getEmail()).isEqualTo(EMAIL);
                assertThat(user.getPassword()).isEqualTo(PASSWORD);

                verify(userRepository).findById(STORED_ID);
            }
        }

        @Nested
        @DisplayName("저장되지 않은 사용자의 id가 주어지면")
        class Context_with_not_an_stored_id {

            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_throws_product_not_found_exception() {
                assertThatThrownBy(() -> userService.getUser(NOT_STORED_ID))
                        .isInstanceOf(UserNotFoundException.class);

                verify(userRepository).findById(NOT_STORED_ID);
            }
        }
    }

    @Nested
    @DisplayName("createUser")
    class Describe_createUser {

        @Nested
        @DisplayName("사용자가 주어지면")
        class Context_with_a_user {
            private User newUser;

            @BeforeEach
            void setUp() {
                 newUser = User.builder()
                        .name(CREATE_NAME)
                        .email(CREATE_EMAIL)
                        .password(CREATE_PASSWORD)
                        .build();
            }

            @Test
            @DisplayName("주어진 사용자를 저장하고 사용자를 리턴한다")
            void it_returns_created_user() {
                given(userRepository.save(any(User.class)))
                        .will(invocation -> invocation.getArgument(0));

                User user = userService.createUser(newUser);

                assertThat(user.getName()).isEqualTo(user.getName());
                assertThat(user.getEmail()).isEqualTo(user.getEmail());
                assertThat(user.getPassword()).isEqualTo(user.getPassword());

                verify(userRepository).save(any(User.class));
            }
        }

        @Nested
        @DisplayName("유효하지 않은 email을 지닌 사용자가 주어지면")
        class Context_with_invalid_email_user {
            private User invalidUser;

            @BeforeEach
            void setUp() {
                invalidUser = User.builder()
                        .name(CREATE_NAME)
                        .email(INVALID_EMAIL)
                        .password(CREATE_PASSWORD)
                        .build();
            }

            @Test
            @DisplayName("InvalidEmailException을 던진다")
            void it_throws_invalid_email_exception() {
                assertThatThrownBy(() -> userService.createUser(invalidUser))
                        .isInstanceOf(InvalidEmailException.class);
            }
        }

        @Nested
        @DisplayName("이미 저장된 사용자의 email이 주어지면")
        class Context_with_stored_user {
            private User storedUser = User.builder()
                    .name(NAME)
                    .email(EMAIL)
                    .password(PASSWORD)
                    .build();

            @BeforeEach
            void setUp() {
                given(userRepository.isExistsEmail(storedUser.getEmail())).willReturn(true);
            }

            @Test
            @DisplayName("DuplicateUserException을 던진다")
            void it_throws_duplicate_user_exception() {
                assertThatThrownBy(() -> userService.createUser(storedUser))
                        .isInstanceOf(DuplicateUserException.class);
            }
        }
    }

    @Nested
    @DisplayName("updateUser")
    class Describe_updateUser {
        private User source;

        @BeforeEach
        void setUp() {
            source = User.builder()
                    .name(UPDATE_NAME)
                    .password(UPDATE_PASSWORD)
                    .build();
        }

        @Nested
        @DisplayName("저장된 사용자 id가 주어지면")
        class Context_with_an_stored_user_id {

            @Test
            @DisplayName("업데이트된 사용자를 리턴한다")
            void it_returns_updated_user() {
                User updatedUser = userService.updateUser(STORED_ID, source);

                assertThat(updatedUser.getName()).isEqualTo(source.getName());
                assertThat(updatedUser.getPassword()).isEqualTo(source.getPassword());
            }
        }

        @Nested
        @DisplayName("저장되지 않은 사용자 id가 주어지면")
        class Context_with_not_stored_user_id {

            @Test
            @DisplayName("UserNotFoundException을 던진다")
            void it_throws_user_not_found_exception() {
                assertThrows(UserNotFoundException.class,
                        () -> userService.updateUser(NOT_STORED_ID, source));

                verify(userRepository).findById(NOT_STORED_ID);
            }
        }
    }

    @Nested
    @DisplayName("deleteUser")
    class Describe_deleteUser {

        @Nested
        @DisplayName("저장된 사용자 id가 주어지면")
        class Context_with_an_stored_id {

            @Test
            @DisplayName("사용자를 삭제한다")
            void it_deletes_user() {
                userService.deleteUser(STORED_ID);

                verify(userRepository).delete(any(User.class));
            }
        }

        @Nested
        @DisplayName("저장되지 않은 사용자 id가 주어지면")
        class Context_with_not_stored_id {

            @Test
            @DisplayName("UserNotFoundException을 던진다")
            void it_throws_user_not_found_exception() {
                assertThrows(UserNotFoundException.class,
                        () -> userService.deleteUser(NOT_STORED_ID));
            }
        }
    }
}
