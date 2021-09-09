package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserData createUser(UserData userData) {
        User user = User.builder()
                .name(userData.getName())
                .password(userData.getPassword())
                .email(userData.getEmail())
                .build();

        return new UserData(userRepository.save(user));
    }

    public UserData selectUser(Long id) {
        return new UserData(getUser(id));
    }

    public List<UserData> selectUsers() {
        return UserData.ofList(userRepository.findAll());
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserData modifyUser(Long id, UserData userData) {
        User user = getUser(id);
        user.modifyUser(userData);
        return new UserData(userRepository.save(user));
    }


}
