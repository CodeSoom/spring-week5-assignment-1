package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 사용자에 대한 HTTP 요청의 처리를 담당한다.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 사용자를 저장하고 리턴한다.
     *
     * @param userData 저장할 사용자 데이터
     * @return 사용자
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserData userData) {
        return userService.createUser(userData);
    }

    /**
     * 요청받은 id의 사용자 정보를 수정한다.
     *
     * @param userData 사용자의 수정된 정보
     * @param id       수정할 사용자 id
     * @return 수정된 사용자
     */
    @PatchMapping("/{id}")
    public User update(
            @PathVariable Long id,
            @RequestBody @Valid UserData userData
    ) {
        return userService.updateUser(id, userData);
    }

    /**
     * 요청받은 id의 사용자를 삭제한다.
     *
     * @param id 삭제할 사용자 id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
