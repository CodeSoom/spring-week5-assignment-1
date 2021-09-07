package com.codesoom.assignment.controllers;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.CreateUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {

        @Nested
        @DisplayName("유효한 유저 생성 DTO가 주어질 때")
        class Context_validUser {

            private CreateUserDto validCreateUserDto;

            @BeforeEach
            void setUp() {
                validCreateUserDto = CreateUserDto.builder()
                    .name("name")
                    .email("email")
                    .password("password")
                    .build();
            }

            @Test
            @DisplayName("생성한 유저를 리턴한다")
            void it_create_a_user() {
                User user = userController.create(validCreateUserDto);

                assertThat(user.getName()).isEqualTo("name");
                assertThat(user.getEmail()).isEqualTo("email");
                assertThat(user.getPassword()).isEqualTo("password");
            }
        }

        @Nested
        @DisplayName("유효하지 않은 유저 생성 DTO가 주어질 때")
        class Context_invalidCreateUserDto {

            private CreateUserDto invalidCreateUserDto;

            @BeforeEach
            void setUp() {
                invalidCreateUserDto = CreateUserDto.builder()
                    .name("name")
                    .email("email")
                    .build();
            }

            @Test
            @DisplayName("에러를 던진다")
            void it_throws() {
                assertThatThrownBy(() ->
                    userController.create(invalidCreateUserDto)
                );
            }
        }
    }
}
