package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.CreateUserDto;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    public User create(@Valid CreateUserDto createUserDto) {
        // TODO: service에서 생성한 유저를 받아 리턴
        return createUserDto.toEntity();
    }
}
