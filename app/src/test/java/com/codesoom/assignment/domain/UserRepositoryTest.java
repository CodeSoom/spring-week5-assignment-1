package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("UserRepository 에서")
class UserRepositoryTest {
    private final static String USERNAME = "username1";
    private final static String EMAIL = "example@example.com";
    private final static String PASSWORD = "password";

    @Autowired
    private UserRepository userRepository;

    /**
     * 하나의 User 를 생성해 등록합니다
     * @return 생성된 User를 반환
     */
    private User createUser() {
        UserData userData = UserData.builder()
                .username(USERNAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        return userRepository.save(userData);
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_of_findAll {

        @Nested
        @DisplayName("찾을 수 있는 User가 있을 경우")
        class Context_with_users {

            @BeforeEach
            void setUp() {
                createUser();
            }

            @Test
            @DisplayName("User를 포함한 배열을 리턴한다")
            void it_returns_users() {
                List<User> users = userRepository.findAll();

                assertThat(users).isNotEmpty();
            }
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_of_findById {

        @Nested
        @DisplayName("찾을 수 있는 객체의 Id 가 주어지면")
        class Context_with_valid_id {
            private Long userId;

            @BeforeEach
            void setUp() {
                User user = createUser();
                userId = user.getId();
            }

            @Test
            @DisplayName("Id와 동일한 객체를 리턴한다")
            void it_return_user() {
                User found = userRepository.findById(userId)
                        .orElse(null);

                assertThat(found).isNotNull();
            }
        }
    }
}
