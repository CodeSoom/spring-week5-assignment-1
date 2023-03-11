package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.fixture.UserFixture;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
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

    private static final Long DEFAULT_ID = 1L;

    private static final Long CREATE_ID = 2L;


    private static final Long INVALID_ID = 1000L;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, DozerBeanMapperBuilder.buildDefault());

        User user = User.builder()
                .id(DEFAULT_ID)
                .name("홍길동")
                .email("hong@naver.com")
                .password("12341234")
                .build();

        given(userRepository.findById(DEFAULT_ID)).willReturn(Optional.of(user));

        given(userRepository.save(any(User.class))).will(invocation -> {
            User source = invocation.getArgument(0);
            return User.builder()
                    .id(CREATE_ID)
                    .name(source.getName())
                    .email(source.getEmail())
                    .password(source.getPassword())
                    .build();
        });
    }

    @Nested
    @DisplayName("유저 생성")
    class CreateUser{
        @Test
        @DisplayName("유저를 생성한다.")
        void createUser() {
            UserData userData = UserFixture.CREATE_USER.getUserData();

            User user = userService.createUser(userData);

            assertThat(user.getId()).isEqualTo(2L);
            assertThat(user.getName()).isEqualTo("앙생성집");
            assertThat(user.getEmail()).isEqualTo("sangzip@naver.com");
            assertThat(user.getPassword()).isEqualTo("12345");

        }
    }


    @Nested
    @DisplayName("유저 수정")
    class UpdateUser{

        @Nested
        @DisplayName("존재하지 않는 유저라면")
        class NotExistUser{
            @Test
            @DisplayName("UserNotFoundException 예외를 던진다")
            void throwsUserNotFoundException() {
                UserData userData = UserFixture.UPDATE_USER.getUserData();

                assertThatThrownBy(() -> userService.updateUser(INVALID_ID, userData))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("존재하는 유저라면")
        class ExistUser{

            @Test
            @DisplayName("수정된 유저정보를 반환한다")
            void updateUserInformation() {
                UserData userData = UserFixture.UPDATE_USER.getUserData();

                User user = userService.updateUser(DEFAULT_ID, userData);

                assertThat(user.getId()).isEqualTo(DEFAULT_ID);
                assertThat(user.getName()).isEqualTo("앙김홍집");
            }
        }
    }

    @Nested
    @DisplayName("유저 삭제")
    class DeleteUser{
        
        @Nested
        @DisplayName("존재하지 않는 유저라면")
        class NotExistUser{
            @Test
            @DisplayName("UserNotFoundException 을 던진다.")
            void throwsUserNotFoundException() {
                assertThatThrownBy(() -> userService.deleteUser(INVALID_ID))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("존재하는 유저라면")
        class ExistUser{
            @Test
            @DisplayName("유저를 삭제한다.")
            void deleteUser() {
                userService.deleteUser(DEFAULT_ID);
                verify(userRepository).delete(any(User.class));
            }
        }
    }
}