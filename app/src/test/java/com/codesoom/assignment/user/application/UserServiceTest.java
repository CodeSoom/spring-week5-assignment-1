package com.codesoom.assignment.user.application;

import com.codesoom.assignment.user.domain.UserRepository;
import com.codesoom.assignment.user.dto.UserResponseDto;
import com.codesoom.assignment.user.dto.UserSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
@DisplayName("UserService 클래스")
class UserServiceTest {
    private static final Long USER_ID = 1L;
    private static final Long NOT_EXIST_ID = -1L;

    private static final String USER_NAME = "test";
    private static final String USER_PASSWORD = "pass";
    private static final String USER_EMAIL = "test@test.com";

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void cleanup() {
        userRepository.findAll().clear();
    }

    @Nested
    @DisplayName("createUser 메서드는")
    class Describe_createUser {
        UserSaveRequestDto requestDto;

        @BeforeEach
        void setUp() {
            requestDto = getUserSaveDto();
        }

        @DisplayName("새로운 사용자 추가되고, 사용자 정보를 리턴한다")
        @Test
        void it_returns_user() {
            UserResponseDto actual = userService.createUser(requestDto);

            assertAll(
                    () -> assertThat(userService.getUsers()).isNotEmpty(),
                    () -> assertThat(actual.getId()).isNotNull(),
                    () -> assertThat(actual.getName()).isEqualTo(USER_NAME),
                    () -> assertThat(actual.getEmail()).isEqualTo(USER_EMAIL),
                    () -> assertThat(actual.getPassword()).isEqualTo(USER_PASSWORD)
            );
        }
    }

    private UserSaveRequestDto getUserSaveDto() {
        return UserSaveRequestDto.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();
    }
}
