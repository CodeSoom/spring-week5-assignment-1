package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.CreateUserRequest;
import com.codesoom.assignment.dto.UpdateUserRequest;
import com.codesoom.assignment.dto.UserResponse;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
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
 * User에  대한 HTTP 요청 핸들러.
 *
 * @see UserService
 * @see User
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final Mapper mapper;

    /**
     * 주어진 user를 저장하고 저장된 user를 응답합니다.
     *
     * @param createUserRequest 저장하고자 하는 user
     * @return 저장된 user
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid CreateUserRequest createUserRequest) {
        User source = mapper.map(createUserRequest, User.class);
        User user = userService.createUser(source);
        return UserResponse.of(user);
    }

    /**
     * 주어진 id와 일치하는 user를 삭제합니다.
     *
     * @param id user 식별자
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    /**
     * 주어진 id와 일치하는 user를 수정하고 수정된 user를 반환합니다.
     *
     * @param id                user 식별자
     * @param updateUserRequest 수정하고자 하는 user
     * @return 수정된 user
     */
    @PatchMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        User source = mapper.map(updateUserRequest, User.class);
        User user = userService.updateUser(id, source);
        return UserResponse.of(user);
    }
}
