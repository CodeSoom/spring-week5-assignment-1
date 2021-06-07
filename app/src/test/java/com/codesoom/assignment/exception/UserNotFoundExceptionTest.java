package com.codesoom.assignment.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {

    @Test
    @DisplayName("없는 유저를 찾을때는 에러가 발생한다.")
    void productNotFoundException() {
        Long givenNotExistedId = 100L;

        UserNotFoundException userNotFoundException
                = new UserNotFoundException(givenNotExistedId);

        assertThat(userNotFoundException.getMessage())
                .isEqualTo("User not found: " + givenNotExistedId);
    }
}
