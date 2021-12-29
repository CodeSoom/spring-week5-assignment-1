// 1. createUser -> 회원 생성
// 2. updateUser -> 회원 수정
// 3. deleteUser -> 회원 삭제

package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(Long id, User source) {
        User user = findUser(id);

        user.change(source);

        return user;
    }

    public User delete(Long id) {
        User user = findUser(id);

        userRepository.delete(user);

        return user;
    }

    public User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
