package com.codesoom.assignment.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("UserService의")
class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Nested
    @DisplayName("getUsers 메서드는")
    class Describe_getUsers {
        @Nested
        @DisplayName("저장된 user가 없다면")
        class Context_without_any_saved_user {
            @Test
            @DisplayName("비어있는 리스트를 리턴한다.")
            void it_return_empty_list() {
                assertThat(userService.getUsers()).isEmpty();
            }
        }

        @Nested
        @DisplayName("저장된 user가 있다면")
        class Context_with_any_saved_user {
            @Test
            @DisplayName("user 리스트를 리턴한다.")
            void it_return_user_list() {
                assertThat(userService.getUsers()).isEqualTo(givenUserList);
            }
        }
    }
}
