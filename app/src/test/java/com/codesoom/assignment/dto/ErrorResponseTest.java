package com.codesoom.assignment.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {
    private ErrorResponse errorResponse;

    @Test
    @DisplayName("메세지 조회에 대한 테스트")
    void setUp() {
        errorResponse = new ErrorResponse("Not Found");

        assertThat(errorResponse.getMessage()).isEqualTo("Not Found");
    }
}
