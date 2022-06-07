package com.codesoom.assignment;

/**
 * Product 탐색 실패 예외 처리
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product not found: " + id);
    }
}
