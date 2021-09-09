package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.CreateUserDto;
import com.codesoom.assignment.dto.UpdateUserDto;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 관련 HTTP 요청 처리 담당.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원을 생성합니다.
     *
     * @param createUserDto 회원 생성 정보
     * @return 생성된 회원
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(
        @RequestBody @Valid CreateUserDto createUserDto
    ) {
        return userService.createUser(createUserDto.toEntity());
    }

    /**
     * 회원 정보를 수정합니다.
     *
     * @param id            식별자
     * @param updateUserDto 수정할 회원 정보
     * @return 수정된 회원
     */
    @PatchMapping("{id}")
    public User update(
        @PathVariable Long id,
        @RequestBody @Valid UpdateUserDto updateUserDto
    ) {
        return userService.updateUser(id, updateUserDto.toEntity());
    }


    /**
     * 식별자로 회원을 찾아 삭제합니다.
     *
     * @param id 식별자
     */
    @DeleteMapping("{id}")
    public void delete(
        @PathVariable Long id
    ) {
        userService.deleteUser(id);
    }
}
