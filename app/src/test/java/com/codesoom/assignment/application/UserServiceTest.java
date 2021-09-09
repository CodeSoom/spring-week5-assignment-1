package com.codesoom.assignment.application;

import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.codesoom.assignment.constant.UserConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserServiceTest {

    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private UserData userData;

    @BeforeEach
    void setup() {
        userService = new UserService(userRepository);
        userData = UserData.builder()
                .name(NAME)
                .password(PASSWORD)
                .email(EMAIL)
                .build();

    }

    @Test
    @DisplayName("유저 생성")
    void createUser() {
        // when
        UserData user = userService.createUser(userData);

        // then
        assertThat(user.getName()).isEqualTo(user.getName());
        assertThat(user.getEmail()).isEqualTo(user.getEmail());
    }


}
