package com.codesoom.assignment.infra;

import static com.codesoom.assignment.constants.UserConstants.USER;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    private User subjectSave() {
        return userRepository.save(USER);
    }

    private void subjectDelete() {
        userRepository.delete(savedUser);
    }

    private Optional<User> subjectFindById() {
        return userRepository.findById(savedUser.getId());
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
        public void it_saves_users() {
            savedUser = subjectSave();

            assertThat(savedUser)
                .matches(user -> user.getId() != null);
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    public class Context_user_delete {
        @BeforeEach
        public void beforeEach() {
            savedUser = subjectSave();
        }

        @Test
        @DisplayName("User를 삭제한다.")
        public void it_deletes_users() {
            subjectDelete();

            assertThat(subjectFindById())
                .isEmpty();
        }
    }
}
