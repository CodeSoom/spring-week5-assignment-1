package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ErrorResponse {
    private final String url;
    private final String method;
    private final String error;
}
