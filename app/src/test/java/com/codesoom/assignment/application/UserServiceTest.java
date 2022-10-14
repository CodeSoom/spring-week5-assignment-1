package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserServiceTest {
    private UserService userService;

    private UserRepository userRepository = mock(UserRepository.class);

//

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
        given(userRepository.save(any(User.class))).will(invocation -> {
            User source = invocation.getArgument(0);
            return source;
        });
    }

    @DisplayName("UserService create 메소드")
    @Nested
    class Describe_create {

        @DisplayName("userdata가 들어올때")
        @Nested
        class Context_userdata {
            private UserData userData = UserData.builder().username("username").email("email@gmail.com").password("2334").build();

            @DisplayName("생성된 userdata를 반환한다")
            @Test
            void it_returns_created_userData() {
                UserData createdUserData = userService.create(userData);
                assertThat(createdUserData.getUsername()).isEqualTo(userData.getUsername());
                assertThat(createdUserData.getEmail()).isEqualTo(userData.getEmail());
                assertThat(createdUserData.getPassword()).isEqualTo(userData.getPassword());
            }
        }
    }

}
