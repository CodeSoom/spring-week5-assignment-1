package com.codesoom.assignment.common.exception;

/**
 * 회원정보를 찾을수 없을때 던집니다.
 */
public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(Long id) {
        super("등록되지 않은 회원입니다.[ID:" + id + "]");
    }
}
