package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserCrudService;
import com.codesoom.assignment.controllers.dtos.UserRequestData;
import com.codesoom.assignment.controllers.dtos.UserResponseData;
import com.codesoom.assignment.controllers.interfaces.*;
import com.codesoom.assignment.domain.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserCrudController implements UserListController, UserDetailController, UserCreateController, UserUpdateController {
    private final UserCrudService service;

    public UserCrudController(UserCrudService service) {
        this.service = service;
    }

    @GetMapping
    @Override
    public List<UserResponseData> list() {
        return service.showAll().stream()
                .map(UserResponseData::from)
                .collect(Collectors.toList());
    }


    @GetMapping("{id}")
    @Override
    public UserResponseData detail(@PathVariable Long id) {
        User user = service.showById(id);
        return UserResponseData.from(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public UserResponseData create(@RequestBody @Valid UserRequestData requestData) {
        User user = service.create(requestData.toEntity());
        return UserResponseData.from(user);
    }

    @PatchMapping("{id}")
    @Override
    public UserResponseData update(@PathVariable Long id, @RequestBody @Valid UserRequestData requestData) {
        User user = service.update(id, requestData.toEntity());
        return UserResponseData.from(user);
    }
}
