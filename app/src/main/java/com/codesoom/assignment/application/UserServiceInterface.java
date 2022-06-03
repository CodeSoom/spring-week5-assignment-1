package com.codesoom.assignment.application;

import com.codesoom.assignment.dto.UserDTO;

import java.util.List;

public interface UserServiceInterface {

    UserDTO.Response createUser(UserDTO.CreateUser source);

    UserDTO.Response getUser(int id);

    void deleteUser(int id);

    List<UserDTO.Response> getUsers();

    UserDTO.Response updateUsers(int id, UserDTO.UpdateUser source);
}
