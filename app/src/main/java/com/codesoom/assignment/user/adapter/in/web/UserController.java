package com.codesoom.assignment.user.adapter.in.web;

import com.codesoom.assignment.user.adapter.in.web.dto.request.UserCreateRequestDto;
import com.codesoom.assignment.user.adapter.in.web.dto.request.UserUpdateRequestDto;
import com.codesoom.assignment.user.adapter.in.web.dto.response.UserCreateResponse;
import com.codesoom.assignment.user.adapter.in.web.dto.response.UserUpdateResponse;
import com.codesoom.assignment.user.application.in.UserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserCreateResponse create(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        return new UserCreateResponse(
                userUseCase.createUser(userCreateRequestDto)
        );
    }

    @RequestMapping(path = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public UserUpdateResponse update(@PathVariable Long id,
                                     @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        return new UserUpdateResponse(
                userUseCase.updateUser(id, userUpdateRequestDto)
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Long delete(@PathVariable Long id) {
        return userUseCase.deleteUser(id);
    }
}
