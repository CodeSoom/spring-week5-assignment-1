package com.codesoom.assignment;

public class ProductBadRequestException extends RuntimeException {
    public ProductBadRequestException(String variable, String status) {
        super(String.format("Product bad request: %s 는 %s 이어야 한다", variable, status));
    }
}
