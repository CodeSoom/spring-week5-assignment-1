package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     *  userData에 user을 생성하고 리턴한다.
     *
     * @param userData 생성된 user 정보
     * @return 생성된 user
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserData userData) {
        return userService.createUser(userData);
    }

    /**
     * id에 해당되는 user을 수정하여 userData 리턴한다.
     *
     * @param userId 수정할 user의 id
     * @param userData 수정한 user 정보
     * @return 수정된 user 정보
     */
    @PatchMapping("/{userId}")
    public User update(
            @PathVariable Long userId,
            @RequestBody @Valid UserData userData) {
        return userService.updateUser(userId, userData);
    }

    /**
     * id에 해당되는 user을 삭제한다.
     * @param userId 삭제할 user id
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
