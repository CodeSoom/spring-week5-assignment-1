package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserCreateData;
import com.codesoom.assignment.dto.UserUpdateData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * User 에 대한 HTTP 요청 관리
 */
@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 주어진 User 를 저장하고 저장된 user 를 반환
     *
     * @param userCreateData 저장할 user
     * @return 저장된 user
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserCreateData userCreateData) {
        return userService.createUser(userCreateData);
    }

    /**
     * 주어진 id 와 일치하는 user 를 수정하고 수정된 user 를 반환
     *
     * @param id 식별자
     * @param userUpdateData 수정할 user
     * @return 수정된 user
     */
    @PatchMapping("/{id}")
    public User update(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdateData userUpdateData
    ) {
        return userService.updateUser(id, userUpdateData);
    }

    /**
     * 주어진 id 와 일치하는 user 를 삭제
     *
     * @param id 식별자
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
