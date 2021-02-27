package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("UserRepository 클래스")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Nested
    @DisplayName("save")
    class Describe_save {

        @DisplayName("사용자를 저장하고 저장된 사용자를 id와 리턴한다")
        @Test
        void it_saves_user_and_returns_with_id() {
            User user = User.builder()
                    .name("Min")
                    .email("min@gmail.com")
                    .password("password")
                    .build();

            User savedUser = userRepository.save(user);

            assertThat(savedUser).isEqualTo(user);
            assertThat(savedUser.getId()).isNotNull();
        }
    }
}
