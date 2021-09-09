package com.codesoom.assignment;

/**
 * 사용자를 찾을 수 없는 경우 던지는 예외입니다
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product not found: " + id);
    }
}
