package com.codesoom.assignment.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponseTest {

    @Test
    @DisplayName("에러메세지 init test")
    public void setUp(){
        ErrorResponse errorResponse = new ErrorResponse("test");
        assertThat(errorResponse.getMessage()).isEqualTo("test");
    }

}
