package com.codesoom.assignment;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(Long id) {
        super("not Found Member " + id);
    }
}
