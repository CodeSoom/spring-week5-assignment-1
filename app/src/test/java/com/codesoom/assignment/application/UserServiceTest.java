package com.codesoom.assignment.application;

import com.codesoom.assignment.TestUserBuilder;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.infra.InMemoryUserRepository;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("UserService 클래스")
class UserServiceTest {
    private TestUserBuilder allFieldsUserBuilder;
    private UserRepository repository;
    private UserService service;

    @BeforeEach
    void setup() {
        allFieldsUserBuilder = new TestUserBuilder()
                .name("name")
                .password("password")
                .email("email");
        repository = new InMemoryUserRepository();
        service = new UserService(repository, DozerBeanMapperBuilder.buildDefault());
    }

    @Nested
    @DisplayName("createUser 메소드는")
    class Describe_createUser {
        @Nested
        @DisplayName("모든 필드가 공백이 아닌 회원 정보를 전달하면")
        class Context_withValidUserData {
            private UserData allFieldsUserData;

            @BeforeEach
            void prepare() {
                allFieldsUserData = allFieldsUserBuilder.buildData();
            }

            @Test
            @DisplayName("생성된 회원 정보를 반환한다")
            void it_returnsCratedUser() throws Exception {
                final User result = service.createUser(allFieldsUserData);
                final User expect = allFieldsUserBuilder.id(1L).buildUser();

                assertThat(result).isEqualTo(expect);
                assertThat(result.getId()).isEqualTo(1L);
            }
        }
    }

    @Nested
    @DisplayName("updateUser 메소드는")
    class Describe_updateUser {
        @Nested
        @DisplayName("존재하는 회원 Id와 모든 필드가 공백이 아닌 회원 정보를 전달하면")
        class Context_withValidUserData {
            private TestUserBuilder updateUserBuilder;
            private UserData updateUserData;

            @BeforeEach
            void prepare() {
                repository.save(allFieldsUserBuilder.buildUser());
                updateUserBuilder = allFieldsUserBuilder
                        .name("name2")
                        .email("email2")
                        .password("password2");
                updateUserData = updateUserBuilder.buildData();
            }

            @Test
            @DisplayName("업데이트된 회원 정보를 반환한다")
            void it_returnsUpdatedUser() throws Exception {
                final User result = service.updateUser(1L, updateUserData);
                final User expect = updateUserBuilder.id(1L).buildUser();

                assertThat(result).isEqualTo(expect);
                assertThat(result.getId()).isEqualTo(1L);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 회원 Id와 모든 필드가 공백이 아닌 회원 정보를 전달하면")
        class Context_withNotExistUserId {
            private UserData updateUserData;

            @BeforeEach
            void prepare() {
                repository.deleteAll();
                updateUserData = allFieldsUserBuilder
                        .name("name2")
                        .email("email2")
                        .password("password2")
                        .buildData();
            }

            @Test
            @DisplayName("회원을 찾을 수 없다는 에러를 던진다")
            void it_returnsUpdatedUser() throws Exception {
                assertThrows(UserNotFoundException.class, () -> {
                    service.updateUser(1L, updateUserData);
                });
            }
        }
    }

    @Nested
    @DisplayName("deleteUser 메소드는")
    class Describe_deleteUser {
        @Nested
        @DisplayName("존재하는 회원 Id를 전달하면")
        class Context_withExistingUserId {
            @BeforeEach
            void prepare() {
                repository.save(allFieldsUserBuilder.buildUser());
            }

            @Test
            @DisplayName("회원 정보를 삭제한다")
            void it_deleteUser() throws Exception {
                service.deleteUserById(1L);

                assertThat(repository.findById(1L)).isEmpty();
            }
        }

        @Nested
        @DisplayName("존재하지 않는 회원 Id로 요청하면")
        class Context_withNotExistingUserId {
            @BeforeEach
            void prepare() {
                repository.deleteAll();
            }

            @Test
            @DisplayName("회원을 찾을 수 없다는 예외 던진다")
            void it_returnsUpdatedUser() throws Exception {
                assertThrows(UserNotFoundException.class, () -> {
                    service.deleteUserById(1L);
                });
            }
        }
    }
}
