package com.codesoom.assignment.controllers.user;

import com.codesoom.assignment.application.user.UserUpdater;
import com.codesoom.assignment.domain.user.User;
import com.codesoom.assignment.dto.user.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserUpdateController {
    private final UserUpdater userUpdater;

    @PatchMapping({"/{id}"})
    public User update(@PathVariable Long id,
                       @RequestBody UserData userData) {
        return userUpdater.update(id, userData);
    }
}
