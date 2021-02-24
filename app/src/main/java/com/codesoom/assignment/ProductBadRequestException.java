package com.codesoom.assignment;

public class ProductBadRequestException extends RuntimeException{
    public ProductBadRequestException(String variable) {
        super(String.format("%s 값은 값은 필수입니다", variable));
    }
}
