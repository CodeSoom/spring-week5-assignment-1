package com.codesoom.assignment.product.exception;

/**
 * 상품을 찾지 못했을 때 던지는 예외입니다.
 * 기본적으로 RuntimeException을 상속받기에, 예외 발생 시 roll-back을 수행합니다.
 */
public class ProductNotFoundException extends RuntimeException {
    private static final String MESSAGE = "상품이 존재하지 않습니다.";

    public ProductNotFoundException(Long id) {
        super(MESSAGE + " Id: " + id);
    }
}
