package com.codesoom.assignment;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super("Bad Request");
    }
}
