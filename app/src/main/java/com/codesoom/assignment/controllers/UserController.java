package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.users.User;
import com.codesoom.assignment.dto.UserSaveRequestData;
import com.codesoom.assignment.dto.UserUpdateRequestData;
import com.codesoom.assignment.dto.UserResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
 * 회원에 대한 HTTP 요청 처리
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 회원 등록하고 등록 정보를 리턴한다.
     *
     * @param saveSource 등록할 회원 데이터
     * @return 등록된 회원 데이터
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserResponseData save(@RequestBody @Valid UserSaveRequestData saveSource) {

        final User user = userService.save(saveSource);

        return UserResponseData.from(user);
    }

    /**
     * 회원 정보를 수정하고 수정된 정보를 리턴한다.
     * @param userId    회원 아이디
     * @param updateSource  수정할 회원 데이터
     * @return 수정된 회원 데이터
     */
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{userId}")
    public UserResponseData patch(@PathVariable Long userId, @RequestBody @Valid UserUpdateRequestData updateSource) {

        final User foundUser = userService.getUser(userId);

        final User updatedUser = userService.updateUser(foundUser, updateSource);

        return UserResponseData.from(updatedUser);
    }

    /**
     * 회원을 삭제합니다.
     * @param userId    회원 아이디
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {

        final User foundUser = userService.getUser(userId);

        userService.deleteUser(foundUser);
    }
}
