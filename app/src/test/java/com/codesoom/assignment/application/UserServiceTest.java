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
    private final Long WROUNG_ID = 100L;
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
    }

    @Nested
    class updateUser_메소드는 {
        @Nested
        class 주어진_아이디의_회원이_있다면 {
            private final String UPDATE_USER_NAME = USER_NAME + "!!!";

            @BeforeEach
            void setUp() {
                User user = createTestUser();

                given(userRepository.findById(USER_ID)).willReturn(Optional.of(user));
            }

            @Test
            void 회원을_수정한다() {
                UserData source = UserData.builder()
                        .name(UPDATE_USER_NAME)
                        .build();

                User user = userService.updateUser(USER_ID, source);

                assertThat(user.getName()).isEqualTo(UPDATE_USER_NAME);
            }
        }

        @Nested
        class 주어진_아이디의_회원이_없다면 {
            @BeforeEach
            void setUp() {
                given(userRepository.findById(WROUNG_ID)).willReturn(Optional.empty());
            }

            @Test
            void 예외를_던진다() {
                UserData source = UserData.builder().build();

                assertThatThrownBy(() -> userService.updateUser(WROUNG_ID, source))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    class deleteUser_메소드는 {
        @Nested
        class 주어진_아이디의_회원이_있다면 {
            @BeforeEach
            void setUp() {
                User user = createTestUser();

                given(userRepository.findById(USER_ID)).willReturn(Optional.of(user));
            }

            @Test
            void 회원을_삭제한다() {
                userService.deleteUser(USER_ID);

                verify(userRepository).delete(any(User.class));
            }
        }
    }

    private User createTestUser() {
        return User.testUser(USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD);
    }
}
