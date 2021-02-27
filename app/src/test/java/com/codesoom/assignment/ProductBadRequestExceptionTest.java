package com.codesoom.assignment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductBadRequestExceptionTest {
    @Test
    @DisplayName("상품에 대한 잘못한 요청의 예외")
    void productNotFoundException() {

        ProductBadRequestException productBadRequestException
                = new ProductBadRequestException("name");

        assertThat(productBadRequestException.getMessage())
                .isEqualTo("name 값은 필수입니다");
    }
}
