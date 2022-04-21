package com.codesoom.assignment.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ApiErrorResponse {
    int statusCode;
    HttpStatus status;
    List<String> messages;

    public ApiErrorResponse(HttpStatus status, List<String> messages) {
        this.statusCode = status.value();
        this.status = status;
        this.messages = messages;
    }
}
