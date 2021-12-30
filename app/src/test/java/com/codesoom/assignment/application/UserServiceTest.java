package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserService 테스트")
class UserServiceTest {
    private UserService userService;

    private final UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        userService = new UserService(mapper, userRepository);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class create_메소드는 {

        private UserData source = UserData.builder()
            .name("홍길동").email("test@test.com").password("asdqwe1234").build();
        private User createdUser;

        @BeforeEach
        void setUpCreation() {
            given(userRepository.save(any(User.class))).will(invocation -> {
                User source = invocation.getArgument(0);
                return User.builder()
                        .name(source.getName())
                        .email(source.getEmail())
                        .password(source.getPassword())
                        .build();
            });
        }

        @Test
        @DisplayName("새로운 회원을 생성하여 리턴한다")
        void create() {
            createdUser = userService.create(source);

            verify(userRepository).save(any(User.class));

            assertThat(createdUser.getName()).isEqualTo("홍길동");
            assertThat(createdUser.getEmail()).isEqualTo("test@test.com");
            assertThat(createdUser.getPassword()).isEqualTo("asdqwe1234");
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class update_메소드는 {
        private UserData source = UserData.builder()
                .name("철수").email("cjftn@test.com").password("asdfg").build();
        private User updatedUser;

        @BeforeEach
        void setUp() {
            User user = User.builder()
                    .name("홍길동")
                    .email("test@test.com")
                    .password("asdqwe1234")
                    .build();

            given(userRepository.findById(1L)).willReturn(Optional.of(user));
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하는_ID라면 {

            @Test
            @DisplayName("수정된 회원 정보를 리턴한다")
            void 수정된_회원_정보를_리턴한다() {
                updatedUser = userService.update(1L, source);

                verify(userRepository).findById(1L);

                assertThat(updatedUser.getName()).isEqualTo("철수");
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하지_않는_ID라면 {

            @Test
            @DisplayName("예외를 던진다")
            void 예외를_던진다() {
                assertThatThrownBy(() -> userService.update(1000L, source))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class delete_메소드는 {

        private User source = User.builder()
                .name("철수").email("cjftn@test.com").password("asdfg").build();
        private User deletedUser;

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하는_ID라면 {

            @Test
            @DisplayName("회원 정보를 삭제하고 삭제된 회원을 리턴한다")
            void 회원_정보를_삭제하고_삭제된_회원을_리턴한다() {
                given(userRepository.findById(1L)).willReturn(Optional.of(source));

                deletedUser = userService.delete(1L);

                verify(userRepository).findById(1L);
                verify(userRepository).delete(source);

                assertThat(deletedUser.getName()).isEqualTo("철수");
                assertThat(deletedUser.getEmail()).isEqualTo("cjftn@test.com");
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하지_않는_ID라면 {

            @Test
            @DisplayName("예외를 던진다")
            void 예외를_던진다() {
                assertThatThrownBy(() -> userService.delete(1000L))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}