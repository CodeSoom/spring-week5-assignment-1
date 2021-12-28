package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 유저를 관리합니다.
 */
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /**
     * 유저를 저장하고 저장된 유저를 리턴한다.
     *
     * @param source 저장할 유저
     * @return 저장된 유저
     */
    public User createUser(User source) {
        User savedUser = User.createSaveUser(
                source.getName(),
                source.getEmail(),
                source.getPassword()
        );

        return userRepository.save(savedUser);
    }
}
