package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserResponse;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 사용자 관련 HTTP 요청을 처리한다.
 */
@RestController
@CrossOrigin
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Mapper mapper;

    /**
     * 주어진 식별자에 해당하는 사용자를 응답한다.
     *
     * @param id - 찾으려는 사용자
     * @return 찾은 사용자
     */
    @GetMapping("{id}")
    public UserResponse findUser(@PathVariable Long id) {
        User user = userService.getUser(id);

        return mapper.map(user, UserResponse.class);
    }
}
