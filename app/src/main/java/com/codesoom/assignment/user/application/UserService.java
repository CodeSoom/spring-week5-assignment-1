package com.codesoom.assignment.user.application;

import com.codesoom.assignment.user.application.in.UserUseCase;
import com.codesoom.assignment.user.application.in.command.UserCreateRequest;
import com.codesoom.assignment.user.application.in.command.UserUpdateRequest;
import com.codesoom.assignment.user.application.out.UserRepository;
import com.codesoom.assignment.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserUseCase {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(final UserCreateRequest createUserRequest) {
        return null;
    }

    @Override
    public User updateUser(final Long id, final UserUpdateRequest updateUserRequest) {
        return null;
    }

    @Override
    public Long deleteUser(final Long id) {
        return null;
    }
}
