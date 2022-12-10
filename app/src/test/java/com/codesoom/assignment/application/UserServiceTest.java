package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.infra.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserService 클래스")
class UserServiceTest {

    private UserRepository repository;
    private UserService service;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        service = new UserService(repository);

        User user = User.builder()
                .id(1L)
                .name("홍길동")
                .email("abc@gmail.com")
                .password("abc123")
                .build();

        given(repository.findById(1L)).willReturn(Optional.ofNullable(user));
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {
        @Test
        @DisplayName("모든 User 정보들을 리턴한다")
        void it_return_users() {
            service.findAll();

            verify(repository).findAll();
        }
    }

    @Nested
    @DisplayName("saveUser 메소드는")
    class Describe_saveUser {
        @Nested
        @DisplayName("검증된 User 정보가 넘어올 경우")
        class Context_with_validUser {
            @Test
            @DisplayName("생성된 User를 리턴한다")
            void it_return_user() {
                UserData userData = UserData.builder()
                        .name("홍길동")
                        .email("abc@gmail.com")
                        .password("abc123")
                        .build();

                service.saveUser(userData);

                verify(repository).save(any(User.class));

                assertThat(service.findUser(1L).getName()).isEqualTo("홍길동");
            }
        }
    }

    @Nested
    @DisplayName("changeUser 메소드는")
    class Describe_changeUser {
        @Nested
        @DisplayName("존재하는 id와 User 정보가 넘어올 경우")
        class Context_with_validIdAndUser {
            @Test
            @DisplayName("User 정보를 수정한다")
            void it_update_user() {
                User user = service.findUser(1L);

                assertThat(service.findUser(1L)).isNotNull();

                UserData userData = UserData.builder()
                        .name("고길동")
                        .email("def@gmail.com")
                        .password("def456")
                        .build();

                service.updateUser(1L, userData);

                assertThat(user.getName()).isEqualTo("고길동");
                assertThat(user.getEmail()).isEqualTo("def@gmail.com");
                assertThat(user.getPassword()).isEqualTo("def456");
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메소드는")
    class Describe_deleteById {
        @Nested
        @DisplayName("존재하는 id가 넘어올 경우")
        class Context_with_validId {
            @Test
            @DisplayName("User 삭제한다")
            void it_delete_user() {
                service.deleteUser(1L);

                verify(repository).deleteById(1L);
            }
        }
    }
}
