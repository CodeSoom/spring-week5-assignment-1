package com.codesoom.assignment.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponseTest {

    @Test
    @DisplayName("ErrorResponse는 에러 메세지를 가지고 있습니다.")
    public void setUp(){
        ErrorResponse errorResponse = new ErrorResponse("test");
        assertThat(errorResponse.getMessage()).isEqualTo("test");
    }

}
