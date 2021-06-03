package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.dto.userdata.UserCreateData;
import com.codesoom.assignment.dto.userdata.UserUpdateData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // TODO: 추상클래스를 만들어서 Controller가 공통적으로 가지는 메소드 추상화하기
    // 아마 제네릭도 필요할 듯

    @GetMapping
    public List<User> list() {
        return this.userService.getUsers();
    }

    @GetMapping("{id}")
    public User detail(@PathVariable Long id) {
        return this.userService.getUser(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserCreateData userData) {
        return this.userService.createUser(userData);
    }

    @PatchMapping("{id}")
    public User update(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdateData userData
    ) {
        return this.userService.updateUser(id, userData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        this.userService.deleteUser(id);
    }
}
