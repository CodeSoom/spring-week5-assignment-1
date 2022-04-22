package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.users.User;
import com.codesoom.assignment.domain.users.UserRepository;
import com.codesoom.assignment.domain.users.UserSaveRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@DisplayName("userService 테스트")
class UserServiceTest {

    private final static String TEST_USER_EMAIL = "test@example.com";
    private final static String TEST_USER_NAME = "test";
    private final static String TEST_USER_PASSWORD = "1234";

    UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
        userRepository.deleteAll();
    }

    @Nested
    @DisplayName("saveUser 메소드는")
    class Describe_saveUser {

        @Nested
        @DisplayName("회원 등록에 필요한 데이터가 주어진다면")
        class Context_valid {

            UserSaveRequest saveRequest = new UserSaveRequest() {
                @Override
                public String getEmail() {
                    return TEST_USER_EMAIL;
                }

                @Override
                public String getName() {
                    return TEST_USER_NAME;
                }

                @Override
                public String getPassword() {
                    return TEST_USER_PASSWORD;
                }
            };

            @Test
            @DisplayName("회원 저장후 리턴한다.")
            void it_save_and_return_user() {

                User savedUser = userService.saveUser(saveRequest);

                assertAll(
                        () -> Assertions.assertThat(savedUser.getId()).isNotNull(),
                        () -> Assertions.assertThat(savedUser.getEmail()).isEqualTo(TEST_USER_EMAIL),
                        () -> Assertions.assertThat(savedUser.getName()).isEqualTo(TEST_USER_NAME),
                        () -> Assertions.assertThat(savedUser.getPassword()).isEqualTo(TEST_USER_PASSWORD)
                );
            }
        }
    }
}
