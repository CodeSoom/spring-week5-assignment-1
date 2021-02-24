package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DataJpaTest
@DisplayName("UserService의")
class UserServiceTest {
    private final Long givenSavedId = 1L;
    private final Long givenUnsavedId = 100L;
    private final String givenName = "newoo";
    private final String givenEmail = "newoo@codesoom.com";
    private final String givenPassword = "codesoom123";

    private UserService userService;
    private UserRepository userRepository = mock(UserRepository.class);
    private User user;
    private UserData userData;

    @BeforeEach
    void setUp() {
        final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        userService = new UserService(mapper, userRepository);

        userData = UserData.builder()
                .id(givenSavedId)
                .name(givenName)
                .email(givenEmail)
                .password(givenPassword)
                .build();

        user = mapper.map(userData, User.class);
    }

    void assertCreatedUser(User user) {
        assertThat(user.getClass()).isEqualTo(User.class);
        assertThat(user.getName()).isEqualTo(givenName);
        assertThat(user.getEmail()).isEqualTo(givenEmail);
        assertThat(user.getPassword()).isEqualTo(givenPassword);
    }

    @Nested
    @DisplayName("getUsers 메서드는")
    class Describe_getUsers {
        @Nested
        @DisplayName("저장된 user가 없다면")
        class Context_without_any_saved_user {
            @BeforeEach
            void setEmptyList() {
                given(userRepository.findAll()).willReturn(List.of());
            }

            @Test
            @DisplayName("비어있는 리스트를 리턴한다.")
            void it_return_empty_list() {
                assertThat(userService.getUsers()).isEmpty();
            }
        }

        @Nested
        @DisplayName("저장된 user가 있다면")
        class Context_with_any_saved_user {
            private List<User> givenUserList;

            @BeforeEach
            void setSavedUser() {
                givenUserList = List.of(user);

                given(userRepository.findAll()).willReturn(givenUserList);
            }

            @Test
            @DisplayName("user 리스트를 리턴한다.")
            void it_return_user_list() {
                assertThat(userService.getUsers()).isEqualTo(givenUserList);
            }
        }
    }

    @Nested
    @DisplayName("getUser 메소드는")
    class Describe_getUser {
        private Long givenId;

        @Nested
        @DisplayName("저장된 user의 id를 가지고 있다면")
        class Context_with_saved_id {
            private User savedUser;

            @BeforeEach
            void setSavedId() {
                givenId = givenSavedId;

                given(userRepository.findById(givenId)).willReturn(Optional.of(user));
            }

            @Test
            @DisplayName("user를 리턴한다.")
            void it_return_user() {
                savedUser = userService.getUser(givenId);

                verify(userRepository).findById(givenId);

                assertCreatedUser(savedUser);
            }
        }

        @Nested
        @DisplayName("저장되지 않은 toy를 찾으려고하면")
        class Context_when_find_unsaved_toy {
            @BeforeEach
            void setUnsavedId() {
                givenId = givenUnsavedId;
            }

            @Test
            @DisplayName("toy를 찾을 수 없다는 exception을 던진다.")
            void it_throw_exception() {
                assertThatThrownBy(
                        () -> UserService.getUser(givenId),
                        "toy를 찾을 수 없다는 예외를 던져야 합니다."
                ).isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("createUser 메소드는")
    class Describe_createUser {
        private User created;

        @Test
        @DisplayName("user를 추가하고, 추가된 user를 리턴한다.")
        void it_create_user_and_return_created_user() {
            given(userRepository.save(any(User.class))).will(invocation -> {
                return invocation.getArgument(0);
            });

            created = userService.createUser(userData);

            verify(userRepository).save(any(User.class));

            assertCreatedUser(created);
        }
    }
}
