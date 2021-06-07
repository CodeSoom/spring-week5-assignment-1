package com.codesoom.assignment;

/**
 * 상품을 찾지 못했을 때 던집니다.
 */
public class ProductNotFoundException extends RuntimeException {
    /**
     * 생성자.
     *
     * @param id 찾지 못한 상품의 id
     */
    public ProductNotFoundException(Long id) {
        super("Product not found: " + id);
    }
}
