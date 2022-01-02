package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserModificationData;
import com.codesoom.assignment.dto.UserRegistrationData;
import com.codesoom.assignment.dto.UserResultData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * userData로 User를 생성합니다.
     *
     * @param userRegistrationData 생성할 User 데이터
     * @return 생성된 User
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResultData create(@RequestBody @Valid UserRegistrationData userRegistrationData) {
        User user = userService.createUser(userRegistrationData);

        return getUserResultData(user);
    }

    /**
     * id와 일치하는 User를 userData로 변경합니다.
     *
     * @param id 변경할 User의 id
     * @param userModificationData 변경될 User 데이터
     * @return 변경된 User
     */
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResultData update(@PathVariable Long id, @RequestBody @Valid UserModificationData userModificationData) {
        User user = userService.updateUser(id, userModificationData);

        return getUserResultData(user);
    }

    /**
     * id와 일치하는 User를 삭제합니다.
     *
     * @param id 삭제할 User의 id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserResultData delete(@PathVariable Long id) {
        User user = userService.deleteUser(id);

        return getUserResultData(user);
    }

    /**
     * User를 UserResultData로 매핑하여 반환합니다.
     *
     * @param user 매핑될 User 객체
     * @return UserResultData 빌더로 만들어진 UserResultData
     */
    private UserResultData getUserResultData(User user) {
        return UserResultData.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
