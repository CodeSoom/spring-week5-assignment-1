package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.dto.UserCreateDto;
import com.codesoom.assignment.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid UserCreateDto userCreateDto){
        this.userService.create(userCreateDto);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id , @RequestBody @Valid UserRequest userRequest){
        this.userService.update(id , userRequest);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){
        this.userService.delete(id);
    }

}
