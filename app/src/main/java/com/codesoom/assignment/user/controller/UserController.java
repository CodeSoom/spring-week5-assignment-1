package com.codesoom.assignment.user.controller;


import com.codesoom.assignment.user.application.UserService;
import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.dto.UserData;
import com.codesoom.assignment.user.dto.UserUpdateData;
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

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public User create(@RequestBody @Valid UserData userData) {
    return userService.createUser(userData);
  }

  @PatchMapping("{id}")
  public User update(@RequestBody @Valid UserUpdateData userUpdateData, @PathVariable Long id) {
    return userService.updateUser(userUpdateData, id);
  }


  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void destroy(@PathVariable Long id) {
    userService.deleteUser(id);
  }
}
