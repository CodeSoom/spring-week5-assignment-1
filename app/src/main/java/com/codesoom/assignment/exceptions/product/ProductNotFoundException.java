package com.codesoom.assignment.exceptions.product;

import com.codesoom.assignment.exceptions.HttpBusinessException;
import org.springframework.http.HttpStatus;

/**
 * 상품을 찾지 못했을 때 던지는 예외입니다.
 */
public class ProductNotFoundException extends HttpBusinessException {
    private static final String MESSAGE = "상품이 존재하지 않습니다.";

    public ProductNotFoundException() {
        super(MESSAGE, HttpStatus.NOT_FOUND);
    }

    public ProductNotFoundException(Long id) {
        super(MESSAGE + " Id: " + id, HttpStatus.NOT_FOUND);
    }
}
