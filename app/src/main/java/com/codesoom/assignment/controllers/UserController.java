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
     *  userData에 user을 생성하고 user가 생성되면 201을 응답한다.
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
     * id에 해당되는 user을 수정하여 userData 리턴하고 200을 응답한다.
     *
     * @param id 수정할 user의 id
     * @param userData 수정한 user 정보
     * @return 수정된 user 정보
     */

    @PatchMapping("/{id}")
    public User update(
            @PathVariable Long id,
            @RequestBody @Valid UserData userData) {
        return userService.updateUser(id, userData);
    }

    /**
     * id에 해당되는 user을 삭제하고 204를 리턴한다.
     * @param id 삭제할 user id
     */

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
