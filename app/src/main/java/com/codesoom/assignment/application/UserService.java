package com.codesoom.assignment.application;

import com.codesoom.assignment.dto.UserData;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	public List<UserData> getAll() {
		return Collections.emptyList();
	}

	public UserData getDetail(Long id) {
		return null;
	}

	public UserData create(UserData user) {
		return user;
	}

	public UserData updateDetail(Long id, UserData userData) {
		return userData;
	}

	public void delete(Long id) {

	}
}
