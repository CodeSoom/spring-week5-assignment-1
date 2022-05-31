package com.codesoom.assignment.application;

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
}
