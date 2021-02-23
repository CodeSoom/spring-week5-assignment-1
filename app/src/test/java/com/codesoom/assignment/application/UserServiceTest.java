package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserDto;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

//TODO 수정시 정보가 없는경우 Validation 추가되지 않음
class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository = mock(UserRepository.class);

    private User user;

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        userService = new UserService(mapper, userRepository);

        user = User.builder()
                .id(1L)
                .name("양승인")
                .email("rhfpdk92@naver.com")
                .password("1234")
                .build();
    }

    @Nested
    @DisplayName("createUser()는")
    class Describe_createUser {

        @Nested
        @DisplayName("UserDto를 User로 변경하고")
        class Context_change_userdto_to_user {

            User gildong = User.builder()
                    .id(2L)
                    .name("홍길동")
                    .email("hong@naver.com")
                    .password("1234")
                    .build();

            @BeforeEach
            void setUp() {
                given(userRepository.save(any(User.class)))
                        .willReturn(gildong);
            }

            @Test
            @DisplayName("생성된 회원을 리턴한다.")
            void it_return_the_created_user() {
                UserDto createduser = userService.createUser(new UserDto(gildong));
                assertThat(createduser.getName()).isEqualTo("홍길동");
            }
        }
    }

    @Nested
    @DisplayName("updateUser()는 ")
    class Describe_updateUser {
        @Nested
        @DisplayName("id에 해당하는 회원이 존재하면")
        class Context_exist_id {
            User source;
            UserDto userDto;

            @BeforeEach
            void setUp() {
                source = User.builder()
                        .id(1L)
                        .name("새유저")
                        .email("new@naver.com")
                        .password("1234")
                        .build();

                userDto = new UserDto(source);
                given(userRepository.findById(1L)).willReturn(Optional.of(user));
                given(userRepository.save(source)).willReturn(source);
            }

            @Test
            @DisplayName("수정된 회원을 반환한다")
            void it_return_updated_user() {
                UserDto updatedUser = userService.updateUser(1L, this.userDto);
                verify(userRepository).findById(1L);

                assertThat(updatedUser.getName()).isEqualTo("새유저");
            }
        }

        @Nested
        @DisplayName("존재하지 않는 id로 회원을 조회하면")
        class Context_does_not_exist_id {
            @Test
            @DisplayName("UserNotFoundException을 던진다")
            void it_return_user_not_found_exception() {
                assertThrows(UserNotFoundException.class, () -> userService.updateUser(100L, new UserDto()));

                verify(userRepository).findById(100L);
            }
        }
    }
}
