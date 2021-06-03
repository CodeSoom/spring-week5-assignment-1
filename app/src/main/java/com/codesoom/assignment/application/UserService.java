package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import org.springframework.stereotype.Service;

/**
 * 사용자 정보를 등록,수정,삭제하는 서비스 로직입니다.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 사용자 정보를 저장합니다.
     * @param userData UserData 저장할 사용자 정보.
     * @return UserData 저장한 사용자 정보 반환.
     */
    public UserData createUser(UserData userData) {
        User user = userRepository.save(changeUserDataToUser(userData));
        return changeUserToUserData(user);
    }

    /**
     * 사용자 정보를 수정합니다.
     * @param id Long 수정할 사용자 아이디.
     * @param sourceData UserData 수정할 사용자 정보.
     * @return UserData 수정한 사용자 정보 반환.
     */
    public UserData updateUser(Long id, UserData sourceData) {
        User foundUser = findUser(id);
        User user = userRepository.save(changeUserDataToUser(sourceData));
        return changeUserToUserData(user);
    }

    /**
     * 사용자 정보를 삭제합니다.
     * @param id Long 삭제할 사용자 아이디.
     * @return UserData 삭제한 사용자 정보 반환.
     */
    public UserData deleteUser(Long id) {
        User user = findUser(id);
        userRepository.deleteUserById(id);
        return changeUserToUserData(user);
    }

    private UserData changeUserToUserData(User user) {
        return UserData.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }

    private User changeUserDataToUser(UserData userData) {
        return User.builder()
                .id(userData.getId())
                .email(userData.getEmail())
                .name(userData.getName())
                .password(userData.getPassword())
                .build();
    }

    private User findUser(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
