package com.codesoom.assignment.exception;


import static org.assertj.core.api.Assertions.assertThat;

import com.codesoom.assignment.exception.ProductNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductNotFoundExceptionTest {

    @Test
    @DisplayName("없는 아이디를 찾을때는 에러가 발생한다.")
    void productNotFoundException() {
        Long givenNotExistedId = 100L;

        ProductNotFoundException productNotFoundException
                = new ProductNotFoundException(givenNotExistedId);

        assertThat(productNotFoundException.getMessage())
                .isEqualTo("Product not found: " + givenNotExistedId);
    }

}
