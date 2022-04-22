package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("UserService 는")
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    private final User userWithoutId = new User(null, "김갑생", "gabseng@naver.com", "12341234");

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Nested
    @DisplayName("create() 메서드에서")
    class Context_create_method {
        @Nested
        @DisplayName("id가 없는 User 객체를 받았을 때")
        class Context_with_user {
            @Test
            @DisplayName("리포지토리에 저장하고, User 객체를 리턴한다.")
            void It_saves_user_in_repository_and_returns_user() {
                User user = userService.create(userWithoutId);
                assertThat(user).isNotNull();
                assertThat(user).isInstanceOf(User.class);
                assertThat(userRepository.count()).isEqualTo(1);
            }
        }
    }
}