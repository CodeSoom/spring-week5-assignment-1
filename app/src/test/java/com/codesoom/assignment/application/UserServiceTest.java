package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("UserService 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserServiceTest {

    private UserRepository userRepository = mock(UserRepository.class);

    private UserService userService = new UserService(userRepository);

    @Nested
    class createUser_메소드는 {

        @BeforeEach
        void setUp() {
            given(userRepository.save(any(User.class)))
                    .will(invocation -> {
                        User user = invocation.getArgument(0);
                        return User.testUser(
                                1L,
                                user.getName(),
                                user.getEmail(),
                                user.getPassword()
                        );
                    });
        }

        @Test
        void 회원을_저장한다() {
            User user = User.createSaveUser(
                    "홍길동",
                    "test@naver.com",
                    "1234"
            );

            userService.createUser(user);

            verify(userRepository).save(any(User.class));
        }
    }

    @Nested
    class updateProduct_메소드는 {

        @Nested
        class 주어진_아이디의_회원이_있다면 {
            private final Long USER_ID = 1L;
            private final String USER_NAME = "test";

            @BeforeEach
            void setUp() {
                User user = User.testUser(USER_ID, USER_NAME, null, null);

                given(userRepository.findById(USER_ID)).willReturn(Optional.of(user));
            }

            @Test
            void 회원을_수정한다() {
                User source = User.testUser(null, USER_NAME, null, null);

                userService.updateProduct(USER_ID, source);
            }
        }
    }
}