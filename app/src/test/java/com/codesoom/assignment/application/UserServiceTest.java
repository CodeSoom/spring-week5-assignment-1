package com.codesoom.assignment.application;

import com.codesoom.assignment.errors.UserEmailDuplicationException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserModificationData;
import com.codesoom.assignment.dto.UserRegisterationData;
import com.codesoom.assignment.errors.UserNotFoundException;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
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

    private static final String EXISTED_EMAIL_ADDRESS = "exiseted@gmail.com";
    private static final Long DELETE_USER_ID = 200L;
    private UserService userService;

    private final UserRepository userRepository =
            mock(UserRepository.class);
    private User user;

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        userService = new UserService(mapper, userRepository);

        given(userRepository.existsByEmail(EXISTED_EMAIL_ADDRESS))
                .willThrow(new UserEmailDuplicationException
                        (EXISTED_EMAIL_ADDRESS));

        given(userRepository.save(any(User.class))).will(invocation -> {
            User source = invocation.getArgument(0);
            User user = User.builder()
                    .id(13L)
                    .email(source.getEmail())
                    .name(source.getName())
                    .build();
            return user;
        });

        given(userRepository.findByIdAndDeletedIsFalse(1L)).willReturn(
                Optional.of(User.builder()
                        .id(1L)
                        .email(EXISTED_EMAIL_ADDRESS)
                        .name("Tester")
                        .password("TEST")
                        .build()));

        given(userRepository.findByIdAndDeletedIsFalse(100L))
                .willReturn(Optional.empty());

        given(userRepository.findByIdAndDeletedIsFalse(DELETE_USER_ID))
                .willReturn(Optional.empty());
    }

    @Test
    void registerUserWithEmail() {
        UserRegisterationData registrationData = UserRegisterationData.builder()
                .email("jihwooon@gmail.com")
                .name("jihwooon")
                .password("test")
                .build();

        User user = userService.registerUser(registrationData);

        assertThat(user.getId()).isEqualTo(13L);
        assertThat(user.getEmail()).isEqualTo("jihwooon@gmail.com");
        assertThat(user.getName()).isEqualTo("jihwooon");

        verify(userRepository).save(any(User.class));

    }

    @Test
    void registerUserWithDuplicatedEmail() {
        UserRegisterationData registrationData = UserRegisterationData.builder()
                .email(EXISTED_EMAIL_ADDRESS)
                .name("jihwooon")
                .password("test")
                .build();

        assertThatThrownBy(() -> userService.registerUser(registrationData))
                .isInstanceOf(UserEmailDuplicationException.class);

        verify(userRepository).existsByEmail(EXISTED_EMAIL_ADDRESS);

    }

    @Test
    void updateUserWithExisteId() {
        UserModificationData modificationData = UserModificationData.builder()
                .name("TEST")
                .password("TEST")
                .build();

        User user = userService.updateUser(1L, modificationData);

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo(EXISTED_EMAIL_ADDRESS);
        assertThat(user.getName()).isEqualTo("TEST");

        verify(userRepository).findByIdAndDeletedIsFalse(1L);
    }


    @Test
    void updateUserWithNotExisteId() {
        UserModificationData modificationData = UserModificationData.builder()
                .name("TEST")
                .password("TEST")
                .build();

        assertThatThrownBy(() -> userService.updateUser(100L, modificationData))
                .isInstanceOf(UserNotFoundException.class);
        verify(userRepository).findByIdAndDeletedIsFalse(100L);
    }


    @Test
    void deleteUserWithExistedId() {
        User user = userService.deleteUser(1L);

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.isDeleted()).isTrue();

        verify(userRepository).findByIdAndDeletedIsFalse(1L);
    }

    @Test
    void deleteUserWithNotExistedId() {
        assertThatThrownBy(() -> userService.deleteUser(100L))
                .isInstanceOf(UserNotFoundException.class);
        verify(userRepository).findByIdAndDeletedIsFalse(100L);
    }


    @Test
    void deleteUserWithDeletedId() {
        assertThatThrownBy(() -> userService.deleteUser(DELETE_USER_ID))
                .isInstanceOf(UserNotFoundException.class);
        verify(userRepository).findByIdAndDeletedIsFalse(DELETE_USER_ID);

    }
}
