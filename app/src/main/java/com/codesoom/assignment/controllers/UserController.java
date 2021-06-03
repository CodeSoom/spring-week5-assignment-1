package com.codesoom.assignment.controllers;


import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 사용자 등록, 수정, 삭제에 대한 Request, Response 처리
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 사용자 정보를 등록한다.
     * @param userData 사용자 등록 정보.
     * @return 등록한 사용자 정보를 반환.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserData createUser(@RequestBody @Valid UserData userData){
        return userService.createUser(userData);
    }

    /**
     * 사용자 정보를 수정한다.
     * @param id 수정할 사용자 아이디.
     * @param userData 수정할 내용.
     * @return 수정한 사용자 정보를 반환
     */
    @PatchMapping("{id}")
    public UserData updateUser(@PathVariable Long id,@RequestBody UserData userData){
        return userService.updateUser(id, userData);
    }

    /**
     * 사용자 정보를 삭제한다.
     * @param id 삭제할 사용자 아이디.
     * @return 삭제한 사용자 정보를 반환.
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserData deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

}
