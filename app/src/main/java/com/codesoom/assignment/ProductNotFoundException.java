package com.codesoom.assignment;

/**
 * 해당하는 상품이 없을 때 호출되는 예외 입니다.
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product not found: " + id);
    }
}
