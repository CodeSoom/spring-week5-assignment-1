package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserSignupData;
import com.codesoom.assignment.dto.UserUpdateInfoData;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@AllArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@RequestBody @Valid UserSignupData userSignupData) {
        return userService.signUp(userSignupData);
    }

    @PatchMapping("/{id}")
    public User update(@PathVariable Long id,
                       @Valid @RequestBody UserUpdateInfoData userUpdateInfoData) {
        return userService.updateInfo(id, userUpdateInfoData);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
