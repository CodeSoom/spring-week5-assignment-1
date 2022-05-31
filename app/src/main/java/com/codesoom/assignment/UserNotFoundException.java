package com.codesoom.assignment;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(int id) {
		super("User not found: " + id);
	}
}
