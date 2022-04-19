package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.users.User;
import com.codesoom.assignment.dto.UserSaveDto;
import com.codesoom.assignment.dto.UserUpdateDto;
import com.codesoom.assignment.dto.UserViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 회원에 대한 HTTP 요청 처리
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 회원을 등록하고 등록 정보를 리턴한다.
     *
     * @param saveSource 등록할 회원 데이터
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserViewDto save(@RequestBody @Validated UserSaveDto saveSource) {

        final User user = userService.save(saveSource);

        return UserViewDto.from(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{userId}")
    public UserViewDto patch(@PathVariable Long userId, @RequestBody @Validated UserUpdateDto updateSource) {

        final User foundUser = userService.getUser(userId);

        final User updatedUser = userService.updateUser(foundUser, updateSource);

        return UserViewDto.from(updatedUser);
    }
}
