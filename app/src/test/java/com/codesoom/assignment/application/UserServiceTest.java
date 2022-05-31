package com.codesoom.assignment.application;

import com.codesoom.assignment.DuplicateUserException;
import com.codesoom.assignment.InvalidEmailException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
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

@DisplayName("UserService 클래스")
class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository = mock(UserRepository.class);

    private final Long STORED_ID = 1L;
    private final Long NOT_STORED_ID = 100L;

    private final String NAME = "name";
    private final String EMAIL = "email@example.email";
    private final String PASSWORD = "password12!@";

    private final String UPDATE_NAME = "name1";
    private final String UPDATE_EMAIL = "email1@example.email";
    private final String UPDATE_PASSWORD = "password12!#";

    private final String INVALID_EMAIL = "email";

    private User user;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);

        user = User.builder()
                .id(STORED_ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
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

            private UserData userData;

            @BeforeEach
            void setUp() {
                userData = UserData.builder()
                        .name(NAME)
                        .email(EMAIL)
                        .password(PASSWORD)
                        .build();
            }

            @Test
            @DisplayName("주어진 사용자를 저장하고 사용자를 리턴한다")
            void it_returns_created_user() {
                given(userRepository.save(any(User.class))).willReturn(user);

                User user = userService.createUser(userData);

                assertThat(user.getName()).isEqualTo(userData.getName());
                assertThat(user.getEmail()).isEqualTo(userData.getEmail());
                assertThat(user.getPassword()).isEqualTo(userData.getPassword());

                verify(userRepository).save(any(User.class));
            }
        }

        @Nested
        @DisplayName("유효하지 않은 email을 지닌 사용자가 주어지면")
        class Context_with_invalid_email_user {
            private UserData invalidUserData;

            @BeforeEach
            void setUp() {
                invalidUserData = UserData.builder()
                        .name(NAME)
                        .email(INVALID_EMAIL)
                        .password(PASSWORD)
                        .build();
            }

            @Test
            @DisplayName("InvalidEmailException을 던진다")
            void it_throws_invalid_email_exception() {
                assertThatThrownBy(() -> userService.createUser(invalidUserData))
                        .isInstanceOf(InvalidEmailException.class);
            }
        }

        @Nested
        @DisplayName("이미 저장된 사용자의 email이 주어지면")
        class Context_with_stored_user {
            private UserData stored_user_data = UserData.builder()
                    .name(NAME)
                    .email(EMAIL)
                    .password(PASSWORD)
                    .build();

            @BeforeEach
            void setUp() {
                given(userRepository.isExistsEmail(stored_user_data.getEmail())).willReturn(true);
            }

            @Test
            @DisplayName("DuplicateUserException을 던진다")
            void it_throws_duplicate_user_exception() {
                assertThatThrownBy(() -> userService.createUser(stored_user_data))
                        .isInstanceOf(DuplicateUserException.class);
            }
        }
    }
}
