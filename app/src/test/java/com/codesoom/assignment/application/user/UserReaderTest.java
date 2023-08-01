package com.codesoom.assignment.application.user;

import com.codesoom.assignment.application.JpaTest;
import com.codesoom.assignment.dto.user.UserRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;


@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("UserReader 클래스")
class UserReaderTest extends JpaTest {
    private final String TEST_NAME = "testName";
    private final String TEST_EMAIL = "test@Email.com";
    private final String TEST_PASSWORD = "testPassword";

    private UserRequest createUserRequest() {
        return new UserRequest(
                TEST_NAME,
                TEST_EMAIL,
                TEST_PASSWORD
        );
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class getUser_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 유저_아이디가_주어지면 {
            private UserRequest USER_REQUEST;
            private Long savedUserId;
            private UserReader userReader;

            @BeforeEach
            void setUp() {
                USER_REQUEST = createUserRequest();
                savedUserId = getUserRepository().save(USER_REQUEST.toUser()).getId();
                userReader = new UserReader(getUserRepository());
            }

            @DisplayName("해당_유저정보를_리턴한다")
            @Test
            void it_returns_user() {
                Assertions.assertThat(userReader.getUser(savedUserId).getName()).isEqualTo(TEST_NAME);
                Assertions.assertThat(userReader.getUser(savedUserId).getEmail()).isEqualTo(TEST_EMAIL);
                Assertions.assertThat(userReader.getUser(savedUserId).getPassword()).isEqualTo(TEST_PASSWORD);
            }
        }
    }

}
