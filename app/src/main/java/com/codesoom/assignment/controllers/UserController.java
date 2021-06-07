package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 사용자에 대한 요청을 처리한다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    /**
     * 모든 사용자 정보를 응답합니다.
     */
    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    /**
     * 주어진 id에 해당하는 사용자 정보를 응답합니다.
     *
     * @param id 찾고자 하는 사용자 id
     * @return 사용자 정보
     * @throws com.codesoom.assignment.exception.UserNotFoundException
     */
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    /**
     * 주어진 사용자 정보를 저장하고, 등록된 사용자 정보를 응답합니다.
     *
     * @param dto 저장하고자 하는 사용자 정보
     * @return 등록된 사용자
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody UserDto dto) {
        return userService.createUser(dto);
    }

    /**
     * 주어진 id에 해당하는 사용자 정보를 갱신하고, 갱신된 정보를 응답합니다.
     *
     * @param id 갱신하고자하는 사용자 식별자
     * @param requestDto 수정하고자 하는 사용자 정보
     * @return 갱신된 사용자 정보
     */
    @PatchMapping("/{id}")
    public User updateUser(@PathVariable Long id,
                                   @Valid @RequestBody UserDto requestDto) {
        return userService.updateUser(id, requestDto);
    }

    /**
     * 주어진 id에 해당하는 사용자를 삭제합니다.
     *
     * @param id 삭제하고자 하는 사용자의 식별자
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
