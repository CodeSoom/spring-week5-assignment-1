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

    /**
     * 유저 목록에 대한 요청을 처리합니다.
     *
     * @return 유저 목록
     */
    @GetMapping
    public List<User> list() {
        return this.userService.getUsers();
    }

    /**
     * 식별자를 가진 유저에 대한 요청을 처리합니다.
     *
     * @param id 유저 식별자
     * @return 유저
     */
    @GetMapping("{id}")
    public User detail(@PathVariable Long id) {
        return this.userService.getUser(id);
    }

    /**
     * 유저 생성에 대한 요청을 처리합니다.
     *
     * @param userData 생성할 유저의 정보
     * @return 생성된 유저
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserCreateData userData) {
        return this.userService.createUser(userData);
    }

    /**
     * 식별자를 가진 유저에 대한 정보 수정 요청을 처리합니다.
     *
     * @param id 유저 식별자
     * @param userData 수정에 필요한 유저의 정보
     * @return 정보가 수정된 유저
     */
    @PatchMapping("{id}")
    public User update(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdateData userData
    ) {
        return this.userService.updateUser(id, userData);
    }

    /**
     * 식별자를 가진 유저 삭제에 대한 요청을 처리합니다.
     *
     * @param id 유저 식별자
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        this.userService.deleteUser(id);
    }
}
