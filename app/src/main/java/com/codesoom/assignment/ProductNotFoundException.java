package com.codesoom.assignment;

/**
 * 상품을 찾지 못할시 나타내는 예외.
 */
public class ProductNotFoundException extends RuntimeException {

    /**
     * 식별자로 상품을 찾지 못할 시 던집니다.
     *
     * @param id 식별자
     */
    public ProductNotFoundException(Long id) {
        super("Product not found: " + id);
    }
}
