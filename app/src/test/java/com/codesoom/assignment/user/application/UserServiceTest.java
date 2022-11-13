package com.codesoom.assignment.user.application;

import com.codesoom.assignment.user.exception.UserNotFoundException;
import com.codesoom.assignment.user.adapter.out.persistence.FakeInMemoryUserPersistenceAdapter;
import com.codesoom.assignment.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.codesoom.assignment.support.IdFixture.ID_MAX;
import static com.codesoom.assignment.support.UserFixture.USER_1;
import static com.codesoom.assignment.support.UserFixture.USER_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("UserService 테스트")
class UserServiceTest {

    private UserService userService;
    private FakeInMemoryUserPersistenceAdapter fakeUserRepository;

    @BeforeEach
    void setUpVariable() {
        fakeUserRepository = new FakeInMemoryUserPersistenceAdapter();
        userService = new UserService(fakeUserRepository);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class createUser_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 새로운_회원이_주어지면 {
            @Test
            @DisplayName("회원을 저장하고 리턴한다")
            void it_returns_user() {
                User user = userService.createUser(USER_1.생성_요청_데이터_생성());

                assertThat(user.getName()).isEqualTo(USER_1.NAME());
                assertThat(user.getEmail()).isEqualTo(USER_1.EMAIL());
                assertThat(user.getPassword()).isEqualTo(USER_1.PASSWORD());
            }

            @Test
            @DisplayName("회원이 1 증가한다")
            void it_returns_user_size() {
                // TODO: service 목록조회 기능 개발 및 테스트 완료 시 교체 필요
                int oldSize = fakeUserRepository.findAll().size();

                userService.createUser(USER_2.생성_요청_데이터_생성());

                int newSize = fakeUserRepository.findAll().size();

                assertThat(newSize - oldSize).isEqualTo(1);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class null이_주어지면 {
            @Test
            @DisplayName("예외를 던진다")
            void it_returns_null_pointer_exception() {
                assertThatThrownBy(() -> userService.createUser(null))
                        .isInstanceOf(NullPointerException.class);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class updateUser_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어지면 {
            private User userFixture;

            @BeforeEach
            void setUpCreateFixture() {
                userFixture = userService.createUser(USER_1.생성_요청_데이터_생성());
            }

            @Test
            @DisplayName("회원을 수정하고 리턴한다")
            void it_returns_user() {
                User user = userService.updateUser(userFixture.getId(), USER_2.수정_요청_데이터_생성());

                assertThat(user).isNotNull();
                assertThat(user.getId()).isEqualTo(userFixture.getId());
                assertThat(user.getName()).isEqualTo(USER_2.NAME());
                assertThat(user.getEmail()).isEqualTo(USER_2.EMAIL());
                assertThat(user.getPassword()).isEqualTo(USER_2.PASSWORD());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어지면 {
            @Test
            @DisplayName("예외를 던진다")
            void it_returns_exception() {
                assertThatThrownBy(() -> userService.updateUser(ID_MAX.value(), USER_2.수정_요청_데이터_생성()))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class deleteUser_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어지면 {
            private User userFixture;

            @BeforeEach
            void setUpCreateFixture() {
                userFixture = userService.createUser(USER_1.생성_요청_데이터_생성());
            }

            @Test
            @DisplayName("회원을 삭제한다")
            void it_delete_user() {
                assertThat(fakeUserRepository.findById(userFixture.getId()))
                        .isNotEmpty();

                userService.deleteUser(userFixture.getId());

                assertThat(fakeUserRepository.findById(userFixture.getId()))
                        .isEmpty();
            }

            @Test
            @DisplayName("회원이 1 감소한다")
            void it_delete_user_size() {
                int oldSize = fakeUserRepository.findAll().size();

                userService.deleteUser(userFixture.getId());

                int newSize = fakeUserRepository.findAll().size();

                assertThat(newSize - oldSize).isEqualTo(-1);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어지면 {
            @Test
            @DisplayName("예외를 던진다")
            void it_returns_exception() {
                assertThatThrownBy(() -> userService.deleteUser(ID_MAX.value()))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
