package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("UserService 클래스")
class UserServiceTest {

    private UserRepository userRepository = mock(UserRepository.class);

    private UserService userService = new UserService(userRepository);

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
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
        void 유저를_저장한다() {
            User user = User.createSaveUser(
                    "홍길동",
                    "test@naver.com",
                    "1234"
            );

            userService.createUser(user);

            verify(userRepository).save(any(User.class));
        }
    }
}