package com.codesoom.assignment.exception;

/**
 * 유저를 찾지 못한 경우 던지는 예외 객체
 */
public class NotFoundUserException extends RuntimeException {
    /**
     * 찾지 못한 유저의 id를 조합한 메시지를 포함하여, 예외 객체를 생성합니다.
     *
     * @param id 찾지 못한 유저의 id
     */
    public NotFoundUserException(Long id) {
        super("Cannot find user " + id);
    }
}
