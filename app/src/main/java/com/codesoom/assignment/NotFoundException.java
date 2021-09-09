package com.codesoom.assignment;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final String className) {
        super("id에 해당하는 " + className + "를 찾을 수 없습니다.");
    }
}
