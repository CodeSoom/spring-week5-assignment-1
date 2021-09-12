package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.exception.UserNotFoundException;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository = mock(UserRepository.class);

    public static final long VALID_ID = 1L;
    public static final long INVALID_ID = 100L;

    @BeforeEach
    void setUpAll() {

        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        userService = new UserService(mapper, userRepository);

        User user1 = User.builder()
                .id(VALID_ID)
                .name("name1")
                .email("email1")
                .password("password1")
                .build();

        //getUser
        given(userRepository.findById(VALID_ID)).willReturn(Optional.of(user1));
        //given(userRepository.findById(INVALID_ID)).willThrow(UserNotFoundException.class);

        given(userRepository.save(any(User.class))).will(invocation -> {
            User user = invocation.getArgument(0);
            return User.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .build();
        });
    }

    @Nested
    @DisplayName("createUser 메소드는")
    class Describe_createUser {

        @Test
        @DisplayName("생성한 사용자를 리턴한다.")
        void it_returns_a_cretaed_user() {
            //Arrange
            String name = "name";
            String email = "email";
            String password = "password";
            UserData source = UserData.builder()
                    .name(name)
                    .email(email)
                    .password(password)
                    .build();

            //Act
            UserData userData = userService.createUser(source);

            //Assert
            verify(userRepository).save(any(User.class));

            assertThat(userData.getName()).isEqualTo(name);
            assertThat(userData.getEmail()).isEqualTo(email);
            assertThat(userData.getPassword()).isEqualTo(password);
        }
    }

    @Nested
    @DisplayName("getUser 메소드는")
    class Describe_getUser{
        Long id;

        @Nested
        @DisplayName("User 엔티티에 식별자가 있는 경우")
        class Context_with_exsisted_id{

            @BeforeEach
            void setUp() {
                id = VALID_ID;
            }

            @Test
            @DisplayName("식별자에 해당하는 User를 리턴한다.")
            void it_returns_an_user() {
                User foundUser = userService.getUser(id);

                assertThat(foundUser.getName()).isEqualTo("name1");
                assertThat(foundUser.getEmail()).isEqualTo("email1");
            }
        }

        @Nested
        @DisplayName("User 엔티티에 식별자가 없는 경우")
        class Context_with_not_exsisted_id{

            @BeforeEach
            void setUp() {
                id = INVALID_ID;
            }

            @Test
            @DisplayName("UserNotFoundException를 던진다.")
            void it_throws_UserNotFoundException() {
                assertThatThrownBy(() -> assertThat(userService.getUser(id)))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    class Describe_updateUser{
        Long id;

        UserData updateParam = UserData.builder()
                .name("updateName")
                .email("updateEmail")
                .password("updatePassword")
                .build();


        @Nested
        class Context_with_exsisted_id{

            @BeforeEach
            void setUp() {
                id = VALID_ID;
            }

            @Test
            void it_returns_an_updated_user() {
                //Arrange
                User foundUser = userService.getUser(id);

                assertThat(foundUser.getId()).isEqualTo(VALID_ID);
                assertThat(foundUser.getName()).isEqualTo("name1");

                //Ast
                UserData updatedUser = userService.updateUser(id, updateParam);

                //Assert
                assertThat(updatedUser.getId()).isEqualTo(VALID_ID);
                assertThat(updatedUser.getName()).isEqualTo("updateName");
            }
        }

        @Nested
        class Context_with_not_exsisted_id{

            @BeforeEach
            void setUp() {
                id = INVALID_ID;
            }

            @Test
            void it_throws_UserNotFoundException() {
                assertThatThrownBy(() -> assertThat(userService.updateUser(id, updateParam)))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }


    @Nested
    class Describe_deleteUser{
        Long id;

        @Nested
        class Context_with_exsisted_id{

            @BeforeEach
            void setUp() {
                id = VALID_ID;
            }

            @Test
            void it_returns_an_user() {
                UserData deletedUser = userService.deleteUser(id);

                assertThat(deletedUser.getName()).isEqualTo("name1");
                assertThat(deletedUser.getEmail()).isEqualTo("email1");
            }
        }

        @Nested
        class Context_with_not_exsisted_id{

            @BeforeEach
            void setUp() {
                id = INVALID_ID;
            }

            @Test
            void it_throws_UserNotFoundException() {
                assertThatThrownBy(() -> assertThat(userService.deleteUser(id)))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

}
