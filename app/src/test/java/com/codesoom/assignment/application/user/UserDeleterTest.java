package com.codesoom.assignment.application.user;

import com.codesoom.assignment.application.JpaTest;
import com.codesoom.assignment.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

@SuppressWarnings({"NonAsciiCharacters","NonAsciiCharacters"})
@DisplayName("UserDeleter 클래스")
class UserDeleterTest extends JpaTest {
    private final String TEST_NAME = "testName";
    private final String TEST_EMAIL = "test@Email.com";
    private final String TEST_PASSWORD = "testPassword";

    private User createUser() {
        return User.builder()
                .name(TEST_NAME)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class deleteUser_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 유저아이디가_주어지면{
            private long deleteId;
            @BeforeEach
            void setUp() {
                User user = createUser();
                deleteId = getUserRepository().save(user).getId();
            }

            @Test
            @DisplayName("해당_유저정보를_삭제한다")
            void it_deletes_user() {
                UserDeleter userDeleter = new UserDeleter(getUserRepository(),new UserReader(getUserRepository()));
                userDeleter.deleteUser(deleteId);
                Assertions.assertThat(getUserRepository().findById(deleteId)).isEmpty();
            }
        }
    }
}
