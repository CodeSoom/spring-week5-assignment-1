package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

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

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        userService = new UserService(mapper, userRepository);

        user = User.builder()
                .id(givenSavedId)
                .name(givenName)
                .email(givenEmail)
                .password(givenPassword)
                .build();
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
    @DisplayName("createUser 메소드는")
    class Describe_createUser {
        private User created;

        @Test
        @DisplayName("user를 추가하고, 추가된 user를 리턴한다.")
        void it_create_user_and_return_created_user() {
            given(userRepository.save(user)).will(invocation -> {
                return invocation.getArgument(0);
            });

            created = userService.createUser(user);

            assertThat(created.getClass()).isEqualTo(User.class);
            assertThat(created.getName()).isEqualTo(givenName);
            assertThat(created.getEmail()).isEqualTo(givenEmail);
            assertThat(created.getPassword()).isEqualTo(givenPassword);
        }
    }
}
