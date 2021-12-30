// 1. createUser -> 회원 생성
// 2. updateUser -> 회원 수정
// 3. deleteUser -> 회원 삭제

package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserService(Mapper dozerMapper, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = dozerMapper;
    }

    public User create(UserData userData) {
        User user = mapper.map(userData, User.class);
        return userRepository.save(user);
    }

    public User update(Long id, UserData userData) {
        User user = findUser(id);

        user.change(
                userData.getName(),
                userData.getEmail(),
                userData.getPassword()
        );

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
