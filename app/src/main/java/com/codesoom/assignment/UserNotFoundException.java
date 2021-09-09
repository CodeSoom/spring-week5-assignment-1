package com.codesoom.assignment;

/**
 * 회원을 찾지 못할 시 나타내는 예외
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * id로 회원을 찾시 못할 시 던집니다.
     *
     * @param id 회원 아이디
     */
    public UserNotFoundException(Long id) {
        super(String.format("id %s에 대한 회원을 찾을 수 없습니다.", id));
    }
}
