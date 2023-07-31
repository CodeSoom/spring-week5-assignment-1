package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.CreateUserData;
import com.codesoom.assignment.dto.UpdateUserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    private UserCreator userCreator;
    private UserDeleter userDeleter;
    private UserUpdater userUpdater;
    private UserRepository userRepository;

    //fixture
    private User user;
    private CreateUserData createUserData;
    private UpdateUserData updateUserData;
    private static final Long EXISTING_ID = 1L;
    private static final Long NOT_EXISTING_ID = 100L;

    private static final String NAME = "dh";
    private static final String EMAIL = "dh@gmail.com";
    private static final String PASSWORD = "1111";
    private static final String UPDATED_NAME = "dhj";
    private static final String UPDATED_PASSWORD = "2222";

    @BeforeEach
    void setup(){
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        userRepository = mock(UserRepository.class);

        userCreator = new UserCreator(userRepository,mapper);
        userUpdater = new UserUpdater(userRepository);
        userDeleter = new UserDeleter(userRepository);

        user = User.builder()
                .id(EXISTING_ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        createUserData = CreateUserData.builder()
                            .name(NAME)
                            .email(EMAIL)
                            .password(PASSWORD)
                            .build();

        updateUserData = UpdateUserData.builder()
                            .name(UPDATED_NAME)
                            .password(UPDATED_PASSWORD)
                            .build();

        given(userRepository.save(any(User.class))).willReturn(user);
        given(userRepository.findById(EXISTING_ID)).willReturn(Optional.of(user));
        given(userRepository.findById(NOT_EXISTING_ID)).willThrow(new UserNotFoundException(NOT_EXISTING_ID));
    }

    @Test
    void createUser() {
        User user = userCreator.create(createUserData);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(EXISTING_ID);
        assertThat(user.getName()).isEqualTo(NAME);
        assertThat(user.getEmail()).isEqualTo(EMAIL);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUserWithExistingId() {
        User updatedUser = userUpdater.update(EXISTING_ID, updateUserData);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getId()).isEqualTo(EXISTING_ID);
        assertThat(updatedUser.getName()).isEqualTo(updateUserData.getName());
        assertThat(updatedUser.getPassword()).isEqualTo(updateUserData.getPassword());

        verify(userRepository).findById(EXISTING_ID);
    }

    @Test
    void updateUserWithNotExistingId() {
        assertThatThrownBy(()->userUpdater.update(NOT_EXISTING_ID, updateUserData))
                .isInstanceOf(UserNotFoundException.class);

        verify(userRepository).findById(NOT_EXISTING_ID);
    }

    @Test
    void deleteUserWithExistingId() {
        userDeleter.delete(EXISTING_ID);
        verify(userRepository).findById(EXISTING_ID);
        verify(userRepository).delete(any(User.class));
    }

    @Test
    void deleteUserWithNotExistingId() {
        assertThatThrownBy(()->userDeleter.delete(NOT_EXISTING_ID))
                .isInstanceOf(UserNotFoundException.class);
        verify(userRepository).findById(NOT_EXISTING_ID);
    }
}
