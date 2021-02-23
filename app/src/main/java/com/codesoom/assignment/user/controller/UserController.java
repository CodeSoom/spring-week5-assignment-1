package com.codesoom.assignment.user.controller;

import com.codesoom.assignment.user.dto.UserCreateRequest;
import com.codesoom.assignment.user.dto.UserResponse;
import com.codesoom.assignment.user.dto.UserUpdateRequest;
import com.codesoom.assignment.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 회원과 관련된 HTTP 요청 처리를 담당합니다.
 */
@RestController
@CrossOrigin
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 모든 회원을 응답합니다.
     */
    @GetMapping
    public List<UserResponse> list() {
        return userService.getUsers();
    }

    /**
     * 주어진 id에 해당하는 회원을 찾아 응답합니다.
     *
     * @param id 찾고자 하는 회원의 식별자
     * @return 찾은 회원
     */
    @GetMapping("{id}")
    public UserResponse find(@PathVariable Long id) {
        return userService.getUser(id);
    }

    /**
     * 주어진 회원을 저장한 뒤, 저장된 회원을 응답합니다.
     *
     * @param createRequest 저장하고자 하는 회원
     * @return 저장된 회원
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid UserCreateRequest createRequest) {
        return userService.createUser(createRequest);
    }

    /**
     * 주어진 id에 해당하는 회원을 찾아 수정하고, 수정된 회원을 응답합니다.
     *
     * @param id 수정하고자 하는 회원의 식별자
     * @param updateRequest 수정하고자 하는 회원
     * @return 수정된 회원
     */
    @PatchMapping("{id}")
    public UserResponse update(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdateRequest updateRequest) {
        return userService.updateUser(id, updateRequest);
    }

    /**
     * 주어진 id에 해당하는 회원을 찾아 삭제합니다.
     *
     * @param id 삭제하고자 하는 회원의 식별자
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
