package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 사용자에 대한 요청을 한다.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 주어진 사용자 저장하고 해당 객체를 리턴한다.
     *
     * @param userData - 새로 저장하고자 하는 사용자
     * @return 저장 된 사용자
     * @throws MethodArgumentNotValidException 만약 주어진 사용자의
     *         이름이 비어있거나, 이메일이 비어있거나, 비밀번호가 비어있는 경우
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserData userData) {
        return userService.createUser(userData);
    }

    /**
     * 주어진 식별자에 해당하는 사용자를 수정하고 해당 객체를 리턴한다.
     *
     * @param id - 수정하고자 하는 사용자의 식별자
     * @param userData - 수정 할 새로운 사용자
     * @return 수정 된 사용자
     * @throws MethodArgumentNotValidException 만약 주어진 사용자
     *         이름이 비어있거나, 이메일이 비어있거나, 비밀번호가 비어있는 경우
     */
    @PatchMapping("/{id}")
    public User update(
            @PathVariable Long id,
            @RequestBody @Valid UserData userData) {

        return userService.updateUser(id, userData);
    }

    /**
     * 주어진 식별자에 해당하는 사용자를 삭제하고 해당 객체를 리턴한다.
     *
     * @param id - 삭제하고자 하는 사용자의 식별자
     * @return 삭제 된 사용자
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public User delete(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
