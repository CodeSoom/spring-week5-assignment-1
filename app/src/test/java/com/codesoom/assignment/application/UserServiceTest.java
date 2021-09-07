package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

@Service
class UserServiceTest {
    private UserService userService;

    private UserRepository userRepository;

    private UserDto userDtoFixture;
    private UserDto newUserDtoFixture;
    private User userFixture;

    private final Long EXISTENT_ID = 1L;
    private final Long NON_EXISTENT_ID = 1000L;

    @BeforeEach
    void setupService() {
        userRepository = mock(UserRepository.class);

        userService = new UserService(userRepository);
    }

    @BeforeEach
    void setupFixtures() {
        userDtoFixture = UserDto.builder()
                .name("난난")
                .email("nana@gmail.com")
                .password("nananana")
                .build();

        newUserDtoFixture = UserDto.builder()
                .name("단단")
                .email("dada@gmail.com")
                .password("dadadada")
                .build();

        userFixture = User.builder()
                .name("난난")
                .email("nana@gmail.com")
                .password("nananana")
                .build();
    }

    @Nested
    @DisplayName("Create Service")
    class CreateService {
        @Test
        @DisplayName("returns a created user")
        void returnsCreatedUser() {
            User user = userService.createUser(userDtoFixture);

            verify(userRepository).save(any(User.class));

            assertThat(user.getName()).isEqualTo(userDtoFixture.getName());
            assertThat(user.getEmail()).isEqualTo(userDtoFixture.getEmail());
            assertThat(user.getPassword()).isEqualTo(userDtoFixture.getPassword());
        }
    }

    @Nested
    @DisplayName("Update Service")
    class UpdateService {
        @Nested
        @DisplayName("with an existent id")
        class WithExistentId {
            @BeforeEach
            void setup() {
                given(userRepository.findById(EXISTENT_ID)).willReturn(Optional.of(userFixture));
            }

            @Test
            @DisplayName("returns an updated user")
            void returnsUpdatedUser() {
                User updatedUser = userService.updateUser(EXISTENT_ID, newUserDtoFixture);

                verify(userRepository).findById(EXISTENT_ID);

                assertThat(updatedUser.getId()).isEqualTo(userFixture.getId());
                assertThat(updatedUser.getName()).isEqualTo(newUserDtoFixture.getName());
                assertThat(updatedUser.getEmail()).isEqualTo(newUserDtoFixture.getEmail());
                assertThat(updatedUser.getPassword()).isEqualTo(newUserDtoFixture.getPassword());
            }
        }

        @Nested
        @DisplayName("with non existent id")
        class WithNotExistentId {
            @BeforeEach
            void setup() {
                given(userRepository.findById(NON_EXISTENT_ID))
                        .willReturn(Optional.empty());
            }

            @Test
            @DisplayName("throws UserNotFoundException")
            void throwsUserNotFoundException() {
                assertThatThrownBy(() -> userService.updateUser(NON_EXISTENT_ID, newUserDtoFixture))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("Delete Service")
    class DeleteService {
        @Nested
        @DisplayName("with an existing id")
        class WithExistingId {
            @BeforeEach
            void setup() {
                given(userRepository.findById(EXISTENT_ID))
                        .willReturn(Optional.of(userFixture));
            }

            @Test
            @DisplayName("calls deleteByUserId in userRepository")
            void callsDeleteByUserId() {
                userService.deleteUser(1L);

                verify(userRepository).deleteById(1L);
            }
        }

        @Nested
        @DisplayName("with not existing id")
        class WithNotExistingId {
            @BeforeEach
            void setup() {
                given(userRepository.findById(NON_EXISTENT_ID))
                        .willReturn(Optional.empty());
            }

            @Test
            @DisplayName("throws UserNotFoundException")
            void throwsUserNotFoundException() {
                assertThatThrownBy(() -> userService.deleteUser(NON_EXISTENT_ID))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
