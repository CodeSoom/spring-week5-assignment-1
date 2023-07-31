package com.codesoom.assignment.controllers.users;

import com.codesoom.assignment.application.UserUpdater;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UpdateUserData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserUpdateController {

    private final UserUpdater userUpdater;

    @PatchMapping("{id}")
    public User update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserData userData) {
        return userUpdater.update(id, userData);
    }

}
