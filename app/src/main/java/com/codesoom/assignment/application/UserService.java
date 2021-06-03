package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.dto.userdata.UserCreateData;
import com.codesoom.assignment.dto.userdata.UserUpdateData;
import com.codesoom.assignment.exception.UserNotFoundException;
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

    // TODO: 메소드 이름 수정하고 제네릭을 이용하면 여기 메소드도 추상화가 가능할 것 같다.

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User getUser(Long id) {
        return findUser(id);
    }

    public User createUser(UserCreateData userData) {
        User user = User.builder()
                .name(userData.getName())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .build();
        return this.userRepository.save(user);
    }

    public User updateUser(Long id, UserUpdateData userData) {
        User user = findUser(id);

        user.change(
                userData.getName(),
                userData.getEmail(),
                userData.getPassword()
        );

        return user;
    }

    public User deleteUser(Long id) {
        User user = findUser(id);

        this.userRepository.delete(user);

        return user;
    }

    private User findUser(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
