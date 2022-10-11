package com.codesoom.assignment.member.common.advice;

import com.codesoom.assignment.member.common.exception.InvalidParamException;
import com.codesoom.assignment.member.common.exception.MemberNotFoundException;
import com.codesoom.assignment.product.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackages = "com.codesoom.assignment.member")
public class MemberErrorAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MemberNotFoundException.class)
    public ErrorResponse handleNotFound(MemberNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidParamException.class)
    public ErrorResponse handleBadRequest(InvalidParamException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException e) {
        Map<String, String> responseBody = new HashMap<>();

        e.getBindingResult().getFieldErrors()
                .forEach(fieldError -> responseBody.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.badRequest().body(responseBody);
    }
}
