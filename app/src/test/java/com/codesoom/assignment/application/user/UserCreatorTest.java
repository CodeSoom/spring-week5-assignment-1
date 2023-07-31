package com.codesoom.assignment.application.user;

import com.codesoom.assignment.application.JpaTest;
import com.codesoom.assignment.domain.user.User;
import com.codesoom.assignment.dto.user.UserRequest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("UserCreator 클래스")
class UserCreatorTest extends JpaTest {
    private UserRequest createUserRequest() {
        return new UserRequest(
                "testName",
                "test@Email.com",
                "testPassword"
        );
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class createUser_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 유저_정보가_주어지면 {
            @Nested
            @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
            class 유저를_생성하고_반환한다 {
                private UserRequest USER_REQUEST;
                private UserCreator userCreator;
                @BeforeEach
                void setUp() {
                    USER_REQUEST = createUserRequest();
                    userCreator = new UserCreator(getUserRepository());

                }

                @Test
                @DisplayName("해당_유저정보를_저장_후_저장한_유저정보를_리턴한다")
                void it_saves_and_returns_user() {
                    User user = userCreator.createUser(USER_REQUEST);

                    assertNotNull(user);
                    assertEquals(USER_REQUEST.getName(), user.getName());
                    assertEquals(USER_REQUEST.getEmail(), user.getEmail());
                    assertEquals(USER_REQUEST.getPassword(), user.getPassword());
                }

            }
        }
    }
}
