package com.codesoom.assignment.application.user;

import com.codesoom.assignment.application.JpaTest;
import com.codesoom.assignment.domain.user.User;
import com.codesoom.assignment.dto.user.UserData;
import org.junit.jupiter.api.*;

import org.assertj.core.api.Assertions;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("UserCreator 클래스")
class UserCreatorTest extends JpaTest {
    private final String TEST_NAME = "testName";
    private final String TEST_EMAIL = "test@Email.com";
    private final String TEST_PASSWORD = "testPassword";

    private UserData createUserRequest() {
        return new UserData(
                TEST_NAME,
                TEST_EMAIL,
                TEST_PASSWORD
        );
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class createUser_메서드는 {

        private UserData USER_REQUEST;
        private UserCreator userCreator;

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 유저_정보가_주어지면 {

                @BeforeEach
                void setUp() {
                    USER_REQUEST = createUserRequest();
                    userCreator = new UserCreator(getUserRepository());
                }

                @Test
                @DisplayName("해당_유저정보를_저장_후_저장한_유저정보를_리턴한다")
                void it_saves_and_returns_user() {
                    User user = userCreator.createUser(USER_REQUEST);

                    Assertions.assertThat(user.getName()).isEqualTo(TEST_NAME);
                    Assertions.assertThat(user.getEmail()).isEqualTo(TEST_EMAIL);
                    Assertions.assertThat(user.getPassword()).isEqualTo(TEST_PASSWORD);
                }

            }
        }
}
