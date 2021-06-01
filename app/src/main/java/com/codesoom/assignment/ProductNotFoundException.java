package com.codesoom.assignment;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

/**
 * 상품을 찾을 수 없는 경우에 던집니다.
 */
public class ProductNotFoundException extends HttpClientErrorException {
    public ProductNotFoundException(Long id) {
        this("Product not found: " + id);
    }

    public ProductNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
