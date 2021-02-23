package com.codesoom.assignment.user.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserRepository 클래스")
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {
        @Nested
        @DisplayName("등록된 사용자가 있다면")
        class Context_with_users {

            @BeforeEach
            void setUp() {
                User user1 = generateUser("test1@test.com");
                User user2 = generateUser("test2@test.com");

                userRepository.save(user1);
                userRepository.save(user2);
            }

            @Test
            @DisplayName("모든 사용자 목록을 리턴한다.")
            void It_returns_all_users() {
                assertThat(userRepository.findAll()).hasSize(2);
            }
        }

        @Nested
        @DisplayName("등록된 사용자가 없다면")
        class Context_without_users {

            @Test
            @DisplayName("비어있는 사용자 목록을 리턴한다.")
            void it_returns_empty_users() {
                assertThat(userRepository.findAll()).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("findById 메서드는 ")
    class Describe_findById {

        @Nested
        @DisplayName("존재하는 사용자 id가 주어진다면")
        class Context_with_exist_user_id {
            private Long existId;

            @BeforeEach
            void setUp() {
                User saved = userRepository.save(generateUser("test1@test.com"));
                existId = saved.getId();
            }

            @Test
            @DisplayName("찾고자하는 사용자를 리턴한다.")
            void it_returns_user() {
                Optional<User> user = userRepository.findById(existId);

                assertThat(user.isPresent()).isTrue();
            }
        }

        @Nested
        @DisplayName("존재하지 않는 사용자 id가 주어진다면")
        class Context_with_not_exist_user_id {
            private Long notExistId = -1L;

            @BeforeEach
            void setUp() {
                userRepository.findById(notExistId)
                        .ifPresent(u -> userRepository.delete(u));
            }

            @Test
            @DisplayName("비어있는 사용자를 리턴한다.")
            void it_returns_empty_user() {
                Optional<User> user = userRepository.findById(notExistId);

                assertThat(user.isEmpty()).isTrue();
            }
        }
    }

    @Nested
    @DisplayName("save 메서드는")
    class Describe_save {
        @DisplayName("사용자을 등록하고, 등록된 사용자를 리턴한다.")
        @Test
        void It_save_user_and_returns_saved_user() {
            User expected = generateUser("test1@test.com");

            User actual = userRepository.save(expected);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_delete {

        @Nested
        @DisplayName("존재하는 사용자가 주어지면")
        class Context_with_exist_user {
            User existedUser;

            @BeforeEach
            void setUp() {
                existedUser = userRepository.save(generateUser("test1@test.com"));
            }

            @Test
            @DisplayName("사용자를 삭제한다.")
            void It_deletes_user() {
                userRepository.delete(existedUser);

                assertThat(userRepository.findAll()).isEmpty();
            }
        }

        @Nested
        @DisplayName("존재하지 않는 사용자가 주어지면")
        class Context_with_not_exist_user {
            User notExistedUser;

            @BeforeEach
            void setUp() {
                userRepository.save(generateUser("test1@test.com"));
                notExistedUser = User.builder().build();
            }

            @Test
            @DisplayName("사용자를 삭제한다.")
            void It_deletes_noting() {
                userRepository.delete(notExistedUser);

                assertThat(userRepository.findAll()).hasSize(1);
            }
        }
    }


    private User generateUser(String email) {
        return User.builder()
                .name("test")
                .email(email)
                .password("pass")
                .build();
    }

}
