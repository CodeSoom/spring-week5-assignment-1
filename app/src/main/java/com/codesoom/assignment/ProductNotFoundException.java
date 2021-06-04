package com.codesoom.assignment;

/**
 * 상품을 찾지 못한 경우의 예외 객체
 */
public class ProductNotFoundException extends RuntimeException {
    /**
     * 찾지 못한 상품의 id를 조합한 메시지를 포함하여, 예외 객체를 생성합니다.
     *
     * @param id 찾지 못한 상품의 id
     */
    public ProductNotFoundException(Long id) {
        super("Product not found: " + id);
    }
}
