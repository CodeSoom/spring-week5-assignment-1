package com.codesoom.assignment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductNotFoundExceptionTest {
    @Test
    @DisplayName("상품을 찾을 수 없다는 예외")
    void productNotFoundException() {
        Long givenNotExistedId = 100L;

        ProductNotFoundException productNotFoundException
                = new ProductNotFoundException(givenNotExistedId);

        assertThat(productNotFoundException.getMessage())
                .isEqualTo("Product not found: " + givenNotExistedId);
    }
}
