package com.codesoom.assignment.application;

import com.codesoom.assignment.application.exceptions.UserNotFoundException;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("UserUpdateService")
class UserUpdateServiceTest {
    private UserCrudService service;
    private final UserRepository repository = mock(UserRepository.class);
    private final Long USER_ID = 1L;
    private final Long USER_ID_NOT_EXISTING = 10L;
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
    @DisplayName("update 메소드는")
    class Describe_update {
        abstract class ContextUpdatingExisting {
            User withExisting() {
                return service.update(USER_ID, userWithoutId);
            }
        }

        abstract class ContextUpdatingNotExisting {
            void withoutExisting() {
                service.update(USER_ID_NOT_EXISTING, userWithoutId);
            }
        }

        @Nested
        @DisplayName("만약 존재하는 User를 수정한다면")
        class Context_with_existing extends ContextUpdatingExisting {
            @BeforeEach
            void setUp() {
                given(repository.existsById(USER_ID)).willReturn(Boolean.TRUE);
                given(repository.save(any(User.class))).will(invocation -> {
                    User source = invocation.getArgument(0);
                    return User.builder()
                            .id(USER_ID)
                            .name(source.getName())
                            .email(source.getEmail())
                            .password(source.getPassword())
                            .build();
                });
            }

            @Test
            @DisplayName("매개변수로 전달한 값을 Id로 가지고 있는 User를 반환한다")
            void it_returns_user_having_id_equal_to_param() {
                assertThat(withExisting().getId()).isEqualTo(USER_ID);
            }

            @Test
            @DisplayName("매개변수로 전달한 값이 반영된 Toy를 반환한다")
            void it_returns_user_reflecting_params() {
                assertThat(withExisting().getName()).isEqualTo(USER_NAME);
                assertThat(withExisting().getEmail()).isEqualTo(USER_EMAIL);
                assertThat(withExisting().getPassword()).isEqualTo(USER_PASSWORD);
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 User를 수정한다면")
        class Context_with_not_existing_user extends ContextUpdatingNotExisting {
            @BeforeEach
            void setUp() {
                given(repository.existsById(USER_ID_NOT_EXISTING)).willReturn(Boolean.FALSE);
            }
            @Test
            @DisplayName("예외를 발생시킨다")
            void it_throws_exception() {
                assertThatThrownBy(this::withoutExisting)
                        .isInstanceOf(UserNotFoundException.class);
            }

        }
    }
}
