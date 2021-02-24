package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
                .id(1L)
                .name(source.getName())
                .email(source.getEmail())
                .password(source.getPassword())
                .build();
        });

    }

    @DisplayName("서비스가 저장소에 생성 요청을 하면, 새로운 유저가 생성된다.")
    @Test
    void test1() {
        userRepository.save(user);

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("홍길동");
        assertThat(user.getEmail()).isEqualTo("hong@gmail.com");
        assertThat(user.getPassword()).isEqualTo("1234");

        verify(userRepository).save(any(User.class));
    }

    @DisplayName("")
    @Test
    void test2() {

    }

    @DisplayName("")
    @Test
    void test3() {

    }

    @DisplayName("")
    @Test
    void test4() {

    }

    @DisplayName("")
    @Test
    void test5() {

    }

    @Test
    void test6() {

    }
}
