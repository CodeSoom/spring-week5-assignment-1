package com.codesoom.assignment.application;

import static com.codesoom.assignment.constants.UserConstants.ID;
import static com.codesoom.assignment.constants.UserConstants.USER;
import static com.codesoom.assignment.constants.UserConstants.USER_DATA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.codesoom.assignment.NotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;

import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

@Nested
@DisplayName("UserService 클래스")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper dozerMapper;

    private OngoingStubbing<User> whenMapper() {
        return when(dozerMapper.map(any(UserData.class), eq(User.class)));
    }

    private void verifyMapper() {
        verify(dozerMapper).map(any(UserData.class), eq(User.class));
    }

    private OngoingStubbing<Optional<User>> mockFindById() {
        return when(userRepository.findById(anyLong()));
    }

    private void verifyFindById(final int invokeNumber) {
        verify(userRepository, times(invokeNumber))
            .findById(anyLong());
    }

    @Nested
    @DisplayName("createUser 메서드는")
    public class Describe_createUser {
        private ObjectAssert<User> subject() {
            return assertThat(userService.createUser(USER_DATA));
        }

        private OngoingStubbing<User> mockSubject() {
            return when(userRepository.save(any(User.class)));
        }

        @BeforeEach
        public void beforeEach() {
            whenMapper()
                .thenReturn(USER);
            mockSubject()
                .thenReturn(USER);
        }

        @AfterEach
        public void afterEach() {
            verifyMapper();
            verify(userRepository)
                .save(any(User.class));
        }

        @Test
        @DisplayName("User를 생성하고 리턴한다.")
        public void it_creates_a_user() {
            subject()
                .isInstanceOf(User.class);
        }
    }

    @Nested
    @DisplayName("deleteUser 메서드는")
    public class Describe_deleteUser {
        private void subject() {
            userService.deleteUser(ID);
        }

        private void verifyDelete(final int invokeNumber) {
            verify(userRepository, times(invokeNumber))
                .delete(any(User.class));
        }

        @Nested
        @DisplayName("User를 찾을 수 없는 경우")
        public class Context_find_fail {
            @BeforeEach
            void beforeEach() {
                mockFindById()
                    .thenThrow(new NotFoundException(ID, User.class.getSimpleName()));
            }

            @AfterEach
            void afterEach() {
                verifyFindById(1);
                verifyDelete(0);
            }

            @Test
            @DisplayName("NotFoundException을 던진다.")
            void it_throws_a_notFoundException() {
                assertThatThrownBy(() -> subject())
                    .isInstanceOf(NotFoundException.class);
            }
        }

        @Nested
        @DisplayName("User를 찾을 수 있는 경우")
        public class Context_find_success {
            @BeforeEach
            void beforeEach() {
                mockFindById()
                    .thenReturn(Optional.of(USER));
            }

            @AfterEach
            void afterEach() {
                verifyFindById(1);
                verifyDelete(1);
            }

            @Test
            @DisplayName("찾은 User를 삭제한다.")
            public void it_deletes_a_user() {
                subject();
            }
        }
    }
}
