package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserData createUser(UserData userData) {

        User user = userRepository.save(new User().chageData(userData));
        return changeUserData(user);
    }

    public UserData updateUser(Long id, UserData sourceData) {
        User user = userRepository.save(findUser(id)
                .chageData(sourceData));
        return changeUserData(user);
    }

    public UserData deleteUser(Long id) {
        User user = findUser(id);
        userRepository.deleteUserById(id);
        return changeUserData(user);
    }

    private UserData changeUserData(User user) {
        return UserData.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }

    private User findUser(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
