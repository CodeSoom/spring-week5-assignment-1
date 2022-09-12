package com.codesoom.assignment;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long id) {
        super("식별자에 해당하는 자원이 존재하지 않습니다. : " + id);
    }
}
