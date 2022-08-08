package com.codesoom.assignment.product.dto;

import lombok.Getter;

@Getter
public class ValidationFailResponse {
    private final String fieldName;
    private final String message;

    public ValidationFailResponse(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}
