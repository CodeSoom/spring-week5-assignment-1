package com.codesoom.assignment.controllers.users;

import com.codesoom.assignment.application.UserCreator;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.CreateUserData;
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
    public User create(@RequestBody @Valid CreateUserData userData) {
        return userCreator.create(userData);
    }

}
