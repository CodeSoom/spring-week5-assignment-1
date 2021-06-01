package com.codesoom.assignment.web.exception;

public class MemberNotFoundException extends RuntimeException {
    /**
     * ID에 해당하는 회원을 찾을 수 없음.
     * @param id
     */
    public MemberNotFoundException(Long id) {
        super("Member not found: " + id);
    }
}
