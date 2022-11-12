package com.codesoom.assignment.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codesoom.assignment.support.IdFixture.ID_MIN;
import static com.codesoom.assignment.support.UserFixture.USER_1;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User 도메인 엔티티 테스트")
class UserTest {

    @Test
    @DisplayName("User Builder 테스트")
    void creationWithBuilder() {
        User user = USER_1.회원_엔티티_생성(ID_MIN.value());

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(ID_MIN.value());
        assertThat(user.getName()).isEqualTo(USER_1.NAME());
        assertThat(user.getEmail()).isEqualTo(USER_1.EMAIL());
        assertThat(user.getPassword()).isEqualTo(USER_1.PASSWORD());
    }
}
