package com.codesoom.assignment.infra;

import static com.codesoom.assignment.constants.UserConstants.ID;
import static com.codesoom.assignment.constants.UserConstants.NAME;
import static com.codesoom.assignment.constants.UserConstants.EMAIL;
import static com.codesoom.assignment.constants.UserConstants.PASSWORD;
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

    private User user;

    private User subjectSave() {
        return userRepository.save(USER);
    }

    private void subjectDelete() {
        userRepository.delete(user);
    }

    private Optional<User> subjectFindById() {
        return userRepository.findById(user.getId());
    }

    @Nested
    @DisplayName("save 메서드는")
    public class Describe_save {
        @AfterEach
        private void afterEach() {
            subjectDelete();
        }

        @Test
        @DisplayName("User를 저장한다.")
        public void it_saves_users() {
            user = subjectSave();

            assertThat(subjectFindById())
                .isPresent();
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    public class Describe_delete {
        @BeforeEach
        private void beforeEach() {
            user = subjectSave();
        }

        @Test
        @DisplayName("User를 삭제한다.")
        public void it_deletes_users() {
            subjectDelete();

            assertThat(subjectFindById())
                .isEmpty();
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    public class Describe_findById {
        @Nested
        @DisplayName("id에 해당하는 User가 있는 경우")
        public class Context_valid_id {
            @BeforeEach
            private void beforeEach() {
                user = subjectSave();
            }

            @AfterEach
            private void afterEach() {
                subjectDelete();
            }

            @Test
            @DisplayName("User를 찾아 리턴한다.")
            public void it_returns_a_user() {
                assertThat(subjectFindById())
                    .isPresent()
                    .matches(output -> output.get().getId() == user.getId());
            }
        }

        @Nested
        @DisplayName("id에 해당하는 User가 없는 경우")
        public class Context_invalid_id {
            @BeforeEach
            private void beforeEach() {
                user = User.builder()
                    .id(ID)
                    .name(NAME)
                    .email(EMAIL)
                    .password(PASSWORD)
                    .build();
            }

            @Test
            @DisplayName("빈 값을 리턴한다.")
            public void it_returns_a_empty_value() {
                assertThat(subjectFindById())
                    .isEmpty();
            }
        }
    }
}
