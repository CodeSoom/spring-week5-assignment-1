package com.codesoom.assignment.infra;

import static com.codesoom.assignment.constants.UserConstants.USER;
import static org.assertj.core.api.Assertions.assertThat;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("UserRepository 클래스")
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    private User savedUser;

    private void subjectSave() {
        savedUser = userRepository.save(USER);
    }

    private void subjectDelete() {
        userRepository.delete(savedUser);
    }

    @Nested
    @DisplayName("save 메서드는")
    public class Context_user_save {
        @AfterEach
        public void afterEach() {
            subjectDelete();
        }

        @Test
        @DisplayName("User를 저장한다.")
        public void it_saves_object() {
            subjectSave();

            assertThat(savedUser)
                .matches(user -> user.getId() != null);
        }
    }
}
