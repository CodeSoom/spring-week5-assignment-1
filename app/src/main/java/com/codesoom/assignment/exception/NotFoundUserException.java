package com.codesoom.assignment.exception;

/**
 * 유저를 찾지 못한 경우 던집니다.
 */
public class NotFoundUserException extends RuntimeException {
    /**
     * 생성자.
     *
     * @param id 찾지 못한 유저의 id
     */
    public NotFoundUserException(Long id) {
        super("Cannot find user " + id);
    }
}
