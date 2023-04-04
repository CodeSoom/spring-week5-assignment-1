package com.codesoom.assignment.controllers;


import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserModificationData;
import com.codesoom.assignment.dto.UserResultData;
import com.codesoom.assignment.dto.UserRegistrationData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResultData create(@RequestBody @Valid UserRegistrationData userData){
        User user = userService.registerUser(userData);

        return getUserResultData(user);
    }

    @PatchMapping("{id}")
    UserResultData update(@RequestBody @Valid UserModificationData userModificationData , @PathVariable Long id){
        User user = userService.updateUser(id , userModificationData);

        return getUserResultData(user);
    }

    @DeleteMapping("{id}")
    public void destroy(@PathVariable Long id){
        userService.delete(id);
    }

    private static UserResultData getUserResultData(User user) {
        if (user ==null){
            return null;
        }
        return UserResultData.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

}
