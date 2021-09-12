package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserPostDto;
import com.codesoom.assignment.dto.UserUpdateDto;
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

    private UserPostDto userDtoFixture;
    private UserUpdateDto newUserDtoFixture;
    private User userFixture;

    @BeforeEach
    void setupService() {
        userRepository = mock(UserRepository.class);

        userService = new UserService(userRepository);
    }

    @BeforeEach
    void setupFixtures() {
        userDtoFixture = UserPostDto.builder()
                .name("난난")
                .email("nana@gmail.com")
                .password("nananana")
                .build();

        newUserDtoFixture = UserUpdateDto.builder()
                .name("단단")
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
                given(userRepository.findById(1L)).willReturn(Optional.of(userFixture));
            }

            @Test
            @DisplayName("returns an updated user")
            void returnsUpdatedUser() {
                User updatedUser = userService.updateUser(1L, newUserDtoFixture);

                verify(userRepository).findById(1L);

                assertThat(updatedUser.getId()).isEqualTo(userFixture.getId());
                assertThat(updatedUser.getEmail()).isEqualTo(userFixture.getEmail());
                assertThat(updatedUser.getName()).isEqualTo(newUserDtoFixture.getName());
                assertThat(updatedUser.getPassword()).isEqualTo(newUserDtoFixture.getPassword());
            }
        }

        @Nested
        @DisplayName("with non existent id")
        class WithNotExistentId {
            @BeforeEach
            void setup() {
                given(userRepository.findById(2L))
                        .willReturn(Optional.empty());
            }

            @Test
            @DisplayName("throws UserNotFoundException")
            void throwsUserNotFoundException() {
                assertThatThrownBy(() -> userService.updateUser(2L, newUserDtoFixture))
                        .isInstanceOf(UserNotFoundException.class)
                        .hasMessageContaining("id가 2 인 사용자를 찾지 못했기 떄문에 사용자 정보를 업데이트하지 못했습니다.");
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
                given(userRepository.findById(1L))
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
                given(userRepository.findById(2L))
                        .willReturn(Optional.empty());
            }

            @Test
            @DisplayName("throws UserNotFoundException")
            void throwsUserNotFoundException() {
                assertThatThrownBy(() -> userService.deleteUser(2L))
                        .isInstanceOf(UserNotFoundException.class)
                        .hasMessageContaining("id가 2 인 사용자를 찾지 못했기 떄문에 사용자 정보를 삭제하지 못했습니다.");
            }
        }
    }
}
