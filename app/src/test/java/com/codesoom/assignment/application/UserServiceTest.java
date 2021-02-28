package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("UserService 클래스")
class UserServiceTest {
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void initRepository() {
        userRepository = new UserRepository.Fake();
        userService = new UserService(userRepository);
    }

    private final Long givenID = 1L;
    private final String givenEmail = "juuni.ni.i@gmail.com";
    private final String givenName = "juunini";
    private final String givenPassword = "secret";
    private final User givenUser = new User(
            givenID,
            givenEmail,
            givenName,
            givenPassword
    );

    private User makeUser(
            String email,
            String name,
            String password
    ) {
        return new User(email, name, password);
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {
        @Test
        @DisplayName("유저를 생성한 뒤 생성된 유저를 리턴한다.")
        void It_makes_user_and_nothing() {
            final User user = userService.create(makeUser(
                    givenEmail,
                    givenName,
                    givenPassword
            ));
            assertThat(user.email()).isEqualTo(givenEmail);
            assertThat(user.name()).isEqualTo(givenName);
            assertThat(user.password()).isEqualTo(givenPassword);
        }
    }

    @Nested
    @DisplayName("modify 메서드는")
    class Describe_modify {
        private final String givenModifiedName = "juuni Ni";
        private final User givenModifiedUser = new User(
                givenID,
                givenEmail,
                givenModifiedName,
                givenPassword
        );

        @Nested
        @DisplayName("주어진 id에 해당하는 유저가 존재할 때")
        class Context_with_exists_given_id_user {
            @BeforeEach
            void setup() {
                userRepository.save(givenUser);
            }

            @Test
            @DisplayName("id에 해당하는 유저를 변경한다.")
            void It_modify_user() {
                userService.modify(givenID, givenModifiedUser);
            }
        }

        @Nested
        @DisplayName("주어진 id에 해당하는 유저가 존재할 때")
        class Context_without_exists_given_id_user {
            @Test
            @DisplayName("UserNotFoundException 을 던진다.")
            void It_throws_UserNotFoundException() {
                assertThatThrownBy(() -> userService.modify(givenID, givenModifiedUser))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
