package com.codesoom.assignment.application;

import com.codesoom.assignment.dto.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserServiec 클래스")
class UserServiceTest {
    private UserService service;

    @BeforeEach
    void setup() {
        service = new UserService();
    }

    @Nested
    @DisplayName("createUser 메소드는")
    class Describe_createUser {
        @DisplayName("유효한 회원 정보를 전달하면")
        class Context_withValidUserData {
            private final UserData validUserData = UserData.builder()
                    .name("name")
                    .email("email")
                    .password("password")
                    .build();

            @Test
            @DisplayName("생성된 회원 정보를 반환한다")
            void it_returnsCratedUserData() throws Exception {
                final UserData result = service.createUser(validUserData);

                final UserData expectUserData = UserData.builder()
                        .id(1L)
                        .name(validUserData.getName())
                        .email(validUserData.getEmail())
                        .password(validUserData.getPassword())
                        .build();
                assertThat(result).isEqualTo(expectUserData);
            }
        }
    }
}
