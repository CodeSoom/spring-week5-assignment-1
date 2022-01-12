//ToDo
//1. 가입 -> POST /users => 가입 정보(DTO) -> email이 unique key! => 완료
//2. 목록, 상세 보기 => ADMIN!
//3. 사용자 정보 갱신 -> PUT/PATCH /users/{id} => 정보 갱신(DTO) -> 이름만!
// ==> 권한 확인. Authorization
//4. 탈퇴 -> DELETE /users/{id} => soft delete
// ==> 권한 확인. Authorization

package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserModificatonData;
import com.codesoom.assignment.dto.UserRegistrationData;
import com.codesoom.assignment.dto.UserResultData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserResultData create(@RequestBody @Valid UserRegistrationData registrationData) {
        User user = userService.registerUser(registrationData);

        return getUserResultData(user);
    }

    private UserResultData getUserResultData(User user) {
        if(user == null){
            return null;
        }

        return UserResultData.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    @PatchMapping("{id}")
    UserResultData update(
            @PathVariable Long id,
            @RequestBody @Valid UserModificatonData modificatonData){
        User user = userService.updateUser(id, modificatonData);
        return getUserResultData(user);
    }

    @DeleteMapping("{id}")
    void destroy(@PathVariable Long id){
        userService.deleteUser(id);
    }

}
