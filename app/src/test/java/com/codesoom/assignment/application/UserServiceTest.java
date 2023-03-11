package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserRegistrationData;
import com.codesoom.assignment.exception.UserEmailDuplcationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    
    private static final String EXIST_EMAIL = "EXIST_EMAIL@test.com";
    private static final String EMAIL = "test@test.com";

    @BeforeEach
    void setUp(){
        given(userRepository.save(any(User.class))).will(invocation -> {
            User source = invocation.getArgument(0);
            return User.builder()
                    .id(13L)
                    .name(source.getName())
                    .email(source.getEmail())
                    .password(source.getPassword())
                    .build();
        });

        given(userRepository.existsByEmail(EXIST_EMAIL)).willThrow(new UserEmailDuplcationException(EXIST_EMAIL));
    }

    @Test
    @DisplayName("registerUser")
    public void registerUser() throws Exception {
        UserRegistrationData userRegistrationData = UserRegistrationData.builder()
                .email(EMAIL)
                .name("Tester")
                .password("test")
                .build();

        User user = userService.registerUser(userRegistrationData);

        assertThat(user.getName()).isEqualTo("Tester");
        assertThat(user.getId()).isEqualTo(13L);

        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("registerUser")
    public void registerUserWithDuplicatedEmail() throws Exception {
        UserRegistrationData userRegistrationData = UserRegistrationData.builder()
                .email(EXIST_EMAIL)
                .name("Tester")
                .password("test")
                .build();

        assertThatThrownBy(()->userService.registerUser(userRegistrationData))
                .isInstanceOf(UserEmailDuplcationException.class)
                .hasMessageContaining("User email is already existed"+userRegistrationData.getEmail());


        verify(userRepository).existsByEmail(EXIST_EMAIL);
    }

}