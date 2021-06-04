package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserDto;
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
 *  유저 API HTTP 요청을 처리합니다.
 */
@CrossOrigin
@RequestMapping("/users")
@RestController
public class UserController {

    private UserService userService;

    /**
     * 요청 받은 동작을 수행할 서비스객체를 주입 받습니다.
     *
     * @param userService
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 유저를 생성하고, 생성한 유저 정보를 응답합니다.
     *
     * @param source 생성할 유저 정보
     * @return 생성한 유저 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Validated(UserDto.ValidateCreate.class) UserDto source) {
        return userService.createUser(source);
    }

    /**
     * 유저 정보를 갱신하고, 갱신한 유저 정보를 응답합니다.
     *
     * @param id 갱신할 유저의 id
     * @param userDto 갱신할 내용
     * @return 갱신한 유저 정보
     */
    @PatchMapping("/{id}")
    public User update(
            @PathVariable Long id,
            @RequestBody @Validated(UserDto.ValidateUpdate.class) UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    /**
     * 유저 정보를 삭제합니다.
     *
     * @param id 삭제할 유저의 id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
       userService.deleteUser(id);
    }
}
