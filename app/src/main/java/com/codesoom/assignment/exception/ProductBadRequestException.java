package com.codesoom.assignment.exception;

public class ProductBadRequestException extends RuntimeException {
    public ProductBadRequestException(String value, String expectedValue) {
        super(String.format("Product bad request: %s 는 %s 이어야 한다", value, expectedValue));
    }

    public ProductBadRequestException(String value){
        super(String.format("%s 값은 필수입니다", value));
    }
}
