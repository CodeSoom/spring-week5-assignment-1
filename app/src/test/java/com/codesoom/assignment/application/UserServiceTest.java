package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserModificationData;
import com.codesoom.assignment.dto.UserRegistrationData;
import com.codesoom.assignment.exception.NotFoundIdException;
import com.codesoom.assignment.exception.UserEmailDuplcationException;
import com.codesoom.assignment.exception.UserNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DataJpaTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static final String EXIST_EMAIL = "EXIST_EMAIL@test.com";
    private static final String EMAIL = "test@test.com";
    private static final Long DELETED_USERID = 200L;

    @BeforeEach
    void setUp() {
        given(userRepository.save(any(User.class))).will(invocation -> {
            User source = invocation.getArgument(0);
            return User.builder()
                    .id(13L)
                    .name(source.getName())
                    .email(source.getEmail())
                    .password(source.getPassword())
                    .build();
        });

        given(userRepository.existsByEmail(EXIST_EMAIL))
                .willReturn(true);

        given(userRepository.findById(1L)).willReturn(Optional.of(User.builder()
                .id(1L)
                .email(EXIST_EMAIL)
                .name("TESTER")
                .password("password")
                .build()));

        given(userRepository.findById(100L)).willReturn(Optional.empty());

        given(userRepository.findById(DELETED_USERID)).willReturn(Optional.of(User.builder()
                .id(DELETED_USERID)
                .deleted(true)
                .build()));
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

        assertThatThrownBy(() -> userService.registerUser(userRegistrationData))
                .isInstanceOf(UserEmailDuplcationException.class)
                .hasMessageContaining("User email is already existed" + userRegistrationData.getEmail());


        verify(userRepository).existsByEmail(EXIST_EMAIL);
    }

    @Test
    @DisplayName("updateUserWithExistedId")
    public void updateUserWithExistedId() throws Exception {
        //given
        //when
        UserModificationData modificationData = UserModificationData.builder()
                .name("Test")
                .password("update_password")
                .build();
        User user = userService.updateUser(1L, modificationData);

        //Then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getPassword()).isEqualTo("update_password");

        verify(userRepository).findById(eq(1L));
    }

    @Test
    @DisplayName("updateUserWithNotExistedId")
    public void updateUserWithNotExistedId() throws Exception {

        UserModificationData modificationData = UserModificationData.builder()
                .name("Test")
                .password("update_password")
                .build();

        //Then
        assertThatThrownBy(() -> userService.updateUser(1000L, modificationData))
                .isInstanceOf(NotFoundIdException.class)
                .hasMessageContaining("NOT FOUND ID: 1000");

    }

    @Test
    @DisplayName("deleteUserWithExistedId")
    public void deleteUserWithExistedId() throws Exception {
        //given
        //when
        User user = userService.delete(1L);
        //Then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.isDeleted()).isEqualTo(true);
        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("deleteUserWithNotExistedId")
    public void deleteUserWithNotExistedId() throws Exception {
//        userService.delete(100L);
        assertThatThrownBy(() -> userService.delete(100L))
                .isInstanceOf(NotFoundIdException.class)
                .hasMessageContaining("NOT FOUND ID: 100");
        verify(userRepository).findById(100L);
    }

    @Test
    void deleteUserWithDeletedId() {
        Assertions.assertThatThrownBy(() -> userService.delete(DELETED_USERID))
                .isInstanceOf(UserNotFoundException.class);
    }

}