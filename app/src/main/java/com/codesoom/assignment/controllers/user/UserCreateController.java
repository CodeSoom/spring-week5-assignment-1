package com.codesoom.assignment.controllers.user;

import com.codesoom.assignment.application.user.UserCreator;
import com.codesoom.assignment.domain.user.User;
import com.codesoom.assignment.dto.user.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserCreateController {
    private final UserCreator userCreator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserData userData) {
        return userCreator.createUser(userData);
    }

}
