package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.dto.UserUpdateInfoData;
import com.codesoom.assignment.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public User signUp(UserData userData) {
        User user = User.builder()
                .email(userData.getEmail())
                .name(userData.getName())
                .password(userData.getPassword())
                .build();

        return userRepository.save(user);
    }

    /**
     * @throws UserNotFoundException Repository 에서 User를 찾지 못했을 때 던지는 예외
     */
    public User updateInfo(Long id, UserUpdateInfoData userUpdateInfoData) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.updateInfo(
                userUpdateInfoData.getName(),
                userUpdateInfoData.getPassword()
        );

        return user;
    }
}
