package com.codesoom.assignment.user.controllers;

import com.codesoom.assignment.user.application.UserService;
import com.codesoom.assignment.user.dto.UserResponseDto;
import com.codesoom.assignment.user.dto.UserSaveRequestDto;
import com.codesoom.assignment.user.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 사용자에 대한 요청을 처리한다.
 */
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 모든 사용자 정보를 응답합니다.
     */
    @GetMapping
    public List<UserResponseDto> getUsers() {
        return userService.getUsers();
    }

    /**
     * 주어진 id에 해당하는 사용자 정보를 응답합니다.
     *
     * @param id 찾고자 하는 사용자 id
     * @return 사용자 정보
     */
    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    /**
     * 주어진 id에 해당하는 사용자 정보를 갱신하고, 갱신된 정보를 응답합니다.
     *
     * @param id         갱신하고자하는 사용자 식별자
     * @param requestDto 수정하고자 하는 사용자 정보
     * @return 갱신된 사용자 정보
     */
    @PatchMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id,
                                      @Valid @RequestBody UserUpdateRequestDto requestDto) {
        return userService.updateUser(id, requestDto);
    }

    /**
     * 주어진 사용자 정보를 저장하고, 등록된 사용자 정보를 응답합니다.
     *
     * @param requestDto 저장하고자 하는 사용자 정보
     * @return 등록된 사용자
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@Valid @RequestBody UserSaveRequestDto requestDto) {
        return userService.createUser(requestDto);
    }

    /**
     * 주어진 id에 해당하는 사용자를 삭제합니다.
     *
     * @param id 삭제하고자 하는 사용자의 식별자
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Long deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
