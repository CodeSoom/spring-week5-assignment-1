package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserCreator;
import com.codesoom.assignment.application.UserDeleter;
import com.codesoom.assignment.application.UserUpdater;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.CreateUserData;
import com.codesoom.assignment.dto.UpdateUserData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserCreator userCreator;
    private final UserDeleter userDeleter;
    private final UserUpdater userUpdater;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid CreateUserData userData) {
        return userCreator.create(userData);
    }

    @PatchMapping("{id}")
    public User update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserData userData) {
        return userUpdater.update(id, userData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userDeleter.delete(id);
    }
}
