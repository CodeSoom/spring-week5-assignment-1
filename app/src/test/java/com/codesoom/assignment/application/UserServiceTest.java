package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserService 클래스")
class UserServiceTest {

    private UserService userService;

    private UserRepository userRepository;

    List<User> users = new ArrayList<>();

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);

        User user = User.builder()
                .name("Hyuk")
                .password("!234")
                .email("pjh0819@naver.com")
                .build();

        IntStream.range(0, 5).forEach(i -> {
            user.setId(Long.valueOf(i));
            users.add(user);
        });
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class createUser_메소드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록할_User가_주어진다면 {

            User givenUser;
            Long givenId = 1L;

            @BeforeEach
            void prepare() {
                givenUser = users.get(0);
                given(userRepository.save(any(User.class))).will(invocation -> {
                    User user = invocation.getArgument(0);
                    user.setId(givenId);
                    return user;
                });
            }

            @Test
            @DisplayName("User 객체를 생성한다")
            void User를_생성하고_리턴한다() {
                User user = userService.createUser(givenUser);

                assertThat(user.getName()).isEqualTo(givenUser.getName());
                assertThat(user.getEmail()).isEqualTo(givenUser.getEmail());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class updateUser_메소드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_User의_id와_수정할_User가_주어진다면 {

            User givenUser;
            Long givenId = 1L;

            @BeforeEach
            void prepaer() {
                givenUser = users.get(0);
                givenUser.setName("Update Hyuk");

                given(userRepository.findById(eq(givenId))).willReturn(Optional.of(givenUser));
                given(userRepository.save(any(User.class))).will(invocation -> {
                    User user = invocation.getArgument(1);
                    user.setId(givenId);
                    return user;
                });
            }

            @Test
            void 해당_id의_User를_수정하고_리턴한다() {
                User user = userService.updateUser(givenId, givenUser);

                verify(userRepository).findById(givenId);

                assertThat(user).isNotNull();
                assertThat(user).isEqualTo(user.getName());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class deleteUser_메소드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_User의_id가_주어진다면 {

            Long givenId = 1L;

            @BeforeEach
            void prepare() {
                given(userRepository.findById(givenId)).willReturn(Optional.of(users.get(0)));
            }

            @Test
            void 등록된_User를_삭제하고_빈값이_리턴한다() {
                userService.deleteUser(givenId);

                verify(userRepository).findById(givenId);
                verify(userRepository).delete(any(User.class));
            }
        }
    }
}
