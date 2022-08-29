package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping
    public List<User> findAll(){
        return service.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user){
        return service.save(user);
    }

    @PostMapping("{id}")
    public User update(@PathVariable Long id , @RequestBody User user){
        return service.update(id , user);
    }

    @DeleteMapping("{id}")
    public User delete(@PathVariable Long id){
        return service.delete(id);
    }
}
