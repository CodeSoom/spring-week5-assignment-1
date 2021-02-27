package com.codesoom.assignment.user.application;

import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.domain.UserRepository;
import com.codesoom.assignment.user.dto.UserData;
import com.codesoom.assignment.user.dto.UserSaveRequestDto;
import com.codesoom.assignment.user.dto.UserUpdateRequestDto;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@DisplayName("UserService 클래스")
@ExtendWith(MockitoExtension.class)
class UserServiceTest2 {
    private static final Long USER1_ID = 1L;
    private static final String USER1_NAME = "user1";
    private static final String USER1_PASSWORD = "pass1";
    private static final String USER1_EMAIL = "user1@test.com";
    private static final Long USER2_ID = 2L;
    private static final String USER2_NAME = "user2";
    private static final String USER2_PASSWORD = "pass2";
    private static final String USER2_EMAIL = "user2@test.com";
    private static final Long NOT_EXIST_ID = -1L;

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user1;
    private User user2;
    private List<User> users;

    private UserData userData1;
    private UserData userData2;

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        userService = new UserService(userRepository, mapper);
        setUpFixtures();
    }

    void setUpFixtures() {
        user1 = User.builder()
                .id(USER1_ID)
                .name(USER1_NAME)
                .email(USER1_EMAIL)
                .password(USER1_PASSWORD)
                .build();

        user2 = User.builder()
                .id(USER2_ID)
                .name(USER2_NAME)
                .email(USER2_EMAIL)
                .password(USER2_PASSWORD)
                .build();

        userData1 = UserData.of(user1);
        userData2 = UserData.of(user2);

        users = Arrays.asList(user1, user2);
    }

    @Test
    @DisplayName("getUsers 메서드는 등록된 모든 사용자 정보를 리턴한다")
    void getUsers() {
        given(userRepository.findAll()).willReturn(users);

        List<UserData> usersInformation = userService.getUsers();

        assertThat(usersInformation).containsExactly(userData1, userData2);
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("getUser 메서드는 등록된 사용자 id에 해당하는 사용자 정보를 리턴한다")
    void getUserWithValidId() {
        given(userRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(user1));

        UserData actual = userService.getUser(anyLong());

        assertAll(
                () -> assertThat(actual).isEqualTo(userData1),
                () -> assertThat(actual.getId()).isEqualTo(USER1_ID)
        );
        verify(userRepository).findById(anyLong());
    }

    @Test
    @DisplayName("getUser 메서드는 등록되지 않은 사용자 id로 조회시 예외를 던진다")
    void getUserWithInValidId() {
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userService.getUser(NOT_EXIST_ID));
        verify(userRepository).findById(NOT_EXIST_ID);
    }

    @Test
    @DisplayName("updateUser 메서드는 등록된 id로 사용자정보를 갱신할 수 있다")
    void updateUserWithValidId() {
        given(userRepository.findById(anyLong()))
                .willReturn(Optional.of(user1));
        UserUpdateRequestDto expected = UserUpdateRequestDto.builder()
                .email(USER2_EMAIL)
                .name(USER2_NAME)
                .password(USER2_PASSWORD)
                .build();

        UserData actual = userService.updateUser(anyLong(), expected);

        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
                () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword())
        );
    }

    @Test
    @DisplayName("updateUser 메서드는 등록되지 않은 사용자 id로 사용자정보를 갱신시 예외를 던진다.")
    void updateUserWithInValidId() {
        UserUpdateRequestDto requestDto = UserUpdateRequestDto.builder().build();

        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userService.updateUser(NOT_EXIST_ID, requestDto));
    }

    @Test
    @DisplayName("deleteUser 메서드는 등록된 사용자 id에 해당하는 사용자를 삭제한다")
    void deleteUserWithValidId() {
        given(userRepository.findById(anyLong()))
                .willReturn(Optional.of(user1));

        userService.deleteUser(anyLong());

        verify(userRepository).delete(any(User.class));
    }

    @Test
    @DisplayName("deleteUser 메서드는 등록되지 사용자 id로 사용자 삭제시 예외를 던진다")
    void deleteUserWithInValidId() {
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userService.deleteUser(NOT_EXIST_ID));

        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    @DisplayName("creatUser 메서드는 사용자를 새로 등록할 수 있다")
    void createUser() {
        given(userRepository.save(any(User.class)))
                .willReturn(user1);
        UserSaveRequestDto expected = UserSaveRequestDto.builder()
                .email(USER1_EMAIL)
                .name(USER1_NAME)
                .password(USER1_PASSWORD)
                .build();

        UserData actual = userService.createUser(expected);

        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword()),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail())
        );
        verify(userRepository).save(any(User.class));
    }
}
