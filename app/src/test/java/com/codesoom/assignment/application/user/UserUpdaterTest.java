package com.codesoom.assignment.application.user;

import com.codesoom.assignment.application.JpaTest;
import com.codesoom.assignment.domain.user.User;
import com.codesoom.assignment.dto.user.UserRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;


@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("UserUpdater 클래스")
class UserUpdaterTest extends JpaTest {
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
    class updateUser_메서드는 {
        private UserUpdater userUpdater = new UserUpdater(userRepository, new UserReader(userRepository));
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 수정할_유저가_존재한다면 {
            private UserRequest USER_REQUEST;
            private Long savedUserId;
            @BeforeEach
            void setUp() {
                userRepository.deleteAll();
                USER_REQUEST = createUserRequest();
                savedUserId = userRepository.save(USER_REQUEST.toUser()).getId();
            }

            @DisplayName("해당 유저정보를 수정 후 수정한 유저정보를 리턴한다")
            @Test
            void it_updates_and_returns_user() {
                UserRequest userUpdateRequest = UserRequest.builder()
                        .name("newName")
                        .email("newEmail")
                        .password("newPassword")
                        .build();

                User user = userUpdater.updateUser(savedUserId, userUpdateRequest);

                Assertions.assertThat(user.getName()).isEqualTo("newName");
                Assertions.assertThat(user.getEmail()).isEqualTo("newEmail");
                Assertions.assertThat(user.getPassword()).isEqualTo("newPassword");
            }
        }
    }
}
