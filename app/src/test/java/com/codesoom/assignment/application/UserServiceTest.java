package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserService 테스트")
class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository;

    private final Long EXISTED_ID = 1L;

    private final String CREATE_USER_NAME = "createdName";
    private final String CREATE_USER_EMAIL = "createdEmail";
    private final String CREATE_USER_PASSWORD = "createdPassword";

    private final String UPDATE_USER_NAME = "updatedName";
    private final String UPDATE_USER_EMAIL = "updatedEmail";
    private final String UPDATE_USER_PASSWORD = "updatedPassword";

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Nested
    @DisplayName("createProduct 메서드는")
    class Describe_createProduct {
        @Nested
        @DisplayName("만약 유저 객체가 주어진다면")
        class Context_WithUser {
            private User source;

            @BeforeEach
            void setUp() {
                source = new User(CREATE_USER_NAME,CREATE_USER_EMAIL,CREATE_USER_EMAIL);
            }

            @Test
            @DisplayName("주어진 객체를 저장하고 해당 객체를 리턴한다")
            void itSavesObjectAndReturnsObject() {
                given(userRepository.save(any(User.class))).willReturn(source);

                User createdUser = userService.createUser(source);
                assertThat(createdUser.getName()).isEqualTo(source.getName());
                assertThat(createdUser.getEmail()).isEqualTo(source.getEmail());
                assertThat(createdUser.getPassword()).isEqualTo(source.getPassword());

                verify(userRepository).save(any(User.class));
            }
        }
    }

    @Nested
    @DisplayName("updateUser 메서드는")
    class Describe_updateUser {
        @Nested
        @DisplayName("만약 저장되어 있는 유저의 아이디와 유저 객체가 주어진다면")
        class Context_WithExistedIdAndObject {
            private final Long givenExistedId = EXISTED_ID;
            private User source;

            @BeforeEach
            void setUp() {
                source = new User(UPDATE_USER_NAME, UPDATE_USER_EMAIL, UPDATE_USER_PASSWORD);
            }

            @Test
            @DisplayName("주어진 객체를 업데이트하고 해당 객체를 리턴한다")
            void itUpdatesObjectAndReturnsObject() {
                given(userRepository.update(eq(givenExistedId), any(User.class))).willReturn(source);

                User updatedUser = userService.updateUser(givenExistedId, source);
                assertThat(updatedUser.getName()).isEqualTo(source.getName());
                assertThat(updatedUser.getEmail()).isEqualTo(source.getEmail());
                assertThat(updatedUser.getPassword()).isEqualTo(source.getPassword());

                verify(userRepository).update((eq(givenExistedId)),any(User.class));
            }
        }
    }
}