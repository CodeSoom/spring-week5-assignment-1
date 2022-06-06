package com.codesoom.assignment.application;

import java.util.List;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserDTO;
import com.codesoom.assignment.dto.UserResponse;

public interface UserServiceInterface {

	UserResponse createUser(UserDTO.CreateUser source);

	UserResponse getUser(int id);

	void deleteUser(int id);

	List<UserResponse> getUsers();

	UserResponse updateUsers(int id, UserDTO.UpdateUser source);
}
