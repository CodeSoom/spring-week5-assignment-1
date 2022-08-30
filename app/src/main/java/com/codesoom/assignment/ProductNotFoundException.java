package com.codesoom.assignment;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long id) {
        super("Product not found: " + id);
    }
}
