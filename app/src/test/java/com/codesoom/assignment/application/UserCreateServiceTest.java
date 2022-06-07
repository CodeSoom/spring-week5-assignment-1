package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("UserCreateService")
class UserCreateServiceTest {
    private UserCrudService service;
    private final UserRepository repository = mock(UserRepository.class);
    private final Long USER_ID = 1L;
    private final String USER_NAME = "Test User";
    private final String USER_EMAIL = "hello@gmail.com";
    private final String USER_PASSWORD = "yahOo~!@12345";
    private User user;
    private User userWithoutId;


    @BeforeEach
    void setUp() {
        service = new UserCrudService(repository);
        user = User.builder()
                .id(USER_ID)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();
        userWithoutId = User.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();
    }

    @Nested
    @DisplayName("create 메소드는")
    class Describe_create {
        private User subject() {
            return service.create(userWithoutId);
        }

        @BeforeEach
        void setUp() {
            given(repository.save(any(User.class))).willReturn(user);
        }

        @Test
        @DisplayName("매개변수로 전달한 값이 반영된 User를 반환한다")
        void it_returns_toy_reflecting_params() {
            assertThat(subject().getName()).isEqualTo(USER_NAME);
            assertThat(subject().getEmail()).isEqualTo(USER_EMAIL);
            assertThat(subject().getPassword()).isEqualTo(USER_PASSWORD);
        }
    }
}
