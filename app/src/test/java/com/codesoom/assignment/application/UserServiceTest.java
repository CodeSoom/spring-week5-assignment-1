package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
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
    private static final String NAME = "양효주";
    private static final String EMAIL = "yhyojoo@codesoom.com";
    private static final String PASSWORD = "112233!!";

    private static final String UPDATE_NAME = "효주";
    private static final String UPDATE_EMAIL = "joo@codesoom.com";
    private static final String UPDATE_PASSWORD = "123!";

    private UserService userService;

    private UserRepository userRepository = mock(UserRepository.class);

    private final Mapper dozerMapper = DozerBeanMapperBuilder.buildDefault();

    private User user;

    @BeforeEach
    void setUp() {
        userService = new UserService(dozerMapper, userRepository);

        user = User.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        given(userRepository.save(any(User.class)))
                .will(invocation -> invocation.<Product>getArgument(0));

        given(userRepository.findById(1L))
                .willReturn(Optional.of(user));
    }

    void updateTest(UserData update) {
        assertThat(update.getName()).isEqualTo(UPDATE_NAME);
        assertThat(update.getEmail()).isEqualTo(UPDATE_EMAIL);
        assertThat(update.getPassword()).isEqualTo(UPDATE_PASSWORD);
    }

    @Nested
    @DisplayName("createUser 메소드는")
    class Describe_createUser {
        UserData userData;

        @Test
        @DisplayName("새로운 사용자를 등록한다")
        void it_returns_user() {
            userData = new UserData();

            userService.createUser(userData);

            verify(userRepository).save(any(User.class));
        }
    }

    @Nested
    @DisplayName("updateUser 메소드는")
    class Describe_updateProduct {
        UserData update;

        @Nested
        @DisplayName("등록된 사용자의 ID와 변경될 정보가 주어진다면")
        class Context_with_valid_id_and_product {

            @BeforeEach
            void setUp() {
                update = UserData.builder()
                        .name(UPDATE_NAME)
                        .email(UPDATE_EMAIL)
                        .password(UPDATE_PASSWORD)
                        .build();

                User updatedUser = userService.createUser(update);

                given(userRepository.findById(1L))
                        .willReturn(Optional.of(updatedUser));
            }

            @Test
            @DisplayName("해당 ID를 갖는 사용자의 정보를 수정한다")
            void it_returns_updated_product() {
                userService.updateUser(1L, update);

                verify(userRepository).findById(1L);

                updateTest(update);
            }
        }

        @Nested
        @DisplayName("등록되지 않은 사용자의 ID가 주어진다면")
        class Context_with_invalid_id {

            @Test
            @DisplayName("수정할 사용자를 찾을 수 없다는 예외를 던진다")
            void it_returns_warning_message() {
                assertThatThrownBy(() -> userService.updateUser(100L, update))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class Describe_deleteProduct {
        User user;

        @Nested
        @DisplayName("등록된 사용자의 ID가 주어진다면")
        class Context_with_valid_id {

            @Test
            @DisplayName("해당 ID를 갖는 사용자를 삭제한다")
            void it_returns_deleted_user() {
                user = new User();

                userService.deleteUser(1L);

                verify(userRepository).findById(1L);
                verify(userRepository).delete(any(User.class));
            }
        }

        @Nested
        @DisplayName("등록되지 않은 사용자의 ID가 주어진다면")
        class Context_without_invalid_id {

            @Test
            @DisplayName("삭제할 사용자를 찾을 수 없다는 예외를 던진다")
            void it_returns_warning_message() {
                assertThatThrownBy(() -> userService.deleteUser(100L))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
