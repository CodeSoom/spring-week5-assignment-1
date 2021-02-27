package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserCreateRequestDto;
import com.codesoom.assignment.dto.UserDto;
import com.codesoom.assignment.dto.UserUpdateRequestDto;
import com.codesoom.assignment.exception.UserNotFoundException;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Transactional
class UserServiceTest {
    private final long EXIST_ID = 1L;
    private final long NOT_EXIST_ID = 100L;
    private UserService userService;
    private UserRepository userRepository = mock(UserRepository.class);

    private User user;

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        userService = new UserService(mapper, userRepository);

        user = User.builder()
                .id(EXIST_ID)
                .name("양승인")
                .email("rhfpdk92@naver.com")
                .password("1234")
                .build();
    }

    @Nested
    @DisplayName("createUser()는")
    class Describe_createUser {
        @BeforeEach
        void setUp() {
            given(userRepository.save(any(User.class)))
                    .willReturn(user);
        }

        @Test
        @DisplayName("생성된 회원을 리턴한다.")
        void it_return_the_created_user() {
            UserDto createdUser = userService.createUser(new UserCreateRequestDto(user));
            assertThat(createdUser.getName()).isEqualTo(user.getName());
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
                        .id(EXIST_ID)
                        .name("새유저")
                        .password("1234")
                        .build();

                userDto = new UserDto(source);
                given(userRepository.findById(EXIST_ID)).willReturn(Optional.of(user));
                given(userRepository.save(source)).willReturn(source);
            }

            @Test
            @DisplayName("수정된 회원을 반환한다")
            void it_return_updated_user() {
                UserDto updatedUser = userService.updateUser(EXIST_ID, new UserUpdateRequestDto(source));
                verify(userRepository).findById(EXIST_ID);

                assertThat(updatedUser.getName()).isEqualTo(source.getName());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 id로 회원을 조회하면")
        class Context_does_not_exist_id {
            @Test
            @DisplayName("UserNotFoundException을 던진다")
            void it_return_user_not_found_exception() {
                assertThrows(UserNotFoundException.class, () -> userService.updateUser(NOT_EXIST_ID, new UserUpdateRequestDto()));

                verify(userRepository).findById(NOT_EXIST_ID);
            }
        }
    }

    @Nested
    @DisplayName("deleteUser()는 ")
    class Describe_deleteUser {
        @Nested
        @DisplayName("id에 해당하는 회원이 존재하면")
        class Context_exist_id {
            @BeforeEach
            void setUp() {
                given(userRepository.findById(EXIST_ID)).willReturn(Optional.of(user));
            }

            @Test
            @DisplayName("장난감을 삭제한다.")
            void it_return_updated_user() {
                userService.deleteUser(EXIST_ID);

                verify(userRepository).delete(user);
            }
        }

        @Nested
        @DisplayName("id에 해당하는 회원이 없으면")
        class Context_does_not_exist_id {
            @Test
            @DisplayName("UserNotFoundException을 던진다")
            void it_return_user_not_found_exception() {
                assertThrows(UserNotFoundException.class, () -> userService.deleteUser(NOT_EXIST_ID));
            }
        }
    }
}
