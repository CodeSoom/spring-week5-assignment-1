package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserCommandService;
import com.codesoom.assignment.application.UserQueryService;
import com.codesoom.assignment.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserQueryService query;
    private final UserCommandService command;

    public UserController(UserQueryService query, UserCommandService command) {
        this.query = query;
        this.command = command;
    }

    @GetMapping
    public List<User> findAll(){
        return query.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user){
        return command.save(user);
    }

    @PostMapping("{id}")
    public User update(@PathVariable Long id , @RequestBody User user){
        return command.update(query.findUser(id) , user);
    }

    @DeleteMapping("{id}")
    public User delete(@PathVariable Long id){
        return command.delete(query.findUser(id));
    }
}
