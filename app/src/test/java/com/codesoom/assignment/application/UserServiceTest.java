package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserService 클래스")
class UserServiceTest {
    private final UserRepository userRepository = new UserRepository.Fake();
    private final UserService userService = new UserService(userRepository);
    private final String givenEmail = "juuni.ni.i@gmail.com";
    private final String givenName = "juunini";
    private final String givenPassword = "secret";

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
}
