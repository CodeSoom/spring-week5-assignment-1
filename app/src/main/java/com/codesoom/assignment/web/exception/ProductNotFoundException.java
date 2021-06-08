package com.codesoom.assignment.web.exception;

/**
 * 제품을 찾을 수 없는 경우 발생되는 Exception.
 */
public class ProductNotFoundException extends RuntimeException {
    /**
     * {@code ProductNotFoundException} 생성자. 상세 메시지를 함께 출력합니다.
     *
     * @param id - 찾을 수 없는 제품의 식별자
     */
    public ProductNotFoundException(Long id) {
        super("Product not found: " + id);
    }
}
