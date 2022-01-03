package com.codesoom.assignment.dto;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(BindingResult bindingResult) {
        List<ObjectError> errors = bindingResult.getAllErrors();
        this.message = errors.get(0).getDefaultMessage();
    }

    public String getMessage() {
        return message;
    }
}
