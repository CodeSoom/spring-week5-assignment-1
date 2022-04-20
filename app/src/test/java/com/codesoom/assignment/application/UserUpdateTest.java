package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.domain.UserSaveDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserUpdateTest extends ServiceTest {

    private UserCommandService service;

    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setup() {
        this.service = new UserCommandService(repository);
        cleanup();
    }

    @AfterEach
    void cleanup() {
        repository.deleteAll();
    }

    @DisplayName("updateUser 메서드는")
    @Nested
    class Describe_update_user {

        @DisplayName("찾을 수 있는 회원 id가 주어지면")
        @Nested
        class Context_with_exist_user {
            private Long EXIST_USER_ID;

            @BeforeEach
            void setup() {
                final UserSaveDto userSaveDto = new UserSaveDto("홍길동", "email", "password");
                this.EXIST_USER_ID = repository.save(userSaveDto.user()).getId();
            }

            @DisplayName("회원 정보를 수정 후 수정된 정보를 반환한다.")
            @Test
            void it_update_user() {
                final UserSaveDto USER_TO_UPDATE = new UserSaveDto("임꺽정", "email", "password");
                User user = service.updateUser(EXIST_USER_ID, USER_TO_UPDATE);

                assertThat(user.getName()).isEqualTo("임꺽정");
            }
        }

        @DisplayName("찾을 수 없는 회원 id가 주어지면")
        @Nested
        class Context_with_not_exist_user {

            private Long NOT_EXIST_USER_ID = 999L;

            @BeforeEach
            void setup() {
                if (repository.existsById(NOT_EXIST_USER_ID)) {
                    repository.deleteById(NOT_EXIST_USER_ID);
                }
            }

            @DisplayName("예외를 던진다.")
            @Test
            void it_update_user() {
                final UserSaveDto USER_TO_UPDATE = new UserSaveDto("임꺽정", "email", "password");

                assertThatThrownBy(() -> service.updateUser(NOT_EXIST_USER_ID, USER_TO_UPDATE))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }

    }

}
