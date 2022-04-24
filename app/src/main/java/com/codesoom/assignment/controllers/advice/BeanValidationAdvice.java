package com.codesoom.assignment.controllers.advice;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ApiErrorResponse;
import com.codesoom.assignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class BeanValidationAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object beanValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<String> errorMessages = new ArrayList<>();
        List<ObjectError> allErrors = bindingResult.getAllErrors();

        for (ObjectError error : allErrors) {
            errorMessages.add(error.getDefaultMessage());
        }

        return new ApiErrorResponse(HttpStatus.BAD_REQUEST, errorMessages);
    }
}
