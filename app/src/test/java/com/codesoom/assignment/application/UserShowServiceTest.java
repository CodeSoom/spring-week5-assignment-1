package com.codesoom.assignment.application;

import com.codesoom.assignment.application.exceptions.UserNotFoundException;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("UserShowService")
class UserShowServiceTest {
    private UserCrudService service;
    private final UserRepository repository = mock(UserRepository.class);
    private final Long USER_ID = 1L;
    private final Long USER_ID_NOT_EXISTING = 10L;
    private final String USER_NAME = "Test User";
    private final String USER_EMAIL = "hello@gmail.com";
    private final String USER_PASSWORD = "yahOo~!@12345";
    private User user;


    @BeforeEach
    void setUp() {
        service = new UserCrudService(repository);
        user = User.builder()
                .id(USER_ID)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();
    }

    @Nested
    @DisplayName("showAll 메소드는")
    class Describe_showAll {
        private List<User> subject() {
            return service.showAll();
        }

        @Nested
        @DisplayName("만약 존재하는 사용자가 없다면")
        class Context_without_existing_user {
            @BeforeEach
            void setUp() {
                given(repository.findAll()).willReturn(List.of());
            }

            @Test
            @DisplayName("빈 리스트를 반환한다")
            void it_returns_empty_list() {
                assertThat(subject()).isEmpty();
            }
        }

        @Nested
        @DisplayName("만약 존재하는 사용자가 있다면")
        class Context_with_existing_toy {
            @BeforeEach
            void setUp() {
                given(repository.findAll()).willReturn(List.of(user));
            }

            @Test
            @DisplayName("비어 있지 않은 리스트를 반환한다")
            void it_returns_not_empty_list() {
                assertThat(subject()).isNotEmpty();
            }
        }
    }

    @Nested
    @DisplayName("showById 메소드는")
    class Describe_showById {
        abstract class ContextShowingByExisting {
            User withExisting() {
                return service.showById(USER_ID);
            }
        }

        abstract class ContextShowingByNotExisting {
            void withoutExisting() {
                service.showById(USER_ID_NOT_EXISTING);
            }
        }

        @Nested
        @DisplayName("만약 존재하는 User를 조회한다면")
        class Context_with_existing_user extends ContextShowingByExisting {
            @BeforeEach
            void setUp() {
                given(repository.findById(USER_ID)).willReturn(Optional.of(user));
            }

            @Test
            @DisplayName("매개변수로 전달한 값을 Id로 가지고 있는 User를 반환한다")
            void it_returns_toy_having_id_equal_to_param() {
                assertThat(withExisting().getId()).isEqualTo(USER_ID);
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 User를 조회한다면")
        class Context_with_not_existing_user extends ContextShowingByNotExisting {
            @Test
            @DisplayName("예외를 발생시킨다")
            void it_throws_exception() {
                assertThatThrownBy(this::withoutExisting)
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
