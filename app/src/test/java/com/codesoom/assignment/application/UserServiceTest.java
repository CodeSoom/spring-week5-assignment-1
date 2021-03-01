package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserService 클래스 테스트")
class UserServiceTest {
    private UserService userService;

    private UserRepository userRepository = mock(UserRepository.class);

    private Long ExistedId = 1L;
    private Long NotExistedId = 1000L;

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        userService = new UserService(mapper, userRepository);

        User user = User.builder()
                .id(ExistedId)
                .name("mikekang")
                .email("test@github.com")
                .password("qwer1234")
                .build();

        given(userRepository.findAll()).willReturn(List.of(user));

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        given(userRepository.save(any(User.class))).will(invocation -> {
            User source = invocation.getArgument(0);
            return User.builder()
                    .id(2L)
                    .name(source.getName())
                    .email(source.getEmail())
                    .password(source.getPassword())
                    .build();
        });
    }

    @Test
    void getUsersWithNoUser() {
        given(userRepository.findAll()).willReturn(List.of());

        assertThat(userService.getUsers()).isEmpty();
    }

    @Test
    @DisplayName("getUsers는 저장된 회원이 있다면 회원 목록을 리턴한다")
    void getUsers() {
        List<User> users = userService.getUsers();

        assertThat(users).isNotEmpty();

        User user = users.get(0);

        assertThat(user.getName()).isEqualTo("mikekang");

    }

    @Test
    @DisplayName("getUserWithExistedId 는 존재하는 특정 회원을 리턴한다.")
    void getUserWithExistedId() {
        User user = userService.getUser(ExistedId);

        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("mikekang");

    }

    @Test
    @DisplayName("getUserWithNotExistedId는 존재하지 않을때 예외를 리턴한다.")
    void getUserWithNotExistedId() {
        assertThatThrownBy(() -> userService.getUser(NotExistedId))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void createUser() {
        UserData userData = UserData.builder()
                .name("mikekang")
                .email("test@github.com")
                .password("qwer1234")
                .build();

        User user = userService.createUser(userData);

        verify(userRepository).save(any(User.class));

        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getName()).isEqualTo("mikekang");
        assertThat(user.getEmail()).isEqualTo("test@github.com");
        assertThat(user.getPassword()).isEqualTo("qwer1234");

    }

    @Test
    void updateUserWithExistedId() {
        UserData userData = UserData.builder()
                .name("jason")
                .email("test@github.com")
                .password("qwer1234")
                .build();

        User user = userService.updateUser(1L, userData);

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("jason");
    }

    @Test
    void updateUserWithNotExistedId() {
        UserData userData = UserData.builder()
                .name("jason")
                .email("test@gitbhub.com")
                .password("qwer1234")
                .build();

        assertThatThrownBy(() -> userService.updateUser(1000L, userData))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void deleteWithExistedId() {
        userService.deleteUser(1L);

        verify(userRepository).delete(any(User.class));
    }

    @Test
    void deleteUserWithNotExistedId() {
        assertThatThrownBy(() -> userService.deleteUser(1000L))
                .isInstanceOf(UserNotFoundException.class);
    }
}