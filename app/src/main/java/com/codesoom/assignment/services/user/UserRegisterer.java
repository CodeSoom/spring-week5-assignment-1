package com.codesoom.assignment.services.user;

import com.codesoom.assignment.services.user.domain.User;
import com.codesoom.assignment.services.user.infra.JpaUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterer {
    private final JpaUserRepository userRepository;

    public UserRegisterer(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 사용자를 생성합니다.
     * @param name 새로운 이름
     * @param email 새로운 이메일
     * @param password 새로운 비밀번호
     * @return 새롭게 생성된 사용자
     */
    public User execute(String name, String email, String password) {
        return userRepository.save(new User(name, email, password));
    }
}
