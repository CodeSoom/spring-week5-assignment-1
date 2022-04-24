package com.codesoom.assignment.controllers.user;

import com.codesoom.assignment.application.user.UserService;
import com.codesoom.assignment.domain.user.User;
import com.codesoom.assignment.dto.user.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserData.RegisterUserRequest request) {
        return userService.createUser(request);
    }
}
