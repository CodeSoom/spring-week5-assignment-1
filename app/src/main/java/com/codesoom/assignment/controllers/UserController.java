package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.CreateUserRequestDto;
import com.codesoom.assignment.dto.CreateUserResponseDto;
import com.codesoom.assignment.dto.UpdateUserRequestDto;
import com.codesoom.assignment.dto.UpdateUserResponseDto;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 관련 HTTP 요청 처리 담당.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원을 생성하고, 해당 회원 정보를 리턴합니다.
     *
     * @param createUserRequestDto 회원 생성 정보
     * @return 회원 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponseDto create(
        @RequestBody @Valid CreateUserRequestDto createUserRequestDto
    ) {
        User user = userService.createUser(createUserRequestDto.toEntity());

        return new CreateUserResponseDto(user);
    }

    /**
     * 회원 정보를 수정하고 리턴합니다.
     *
     * @param id                   식별자
     * @param updateUserRequestDto 수정할 회원 정보
     * @return 수정된 회원 정보
     */
    @PatchMapping("{id}")
    public UpdateUserResponseDto update(
        @PathVariable Long id,
        @RequestBody @Valid UpdateUserRequestDto updateUserRequestDto
    ) {
        User user = userService.updateUser(id, updateUserRequestDto.toEntity());

        return new UpdateUserResponseDto(user);
    }


    /**
     * 식별자로 회원을 찾아 삭제합니다.
     *
     * @param id 식별자
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
        @PathVariable Long id
    ) {
        userService.deleteUser(id);
    }
}
