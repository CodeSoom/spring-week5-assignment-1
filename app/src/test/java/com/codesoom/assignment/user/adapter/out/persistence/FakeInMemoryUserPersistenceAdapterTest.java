package com.codesoom.assignment.user.adapter.out.persistence;

import com.codesoom.assignment.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.codesoom.assignment.support.IdFixture.ID_MAX;
import static com.codesoom.assignment.support.UserFixture.USER_1;
import static com.codesoom.assignment.support.UserFixture.USER_2;
import static org.assertj.core.api.Assertions.assertThat;

class FakeInMemoryUserPersistenceAdapterTest {
    private final FakeInMemoryUserPersistenceAdapter fakeUserRepository;

    FakeInMemoryUserPersistenceAdapterTest() {
        this.fakeUserRepository = new FakeInMemoryUserPersistenceAdapter();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class save_메서드는 {
        @Test
        @DisplayName("회원을 저장하고 리턴합니다")
        void it_returns_user() {
            User user = fakeUserRepository.save(USER_1.회원_엔티티_생성());

            assertThat(user.getName()).isEqualTo(USER_1.NAME());
            assertThat(user.getEmail()).isEqualTo(USER_1.EMAIL());
            assertThat(user.getPassword()).isEqualTo(USER_1.PASSWORD());
        }

        @Test
        @DisplayName("회원이 1 증가합니다")
        void it_returns_users_size() {
            int oldSize = fakeUserRepository.findAll().size();

            fakeUserRepository.save(USER_2.회원_엔티티_생성());

            int newSize = fakeUserRepository.findAll().size();

            assertThat(newSize - oldSize).isEqualTo(1);
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class delete_메서드는 {

        private User fixtureUser;

        @BeforeEach
        void setUpCreateFixture() {
            fixtureUser = fakeUserRepository.save(USER_1.회원_엔티티_생성());
        }

        @Test
        @DisplayName("회원을 삭제합니다")
        void it_deleted() {
            assertThat(fakeUserRepository.findById(fixtureUser.getId()))
                    .isNotEmpty();

            fakeUserRepository.delete(fixtureUser);

            assertThat(fakeUserRepository.findById(fixtureUser.getId()))
                    .isEmpty();
        }

        @Test
        @DisplayName("회원이 1 감소합니다")
        void it_returns_users_size() {
            int oldSize = fakeUserRepository.findAll().size();

            fakeUserRepository.delete(fixtureUser);

            int newSize = fakeUserRepository.findAll().size();

            assertThat(newSize - oldSize).isEqualTo(-1);
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class findAll_메서드는 {
        @Test
        @DisplayName("회원 목록을 리턴합니다")
        void it_returns_users() {
            List<User> users = fakeUserRepository.findAll();

            assertThat(users).isNotNull()
                    .isInstanceOf(List.class);
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class findById_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 회원이_있을_경우 {
            @Test
            @DisplayName("회원을 리턴합니다")
            void it_returns_user() {
                User userSource = fakeUserRepository.save(USER_2.회원_엔티티_생성());

                User user = fakeUserRepository.findById(userSource.getId()).get();

                assertThat(user.getName()).isEqualTo(USER_2.NAME());
                assertThat(user.getEmail()).isEqualTo(USER_2.EMAIL());
                assertThat(user.getPassword()).isEqualTo(USER_2.PASSWORD());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 회원이_없을_경우 {
            @Test
            @DisplayName("빈 Optional을 리턴합니다")
            void it_returns_user() {
                Optional<User> user = fakeUserRepository.findById(ID_MAX.value());

                assertThat(user)
                        .isInstanceOf(Optional.class)
                        .isEmpty();
            }
        }
    }
}
