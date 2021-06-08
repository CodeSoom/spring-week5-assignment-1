package com.codesoom.assignment.web.controller;

import com.codesoom.assignment.web.dto.ErrorResponse;
import com.codesoom.assignment.web.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MemberExceptionAdvisor {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MemberNotFoundException.class)
    public ErrorResponse handleMemberNotFound() {
        return ErrorResponse.builder()
                .message("Member not found")
                .build();
    }
}
