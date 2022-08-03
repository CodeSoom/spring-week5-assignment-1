package com.codesoom.assignment;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super(id + "를 찾을 수 없습니다.");
    }
}
