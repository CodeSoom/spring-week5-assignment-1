package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.users.User;
import com.codesoom.assignment.dto.UserSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
     * @param source 등록할 회원 데이터
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User save(@RequestBody UserSaveDto source) {
        final User user = User.builder()
                .email(source.getEmail())
                .name(source.getName())
                .password(source.getPassword())
                .build();

        return userService.save(user);
    }
}
