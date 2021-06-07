package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User 클래스의")
class UserTest {
    private User userBob;

    @BeforeEach
    void prepareUser() {
        userBob = UserFixtures.bob();
    }

    @Nested
    @DisplayName("changeWith 메서드는")
    class Describe_changeWith {

        @Test
        @DisplayName("사용자의 속성을 주어진 사용자 데이터로 수정한 후 리턴한다")
        void sut_changeWith() {
            User userAlice = UserFixtures.alice();

            userBob.changeWith(userAlice);

            assertThat(userBob.getName()).isEqualTo(userAlice.getName());
            assertThat(userBob.getEmail()).isEqualTo(userAlice.getEmail());
            assertThat(userBob.getPassword()).isEqualTo(userAlice.getPassword());
        }
    }
}
