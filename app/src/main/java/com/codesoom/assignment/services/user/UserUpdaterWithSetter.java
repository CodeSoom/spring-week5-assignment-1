package com.codesoom.assignment.services.user;

import com.codesoom.assignment.services.user.exception.UserNotFoundException;
import com.codesoom.assignment.services.user.domain.User;
import com.codesoom.assignment.services.user.infra.JpaUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserUpdaterWithSetter implements UpdateService {
    private final JpaUserRepository userRepository;

    public UserUpdaterWithSetter(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 사용자를 찾아 정보를 수정합니다.
     * @param id 찾을 사용자의 아이디
     * @param name 사용자의 새로운 이름
     * @param email 사용자의 새로운 이메일
     * @param password 사용자의 새로운 비밀번호
     * @return 정보가 수정된 사용자
     * @throws UserNotFoundException 사용자를 찾지 못한 경우
     */
    public User execute(Long id, String name, String email, String password) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Setter를 이용해 정보를 수정. Side effect 있을 수도.
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return userRepository.save(user);
    }
}
