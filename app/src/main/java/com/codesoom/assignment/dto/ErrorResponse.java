package com.codesoom.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ErrorResponse {
    private final String url;
    private final String method;
    private final String error;
    private final String solution;
}
