package com.codesoom.assignment.advice;

import com.codesoom.assignment.advice.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class BadRequestErrorAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class, // When req path parameter has problems
            MethodArgumentNotValidException.class, // When req body has problems
    })
    public ErrorResponse handleProductBadRequest() {
        return new ErrorResponse("Check your request body and path variables");
    }
}