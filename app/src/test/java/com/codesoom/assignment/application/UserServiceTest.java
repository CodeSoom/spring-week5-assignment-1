package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;

import com.codesoom.assignment.dto.UserDTO;
import com.codesoom.assignment.exception.UserNotFoundException;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("UserService 테스트")
class UserServiceTest {

    private final String NAME = "이름1";
    private final String PASSWORD = "비밀번호1";
    private final String EMAIL = "이메일1";

    private UserDTO userDTO;
    private User user;
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        userService = new UserService(mapper, userRepository);
        userDTO = UserDTO.builder()
                .name(NAME)
                .password(PASSWORD)
                .email(EMAIL).build();
        user = mapper.map(userDTO, User.class);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Nested
    @DisplayName("create 메소드")
    class Describe_create {

        @Test
        @DisplayName("생성된 유저를 반환합니다.")
        void it_return_created_user() {
            User createdUser = userService.create(userDTO);

            assertThat(createdUser.getName()).isEqualTo(NAME);
            assertThat(createdUser.getPassword()).isEqualTo(PASSWORD);
            assertThat(createdUser.getEmail()).isEqualTo(EMAIL);
        }
    }

    @Nested
    @DisplayName("update 메소드")
    class Describe_update {

        private UserDTO newUserDTO;
        private final String NEW_NAME = "이름2";
        private final String NEW_PASSWORD = "비밀번호2";
        private final String NEW_EMAIL = "이메일2";

        @BeforeEach
        void prepare() {
            newUserDTO = UserDTO.builder()
                    .name(NEW_NAME)
                    .password(NEW_PASSWORD)
                    .email(NEW_EMAIL).build();
        }

        @Nested
        @DisplayName("저장된 유저의 id가 주어지면")
        class Context_with_existing_user_id {

            private Long validId;

            @BeforeEach
            void prepare() {
                User givenUser = userRepository.save(user);
                validId = givenUser.getId();
            }

            @Test
            @DisplayName("변경된 유저를 반환합니다.")
            void it_return_created_user() {
                User updatedUser = userService.update(validId, newUserDTO);

                assertThat(updatedUser.getName()).isEqualTo(NEW_NAME);
                assertThat(updatedUser.getPassword()).isEqualTo(NEW_PASSWORD);
                assertThat(updatedUser.getEmail()).isEqualTo(NEW_EMAIL);
            }
        }

        @Nested
        @DisplayName("저장되지 않는 유저의 id가 주어지면")
        class Context_with_not_existing_user_id {

            private Long invalidId;

            @BeforeEach
            void prepare() {
                userRepository.deleteAll();
                invalidId = 1L;
            }

            @Test
            @DisplayName("UserNotFoundException을 던집니다.")
            void it_throw_UserNotFoundException() {
                assertThatThrownBy(()->userService.update(invalidId, newUserDTO))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("delete 메소드")
    class Describe_delete {

        @Nested
        @DisplayName("저장되지 않는 유저의 id가 주어지면")
        class Context_with_not_existing_user_id {

            private Long invalidId;

            @BeforeEach
            void prepare() {
                userRepository.deleteAll();
                invalidId = 1L;
            }

            @Test
            @DisplayName("UserNotFoundException을 던집니다.")
            void it_throw_UserNotFoundException() {
                assertThatThrownBy(()->userService.delete(invalidId))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}