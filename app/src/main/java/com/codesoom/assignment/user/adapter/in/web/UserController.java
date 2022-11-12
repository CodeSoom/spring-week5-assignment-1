package com.codesoom.assignment.user.adapter.in.web;

import com.codesoom.assignment.user.adapter.in.web.dto.request.UserCreateRequestDto;
import com.codesoom.assignment.user.adapter.in.web.dto.response.UserCreateResponse;
import com.codesoom.assignment.user.application.in.UserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserCreateResponse create(@RequestBody UserCreateRequestDto userCreateRequestDto) {
        return new UserCreateResponse(
                userUseCase.createUser(userCreateRequestDto)
        );
    }
}
