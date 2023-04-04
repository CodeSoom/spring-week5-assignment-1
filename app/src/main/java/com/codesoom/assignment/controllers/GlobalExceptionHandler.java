package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorResponse handleProductTaskNotFound() {
        return new ErrorResponse("Product not found");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFound() {
        return new ErrorResponse("User not found");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleVaildation(MethodArgumentNotValidException e){
        String errMessage = getNotValidErrorMessage(e);
        return new ErrorResponse(errMessage);
    }

    private String getNotValidErrorMessage(MethodArgumentNotValidException e){

        BindingResult bindingResult = e.getBindingResult();

        return bindingResult.getFieldError().getDefaultMessage();

    }
}
