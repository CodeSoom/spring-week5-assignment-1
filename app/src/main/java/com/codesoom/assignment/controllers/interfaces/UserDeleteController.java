package com.codesoom.assignment.controllers.interfaces;

/**
 * User에 대한 HTTP DELETE 요청을 수신하고 처리 결과를 송신한다
 * <p>
 * All Known Implementing Classes:
 * UserDeleteController
 * </p>
 */
public interface UserDeleteController {
    /**
     * 특정 User 대한 삭제 요청을 수신한다
     * <p>
     *
     * @param id Request Path Parameter으로 전달된 User Id를 받기 위한 객체
     * </p>
     */
    void delete(Long id);
}
