package com.codesoom.assignment.exception;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class ExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(ProductNotFoundException.class)
    public ErrorResponse handleProductTaskNotFound(ProductNotFoundException e) {
        return ErrorSet(400 , "잘못된 에러입니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse exceptionHandler(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorSet(400 , "잘못된 입력으로 에러가 발생.");

        for (FieldError fieldError : e.getBindingResult().getFieldErrors())
            errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        return errorResponse;
    }


    private static ErrorResponse ErrorSet(int code , String message) {
        return ErrorResponse.builder()
                .code(String.valueOf(code))
                .message(message)
                .build();
    }
}
