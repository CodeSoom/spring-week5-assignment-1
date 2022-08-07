package com.codesoom.assignment.application;

import com.codesoom.assignment.TestUserBuilder;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.infra.InMemoryUserRepository;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserService 클래스")
class UserServiceTest {
    private final TestUserBuilder allFieldsUserFactory = new TestUserBuilder()
            .name("name")
            .password("password")
            .email("email");

    private UserService service;

    @BeforeEach
    void setup() {
        service = new UserService(new InMemoryUserRepository(), DozerBeanMapperBuilder.buildDefault());
    }

    @Nested
    @DisplayName("createUser 메소드는")
    class Describe_createUser {
        @Nested
        @DisplayName("유효한 회원 정보를 전달하면")
        class Context_withValidUserData {
            private UserData allFieldsUserData;

            @BeforeEach
            void prepare() {
                allFieldsUserData = allFieldsUserFactory.buildData();
            }

            @Test
            @DisplayName("생성된 회원 정보를 반환한다")
            void it_returnsCratedUser() throws Exception {
                final User result = service.createUser(allFieldsUserData);
                final User expect = allFieldsUserFactory.id(1L).buildUser();

                assertThat(result).isEqualTo(expect);
                assertThat(result.getId()).isEqualTo(1L);
            }
        }
    }
}
