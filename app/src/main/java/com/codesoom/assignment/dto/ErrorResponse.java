package com.codesoom.assignment.dto;

import com.codesoom.assignment.application.common.exception.ErrorValidation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private List<ErrorValidation> errors;


    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String code, String message, List<ErrorValidation> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors != null ? errors : List.of();
    }


}
