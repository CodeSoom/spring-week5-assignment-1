package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("UserService 클래스 테스트")
class UserServiceTest {
    private UserService userService;

    private UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        userService = new UserService(mapper, userRepository);

        User user = User.builder()
                .id(1L)
                .name("mikekang")
                .email("test@github.com")
                .password("qwer1234")
                .build();

        given(userRepository.findAll()).willReturn(List.of(user));

        given(userRepository.findById(1L)).willReturn(Optional.of(user));
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
        User user = userService.getUser(1L);

        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("mikekang");

    }
}