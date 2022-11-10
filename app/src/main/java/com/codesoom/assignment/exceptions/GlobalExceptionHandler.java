package com.codesoom.assignment.exceptions;

import com.codesoom.assignment.exceptions.product.ProductNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ProductNotFoundException.class})
    protected ErrorResponse handleProductNotFoundException(final ProductNotFoundException exception) {
        return ErrorResponse.from(exception);
    }
}
