package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.dto.UserPatchValidation;
import com.codesoom.assignment.dto.UserPostValidation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 API 요청을 처리합니다.
 */
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 사용자를 생성한 후 생성된 사용자를 리턴합니다.
     *
     * @param userData 사용자 데이터
     * @return 사용자
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(
            @RequestBody @Validated(UserPostValidation.class)
                    UserData userData) {
        return userService.create(userData);
    }

    /**
     * 사용자 정보를 수정한 후 수정된 사용자를 리턴합니다.
     *
     * @param id       식별자
     * @param userData 사용자 데이터
     * @return 수정된 사용자
     */
    @PatchMapping("{id}")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody @Validated(UserPatchValidation.class)
                    UserData userData) {
        return userService.patch(id, userData);
    }

    /**
     * 사용자를 제거합니다.
     *
     * @param id 식별자
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
