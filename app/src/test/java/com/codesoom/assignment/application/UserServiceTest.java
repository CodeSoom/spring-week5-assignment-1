package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class UserServiceTest {

    private User user;
    private UserService userService;

    private UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    public void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        userService = new UserService(mapper, userRepository);

        user = User.builder()
            .id(1L)
            .name("홍길동")
            .email("hong@gmail.com")
            .password("1234")
            .build();

        given(userRepository.save(any(User.class))).will(invocation -> {
            User source = invocation.getArgument(0);
            return User.builder()
                .id(2L)
                .name(source.getName())
                .email(source.getEmail())
                .password(source.getPassword())
                .build();
        });


        given(userRepository.delete(eq(1000L))).willThrow(UserNotFoundException.class);
    }

    @DisplayName("서비스가 저장소에 생성 요청을 하면, 새로운 유저가 생성된다.")
    @Test
    void createUser() {
        User createdUser= userRepository.save(user);

        assertThat(createdUser.getId()).isEqualTo(2L);
        assertThat(createdUser.getName()).isEqualTo("홍길동");
        assertThat(createdUser.getEmail()).isEqualTo("hong@gmail.com");
        assertThat(createdUser.getPassword()).isEqualTo("1234");

        verify(userRepository).save(any(User.class));
    }

    @DisplayName("서비스가 저장소에 존재하는 유저에 대해서 수정 요청을 하면, 수정된 유저가 반환된다.")
    @Test
    void updateWithExistedUser() {
        User updatedUser = User.builder()
            .name("임꺽정")
            .email("lim@gmail.com")
            .password("5678")
            .build();

        User user = userService.updateUser(2L, updatedUser);

        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getName()).isEqualTo("임꺽정");
        assertThat(user.getEmail()).isEqualTo("lim@gmail.com");
        assertThat(user.getPassword()).isEqualTo("5678");
    }

    @DisplayName("서비스가 저장소에 존재하지 않는 유저에 대해서 수정 요청을 하면, 예외를 호출합니다.")
    @Test
    void updateWithNonExistedUser() {
        User updatedUser = User.builder()
            .name("임꺽정")
            .email("lim@gmail.com")
            .password("5678")
            .build();

        assertThatThrownBy(() -> userService.updateUser(1000L ,updatedUser))
            .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("서비스가 저장소에 존재하는 유저에 대해서 삭제 요청을 하면, 유저를 삭제합니다.")
    @Test
    void deleteWithExistedUser() {
        userService.deleteUser(1L);
        verify(userRepository).delete(eq(1L));
    }

    @DisplayName("서비스가 저장소에 존재하지 않는 유저에 대해서 삭제 요청을 하면, 예외를 호출합니다.")
    @Test
    void deleteWithNotExistedUser() {
        assertThatThrownBy(() -> userService.deleteUser(1000L))
            .isInstanceOf(UserNotFoundException.class);
    }
}
