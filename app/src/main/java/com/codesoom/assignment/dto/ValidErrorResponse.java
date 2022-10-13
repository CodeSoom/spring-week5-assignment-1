package com.codesoom.assignment.dto;

import java.util.List;

public class ValidErrorResponse {
    private final List<String> messages;

    public ValidErrorResponse(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}
