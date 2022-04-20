package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    public User createUser(UserData userData) {
        return null;
    }

    public User updateUser(Long id, UserData userData) {
        return null;
    }

    public User deleteUser(Long id) {
        return null;
    }
}
