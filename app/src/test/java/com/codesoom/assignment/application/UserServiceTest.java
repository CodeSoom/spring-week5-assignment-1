package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
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

    private UserRepository userRepository = mock(UserRepository.class);
    private Mapper mapper = new DozerBeanMapper();

    private UserService userService = new UserService(userRepository, mapper);

    private final Long USER_ID = 1L;
    private final Long WRONG_ID = 100L;
    private final String USER_NAME = "홍길동";
    private final String USER_EMAIL = "test@naver.com";
    private final String USER_PASSWORD = "1234";

    @DisplayName("createUser 메소드는")
    @Nested
    class createUserMethod {

        @DisplayName("null이 아닌 UserData가 주어진다면")
        @Nested
        class ifUserDataNotNull {
            @BeforeEach
            void setUp() {
                User user = createTestUser();

                given(userRepository.save(any(User.class))).willReturn(user);
            }

            @DisplayName("회원을 저장한다.")
            @Test
            void saveUser() {
                UserData source = UserData.builder()
                        .name("홍길동")
                        .email("test@test.com")
                        .password("1234")
                        .build();

                User user = userService.createUser(source);

                verify(userRepository).save(any(User.class));

                assertThat(user).isNotNull();
            }
        }

        @DisplayName("UserData의 값이 null이라면")
        @Nested
        class UserDataIsNull {
            @DisplayName("회원을 찾을 수 없다는 예외를 던진다.")
            @Test
            void test() {
                UserData source = null;

                assertThatThrownBy(() -> userService.createUser(source))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("updateUser 메소드는")
    class updateUserMethod {
        @DisplayName("주어진 아이디의 회원이 있다면")
        @Nested
        class haveUserWithId {
            private final String UPDATE_USER_NAME = USER_NAME + "!!!";

            @BeforeEach
            void setUp() {
                User user = createTestUser();

                given(userRepository.findById(USER_ID)).willReturn(Optional.of(user));
            }

            @DisplayName("회원을 수정한다.")
            @Test
            void updateUser() {
                UserData source = UserData.builder()
                        .name(UPDATE_USER_NAME)
                        .build();

                User user = userService.updateUser(USER_ID, source);

                assertThat(user.getName()).isEqualTo(UPDATE_USER_NAME);
            }
        }

        @DisplayName("주어진 아이디의 회원이 없다면")
        @Nested
        class NotHaveUserWithId {
            @BeforeEach
            void setUp() {
                given(userRepository.findById(WRONG_ID)).willReturn(Optional.empty());
            }

            @DisplayName("예외를 던진다.")
            @Test
            void throwError() {
                UserData source = UserData.builder().build();

                assertThatThrownBy(() -> userService.updateUser(WRONG_ID, source))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @DisplayName("deleteUser 메소드는")
    @Nested
    class deleteUserMethod {
        @DisplayName("주어진 아이디의 회원이 있다면")
        @Nested
        class haveUserWithId {
            @BeforeEach
            void setUp() {
                User user = createTestUser();

                given(userRepository.findById(USER_ID)).willReturn(Optional.of(user));
            }

            @DisplayName("회원을 삭제한다.")
            @Test
            void deleteUser() {
                userService.deleteUser(USER_ID);

                verify(userRepository).delete(any(User.class));
            }
        }
    }

    private User createTestUser() {
        return User.testUser(USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD);
    }
}
