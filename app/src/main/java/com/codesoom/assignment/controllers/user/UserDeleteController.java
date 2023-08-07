package com.codesoom.assignment.controllers.user;

import com.codesoom.assignment.application.user.UserDeleter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserDeleteController {
    private final UserDeleter userDeleter;

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userDeleter.delete(id);
    }

}
