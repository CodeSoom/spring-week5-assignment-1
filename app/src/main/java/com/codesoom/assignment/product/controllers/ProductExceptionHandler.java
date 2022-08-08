package com.codesoom.assignment.product.controllers;

import com.codesoom.assignment.product.dto.ProductNotFoundException;
import com.codesoom.assignment.product.dto.ErrorResponse;
import com.codesoom.assignment.product.dto.ValidationFailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ProductExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorResponse handleProductTaskNotFound() {
        return new ErrorResponse("Product not found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ValidationFailResponse> handleInValidValue(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    return new ValidationFailResponse(fieldName, message);
                }).collect(Collectors.toList());
    }
}
