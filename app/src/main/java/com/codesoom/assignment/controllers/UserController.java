package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.User;

import java.util.List;

/**
 * 유저의 Http 요청을 처리합니다.
 */
public class UserController {
    /**
     * 요청 유저와 동일한 유저를 생성하고, 생성한 유저를 응답 합니다.
     *
     * @param user 요청 유저
     * @return 생성한 유저
     */
    User create(User user) {
        return null;
    }

    /**
     * 요청 id의 유저를 응답합니다.
     *
     * @param id 요청 id
     * @return 요청 id의 유저
     */
    User detail(Long id) {
        return null;
    }

    /**
     * 요청 id의 유저를 요청 유저와 동일하도록 변경하고 변경한 유저를 응답합니다.
     *
     * @param id   요청 id
     * @param user 요청 유저
     * @return
     */
    User update(Long id, User user) {
        return null;
    }

    /**
     * 요청 id의 유저를 삭제합니다.
     *
     * @param id 요청 id
     */
    void delete(Long id) {
    }

    /**
     * 등록된 모든 유저들의 목록을 응답합니다.
     *
     * @return 등록된 모든 유저 목록
     */
    List<User> list() {

    }
}
