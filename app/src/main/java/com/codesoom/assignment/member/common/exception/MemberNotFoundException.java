package com.codesoom.assignment.member.common.exception;

/**
 * 회원 ID에 해당하는 회원정보를 찾을수 없을때 발생하는 예외
 */
public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(Long id) {
        super("등록되지 않은 회원입니다.[ID:" + id + "]");
    }
}
