package com.codesoom.assignment.web.exception;

/**
 * 고양이 장난감 ID가 유효하지 않거나 찾을 수 없음을 나타냅니다.
 */
public class ProductNotFoundException extends RuntimeException {
    /**
     * {@code ProductNotFoundException} 생성자. 상세 메시지를 함께 출력합니다.
     *
     * @param id - 찾을 수 없는 고양이 장난감 ID
     */
    public ProductNotFoundException(Long id) {
        super("Product not found: " + id);
    }
}
