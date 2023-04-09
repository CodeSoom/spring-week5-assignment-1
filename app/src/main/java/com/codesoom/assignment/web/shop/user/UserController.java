package com.codesoom.assignment.web.shop.user;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.web.shop.user.dto.UserResultData;
import com.codesoom.assignment.web.shop.user.dto.UserRegistrationData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * TODO
 * 1. 가입 -> POSt /users => 가입 정보(DTO) -> eamil이 유니크키
 * 2. 목록, 상세보기 ->ADMIN
 * 3. 사용자 정보 갱신
 */

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserResultData create(@RequestBody @Valid UserRegistrationData requestUserData) {
        User user = userService.registerUser(requestUserData);
        return UserResultData.builder()
                .email(user.getEmail())
                .name(user.getName())
                .id(user.getId())
                .build();
    }

}
