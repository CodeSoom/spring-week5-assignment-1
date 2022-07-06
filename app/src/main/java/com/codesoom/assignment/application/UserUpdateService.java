package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.infra.JpaUserRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserUpdateService {
    private final JpaUserRepository userRepository;

    public UserUpdateService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(Long id, String name, String email, String password) throws NotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id " + id));

//        방법 1 - repository에서 받아온 user object를 id만 남기고 나머지를 setter로 수정하는 방법
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return userRepository.save(user);

//        방법 2 - 새로운 user object를 생성하여  repository에 저장하는 방법
//        User updatedUser = new User(user.getId(), name, email, password);
//        return userRepository.save(updatedUser);
    }
}
