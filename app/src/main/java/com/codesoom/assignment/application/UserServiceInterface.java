package com.codesoom.assignment.application;

import java.util.List;

import com.codesoom.assignment.dto.UserDTO;

public interface UserServiceInterface {

	T createUser(S source);

	UserDTO.Response getUser(int id);

	void deleteUser(int id);

	List<T> getUsers();

	T updateUsers(int id, U source);
}
