package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.infra.JpaUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDeleteService {
    private final JpaUserRepository userRepository;

    public UserDeleteService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 사용자를 찾아 삭제합니다.
     * @param id 찾을 사용자의 아이디
     * @throws UserNotFoundException 사용자를 찾지 못한 경우
     */
    public void execute(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
    }
}
