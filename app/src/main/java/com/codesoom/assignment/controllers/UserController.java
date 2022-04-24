package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.CreateUserDto;
import com.codesoom.assignment.dto.UpdateUserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody CreateUserDto createUserDto) {
        User user = modelMapper.map(createUserDto, User.class);
        return userService.create(user);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return userService.get(id);
    }

    @PatchMapping("/{id}")
    public User update(@PathVariable Long id, @Valid @RequestBody UpdateUserDto updateUserDto) {
        User source = modelMapper.map(updateUserDto, User.class);
        return userService.update(id, source);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
