package com.codesoom.assignment.controllers.user;

import com.codesoom.assignment.application.user.UserCreator;
import com.codesoom.assignment.application.user.UserDeleter;
import com.codesoom.assignment.application.user.UserUpdater;
import com.codesoom.assignment.domain.user.User;
import com.codesoom.assignment.dto.user.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserCreator userCreator;
    private final UserUpdater userUpdater;
    private final UserDeleter userDeleter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserData userData) {
        return userCreator.createUser(userData);
    }

    @PatchMapping({"/{id}"})
    public User update(@PathVariable Long id,
                       @RequestBody UserData userData) {
        return userUpdater.update(id, userData);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userDeleter.delete(id);
    }

}
