package com.codesoom.assignment.controllers;


import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.dto.UserValidationGroups;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
<<<<<<< HEAD
 * 사용자 정보에 대한 http 처리를 담당합니다.
=======
 * 사용자 등록, 수정, 삭제에 대한 Request, Response 처리
>>>>>>> 22c8bd2609d69f3ecd808fca4d6127023b3f2971
 */
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 사용자 정보를 등록한다.
<<<<<<< HEAD
     * @param userData 사용자 등록 정보
     * @return 등록한 사용자 정보를
=======
     * @param userData 사용자 등록 정보.
     * @return 등록한 사용자 정보를 반환.
>>>>>>> 22c8bd2609d69f3ecd808fca4d6127023b3f2971
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserData createUser(@RequestBody @Validated(UserValidationGroups
            .createUserGroup.class) UserData userData){
        return userService.createUser(userData);
    }

    /**
     * 사용자 정보를 수정한다.
<<<<<<< HEAD
     * @param id 수정할 사용자 아이디
     * @param userData 수정할 내용
     * @return 수정한 사용자 정보를
=======
     * @param id 수정할 사용자 아이디.
     * @param userData 수정할 내용.
     * @return 수정한 사용자 정보를 반환
>>>>>>> 22c8bd2609d69f3ecd808fca4d6127023b3f2971
     */
    @PatchMapping("{id}")
    public UserData updateUser(@PathVariable Long id,@RequestBody @Validated(UserValidationGroups
            .updateUserGroup.class) UserData userData){
        return userService.updateUser(id, userData);
    }

    /**
     * 사용자 정보를 삭제한다.
<<<<<<< HEAD
     * @param id 삭제할 사용자 아이디
     * @return 삭제한 사용자 정보를
=======
     * @param id 삭제할 사용자 아이디.
     * @return 삭제한 사용자 정보를 반환.
>>>>>>> 22c8bd2609d69f3ecd808fca4d6127023b3f2971
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserData deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

}
