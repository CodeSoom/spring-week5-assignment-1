package com.codesoom.assignment.user.application;

import com.codesoom.assignment.exceptions.user.UserNotFoundException;
import com.codesoom.assignment.user.application.in.UserUseCase;
import com.codesoom.assignment.user.application.in.command.UserCreateRequest;
import com.codesoom.assignment.user.application.in.command.UserUpdateRequest;
import com.codesoom.assignment.user.application.out.UserRepository;
import com.codesoom.assignment.user.domain.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserUseCase {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(final UserCreateRequest createUserRequest) {
        return userRepository.save(createUserRequest.toEntity());
    }

    @Override
    public User updateUser(final Long id, final UserUpdateRequest updateUserRequest) {
        User user = findUser(id);

        user.update(updateUserRequest.toEntity());

        return user;
    }

    @Override
    public Long deleteUser(final Long id) {
        User user = findUser(id);

        userRepository.delete(user);

        return user.getId();
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
