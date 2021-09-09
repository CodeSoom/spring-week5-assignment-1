package com.codesoom.assignment;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final Long id, final String className) {
        super(String.format("id %d 에 해당하는 %s를 찾을 수 없습니다.", id, className));
    }
}
