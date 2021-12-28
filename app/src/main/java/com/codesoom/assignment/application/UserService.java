package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;

public class UserService {

    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserService(Mapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    public User createUser(UserData userData){
        return null;
    }

    public User updateUser(Long id, UserData userData){
        return null;
    }

    public User deleteUser(Long id){
        return null;
    }

}
