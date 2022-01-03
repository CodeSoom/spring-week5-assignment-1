package com.codesoom.assignment;

import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class UserBadRequestException extends RuntimeException {
    private String message;

    public UserBadRequestException(BindingResult bindingResult) {
        List<ObjectError> errors = bindingResult.getAllErrors();

        this.message = errors.get(0).getDefaultMessage();
    }
}
