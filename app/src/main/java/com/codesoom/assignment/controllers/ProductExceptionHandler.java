package com.codesoom.assignment.controllers;

import com.codesoom.assignment.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductExceptionHandler {

    public final String PRODUCT_NOT_FOUND = "상품을 찾을 수 없습니다. id를 확인해주세요.";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public String handleNotFoundException() {
        return PRODUCT_NOT_FOUND;
    }
}
