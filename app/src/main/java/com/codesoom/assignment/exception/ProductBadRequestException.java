package com.codesoom.assignment.exception;

public class ProductBadRequestException extends RuntimeException {
    public ProductBadRequestException(String variable, String status) {
        super(String.format("Product bad request: %s 는 %s 이어야 한다", variable, status));
    }
    public ProductBadRequestException(String variable){
        super(String.format("%s 값은 필수입니다", variable));
    }
}
