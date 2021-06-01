package com.codesoom.assignment.application;


import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserDto;
import com.codesoom.assignment.exception.NotFoundUserException;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserService 클래스")
class UserServiceTest {

    private Mapper mapper;
    private UserRepository userRepository;
    private UserService userService;

    private UserDto sampleUserDto;
    private User sampleUser;
    final private Long EXISTENT_ID = 1L;
    final private Long NON_EXISTENT_ID = -1L;

    @BeforeEach
    void setup() {
        this.mapper = DozerBeanMapperBuilder.buildDefault();
        this.userRepository = mock(UserRepository.class);
        this.userService = new UserService(mapper, userRepository);
        setupFixture();
    }

    void setupFixture() {
        sampleUserDto = UserDto.builder()
                .id(EXISTENT_ID)
                .name("name1")
                .email("email1")
                .password("password1")
                .build();
        sampleUser = User.builder()
                .id(sampleUserDto.getId())
                .name(sampleUserDto.getName())
                .email(sampleUserDto.getEmail())
                .password(sampleUserDto.getPassword())
                .build();

        given(userRepository.findAll())
                .willReturn(List.of(sampleUser));

        given(userRepository.findById(EXISTENT_ID))
                .willReturn(Optional.ofNullable(sampleUser));

        given(userRepository.findById(NON_EXISTENT_ID))
                .willThrow(EmptyResultDataAccessException.class);

        willThrow(EmptyResultDataAccessException.class)
                .given(userRepository).deleteById(NON_EXISTENT_ID);

        given(userRepository.save(any(User.class)))
                .will(invocation -> {
                    User source = invocation.getArgument(0);
                    return User.builder()
                            .id(EXISTENT_ID + 1)
                            .name(source.getName())
                            .email(source.getEmail())
                            .password(source.getPassword())
                            .build();
                });
    }

    @Nested
    @DisplayName("createUser 메소드는")
    class Describe_of_createUser {

        @Nested
        @DisplayName("유저 정보가 주어지면")
        class Context_of_valid_user {

            private UserDto givenUserDto;

            @BeforeEach
            void setup() {
                givenUserDto = sampleUserDto;
            }

            @Test
            @DisplayName("유저를 생성하고, 생성한 유저를 반환한다")
            void it_creates_and_returns_user() {
                User user = userService.createUser(givenUserDto);

                verify(userRepository).save(any(User.class));

                assertThat(user.getName()).isEqualTo(givenUserDto.getName());
            }
        }
    }

    @Nested
    @DisplayName("updateUser 메소드는")
    class Describe_of_updateUser {

        @Nested
        @DisplayName("유저 정보가 주어지면")
        class Context_of_valid_user {

            private UserDto givenUserDto;

            @BeforeEach
            void setup() {
                givenUserDto = UserDto.builder()
                        .id(EXISTENT_ID)
                        .name("updatedName")
                        .email("updatedEmail")
                        .password("updatedPassword")
                        .build();
            }

            @Test
            @DisplayName("유저를 갱신하고, 갱신한 유저를 반환한다")
            void it_updates_and_returns_user() {
                User user = userService.updateUser(EXISTENT_ID, givenUserDto);

                assertThat(user.getName()).isEqualTo(givenUserDto.getName());
                verify(userRepository).findById(EXISTENT_ID);
                verify(userRepository).save(any(User.class));
            }
        }
    }

    @Nested
    @DisplayName("deleteUser 메소드는")
    class Describe_of_deleteUser {

        @Nested
        @DisplayName("존재하는 유저의 id가 주어지면")
        class Context_of_existent_id {

            private Long givenId;

            @BeforeEach
            void setup() {
                this.givenId = EXISTENT_ID;
            }

            @Test
            @DisplayName("유저를 삭제한다")
            void it_removes_user() {
                userService.deleteUser(givenId);

                verify(userRepository).deleteById(givenId);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 유저의 id가 주어지면")
        class Context_of_non_existent_id {

            private Long givenId;

            @BeforeEach
            void setup() {
                this.givenId = NON_EXISTENT_ID;
            }

            @Test
            @DisplayName("유저를 찾지 못 했다는 예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> userService.deleteUser(givenId))
                        .isInstanceOf(NotFoundUserException.class);
            }
        }
    }
}
