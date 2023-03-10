package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.fixture.UserFixture;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import org.junit.jupiter.api.BeforeEach;
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

    @Test
    void createUser() {
        UserData userData = UserFixture.UPDATE_USER.getUserData();

        User user = userService.createUser(userData);

        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getName()).isEqualTo("앙김홍집");
        assertThat(user.getEmail()).isEqualTo("hongzip@naver.com");
        assertThat(user.getPassword()).isEqualTo("123123");

    }

    @Test
    void updateUserWithExistedId() {
        UserData userData = UserFixture.UPDATE_USER.getUserData();

        User user = userService.updateUser(DEFAULT_ID, userData);

        assertThat(user.getId()).isEqualTo(DEFAULT_ID);
        assertThat(user.getName()).isEqualTo("앙김홍집");
    }

    @Test
    void updateUserWithNotExistedId() {
        UserData userData = UserFixture.UPDATE_USER.getUserData();

        assertThatThrownBy(() -> userService.updateUser(INVALID_ID, userData))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void deleteUserWithExistedId() {
        userService.deleteUser(DEFAULT_ID);

        verify(userRepository).delete(any(User.class));
    }

    @Test
    void deleteUserWithNotExistedId() {
        assertThatThrownBy(() -> userService.deleteUser(INVALID_ID))
                .isInstanceOf(UserNotFoundException.class);
    }
}