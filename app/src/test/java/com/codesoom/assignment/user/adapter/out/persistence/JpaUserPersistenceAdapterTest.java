package com.codesoom.assignment.user.adapter.out.persistence;

import com.codesoom.assignment.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.codesoom.assignment.support.UserFixture.USER_1;
import static com.codesoom.assignment.support.UserFixture.USER_2;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("UserPersistenceAdapter JPA 테스트")
class JpaUserPersistenceAdapterTest {
    private final JpaUserPersistenceAdapter jpaUserPersistenceAdapter;

    @Autowired
    JpaUserPersistenceAdapterTest(JpaUserPersistenceAdapter jpaUserPersistenceAdapter) {
        this.jpaUserPersistenceAdapter = jpaUserPersistenceAdapter;
    }

    @BeforeEach
    void setUpFixtureDeleteAll() {
        jpaUserPersistenceAdapter.deleteAllInBatch();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class save_메서드는 {

        @Test
        @DisplayName("회원을 저장하고 리턴합니다")
        void it_returns_user() {
            User user = jpaUserPersistenceAdapter.save(USER_1.회원_엔티티_생성());

            assertThat(user.getName()).isEqualTo(USER_1.NAME());
            assertThat(user.getEmail()).isEqualTo(USER_1.EMAIL());
            assertThat(user.getPassword()).isEqualTo(USER_1.PASSWORD());
        }

        @Test
        @DisplayName("회원이 1 증가합니다")
        void it_returns_users_size() {
            int oldSize = jpaUserPersistenceAdapter.findAll().size();

            jpaUserPersistenceAdapter.save(USER_2.회원_엔티티_생성());

            int newSize = jpaUserPersistenceAdapter.findAll().size();

            assertThat(newSize - oldSize).isEqualTo(1);
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class delete_메서드는 {

        private User fixtureUser;

        @BeforeEach
        void setUpCreateFixture() {
            fixtureUser = jpaUserPersistenceAdapter.save(USER_1.회원_엔티티_생성());
        }

        @Test
        @DisplayName("회원을 삭제합니다")
        void it_deleted() {
            assertThat(jpaUserPersistenceAdapter.findById(fixtureUser.getId()))
                    .isNotEmpty();

            jpaUserPersistenceAdapter.delete(fixtureUser);

            assertThat(jpaUserPersistenceAdapter.findById(fixtureUser.getId()))
                    .isEmpty();
        }

        @Test
        @DisplayName("회원이 1 감소합니다")
        void it_returns_users_size() {
            int oldSize = jpaUserPersistenceAdapter.findAll().size();

            jpaUserPersistenceAdapter.delete(fixtureUser);

            int newSize = jpaUserPersistenceAdapter.findAll().size();

            assertThat(newSize - oldSize).isEqualTo(-1);
        }
    }
}
