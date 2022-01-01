package com.codesoom.assignment.application;

import com.codesoom.assignment.UserEmailDuplicationException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserRegisterationData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    private static final String EXISTED_EMAIL_ADDRESS = "exiseted@gmail.com";
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
}
