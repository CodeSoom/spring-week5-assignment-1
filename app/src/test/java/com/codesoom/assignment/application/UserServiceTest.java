package com.codesoom.assignment.application;

import com.codesoom.assignment.UserEmailDuplicationException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("UserService 클래스")
@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {
    @Value("${test.id-info.exist-id}")
    private Long EXIST_ID;

    @Value("${test.id-info.not-exist-id}")
    private Long NOT_EXIST_ID;

    @Value("${test.create-info.name}")
    private String NAME;

    @Value("${test.create-info.email}")
    private String EMAIL;

    @Value("${test.create-info.password}")
    private String PASSWORD;

    @Value("${test.update-info.name}")
    private String UPDATE_NAME;

    @Value("${test.update-info.password}")
    private String UPDATE_PASSWORD;

    User givenUser;

    private UserService userService;
    private UserRepository userRepository = mock(UserRepository.class);

    boolean isEquals(User expectedUser, User actualUser) {
        return actualUser.getName().equals(expectedUser.getName()) &&
                actualUser.getEmail().equals(expectedUser.getEmail()) &&
                actualUser.getPassword().equals(expectedUser.getPassword());
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(userRepository);
        userService = new UserService(userRepository);

        givenUser = User.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        given(userRepository.save(any(User.class))).willReturn(givenUser);
        given(userRepository.findById(EXIST_ID)).willReturn(Optional.of(givenUser));
        given(userRepository.findById(NOT_EXIST_ID)).willReturn(Optional.empty());
    }

    @DisplayName("createUser()")
    @Nested
    class Describe_create {

        User subject(User user) {
            return userService.createUser(user);
        }

        @Nested
        @DisplayName("유저 정보가 주어진다면")
        class Context_with_user {

            @DisplayName("생성된 user를 반환한다")
            @Test
            void it_returns_user() {
                User user = subject(givenUser);
                assertThat(isEquals(givenUser, user)).isTrue();
            }
        }

        @Nested
        @DisplayName("중복된 이메일 정보가 주어진다면")
        class Context_with_duplicate_email {

            @BeforeEach
            void setUp() {
                given(userRepository.existsByEmail(EMAIL))
                        .willReturn(true);
            }

            @DisplayName("중복된 이메일로 인하여 유저 생성에 실패했다는 예외를 던진다")
            @Test
            void it_returns_duplicate_exception() {
                assertThrows(UserEmailDuplicationException.class,
                        () -> subject(givenUser));
            }
        }
    }

    @DisplayName("findUser()")
    @Nested
    class Describe_findById {
        @Nested
        @DisplayName("존재하는 user id가 주어진다면")
        class Context_with_exist_user_id {
            Long givenUserId = EXIST_ID;

            @DisplayName("주어진 id와 일치하는 user를 반환한다")
            @Test
            void it_returns_user() {
                User user = userService.findUser(givenUserId);
                assertThat(isEquals(givenUser, user)).isTrue();
            }
        }

        @Nested
        @DisplayName("존재하지 않는 user id와 user가 주어진다면")
        class Context_with_not_exist_user_id {
            Long givenUserId = NOT_EXIST_ID;

            @DisplayName("user를 찾을수 없다는 예외를 던진다")
            @Test
            void it_returns_not_fount_exception() {
                assertThrows(UserNotFoundException.class, () -> userService.findUser(givenUserId));
            }
        }
    }

    @DisplayName("updateUser()")
    @Nested
    class Describe_update {
        User source = User.builder()
                .name(UPDATE_NAME)
                .password(UPDATE_PASSWORD)
                .build();

        User subject(Long id, User source) {
            return userService.updateUser(id, source);
        }

        @Nested
        @DisplayName("존재하는 user id와 user가 주어진다면")
        class Context_with_exist_user_id {
            Long givenUserId = EXIST_ID;

            @DisplayName("수정된 user를 반환한다")
            @Test
            void it_returns_user() {
                User user = subject(givenUserId, source);

                assertThat(user.getName()).isEqualTo(UPDATE_NAME);
                assertThat(user.getPassword()).isEqualTo(UPDATE_PASSWORD);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 user id와 user가 주어진다면")
        class Context_with_not_exist_user_id {
            Long givenId = NOT_EXIST_ID;

            @DisplayName("user를 찾을수 없다는 예외를 던진다")
            @Test
            void it_returns_not_fount_exception() {
                assertThrows(UserNotFoundException.class, () -> subject(givenId, source));
            }
        }
    }

    @Nested
    @DisplayName("deleteUser()")
    class Describe_Delete {
        User subject(Long id) {
            return userService.deleteUser(id);
        }

        @Nested
        @DisplayName("존재하는 user id와 user가 주어진다면")
        class Context_with_exist_user_id {
            Long givenId = EXIST_ID;

            @DisplayName("삭제된 user를 반환한다")
            @Test
            void it_returns_user() {
                User user = subject(givenId);
                assertThat(isEquals(givenUser, user)).isTrue();
            }
        }

        @Nested
        @DisplayName("존재하지 않는 user id와 user가 주어진다면")
        class Context_with_not_exist_user_id {
            Long givenId = NOT_EXIST_ID;

            @DisplayName("user를 찾을수 없다는 예외를 던진다")
            @Test
            void it_returns_not_fount_exception() {
                assertThrows(UserNotFoundException.class, () -> subject(givenId));
            }
        }
    }
}
