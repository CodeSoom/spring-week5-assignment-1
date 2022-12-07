package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ProductErrorAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorResponse handleProductTaskNotFound() {
        return new ErrorResponse("상품을 찾을 수 없습니다.");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleProductNotValid(MethodArgumentNotValidException ex) {
        BindingResult result  = ex.getBindingResult();
        StringBuilder builder = new StringBuilder();
        List<FieldError> fieldErrors = result.getFieldErrors();

        builder.append("[");
        for(FieldError error : fieldErrors) {
            builder.append(error.getDefaultMessage());
            builder.append(",");
        }
        builder.deleteCharAt(builder.lastIndexOf(",")); //쉼표 제거
        builder.append("] 은(는) 필수 입력 사항입니다.");

        return new ErrorResponse(builder.toString());
    }
}
