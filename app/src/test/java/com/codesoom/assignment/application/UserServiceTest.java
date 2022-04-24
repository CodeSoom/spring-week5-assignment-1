package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("UserService 는")
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    private final User userWithoutId = User.builder()
            .id(null)
            .name("김갑생")
            .email("gabseng@naver.com")
            .password("12341234")
            .build();

    @Nested
    @DisplayName("create() 메서드는")
    class Context_create_method {

        public Context_create_method() {
            userRepository.deleteAll();
        }

        @Nested
        @DisplayName("id가 null 인 User 객체를 받았을 때")
        class Context_with_user {
            @Test
            @DisplayName("리포지토리에 저장하고, User 객체를 리턴한다.")
            void It_saves_user_in_repository_and_returns_user() {
                User user = userService.create(userWithoutId);
                assertThat(user).isNotNull();
                assertThat(user).isInstanceOf(User.class);
                assertThat(userRepository.count()).isEqualTo(1);
            }
        }
    }

    @Nested
    @DisplayName("get() 메서드는")
    class Context_get_method {
        @Nested
        @DisplayName("존재하는 id 를 받았을 때")
        class Context_valid_id {
            private final User user;

            // 한번만 수행은 생성자
            public Context_valid_id() {
                userRepository.deleteAll();
                user = userRepository.save(User.builder()
                        .password("password")
                        .name("name")
                        .email("email")
                        .build());
            }

            @Test
            @DisplayName("id 를 가진 User 를 반환한다.")
            void it_returns_user_having_that_id() {
                User resultUser = userService.get(user.getId());

                assertThat(resultUser).isNotNull();
                assertThat(resultUser.getId()).isEqualTo(user.getId());
                assertThat(resultUser.getEmail()).isEqualTo(user.getEmail());
                assertThat(resultUser.getName()).isEqualTo(user.getName());
                assertThat(resultUser.getPassword()).isEqualTo(user.getPassword());
            }
        }
    }

    @Nested
    @DisplayName("update() 메서드는")
    class Context_update_method {
        @Nested
        @DisplayName("존재하는 id 를 받았을 때")
        class Context_valid_id {
            private final User originUser;

            public Context_valid_id() {
                userRepository.deleteAll();
                originUser = userRepository.save(User.builder()
                        .password("password")
                        .name("name")
                        .email("email")
                        .build());
            }

            @Nested
            @DisplayName("변경사항이 포함된 User 객체를 받는다면")
            class Context_user_to_update {
                User updateUser = User.builder()
                        .name("updated" + originUser.getName())
                        .password("updated" + originUser.getPassword())
                        .email("updated" + originUser.getEmail())
                        .build();

                @Test
                @DisplayName("User 를 업데이트 후 반환한다.")
                void it_updates_user() {
                    User resultUser = userService.update(originUser.getId(), updateUser);

                    assertThat(resultUser.getId()).isEqualTo(originUser.getId());
                    assertThat(resultUser.getEmail()).isEqualTo(updateUser.getEmail());
                    assertThat(resultUser.getPassword()).isEqualTo(updateUser.getPassword());
                    assertThat(resultUser.getName()).isEqualTo(updateUser.getName());
                }
            }

        }
    }
}