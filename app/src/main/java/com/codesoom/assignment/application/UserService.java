package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserModificationData;
import com.codesoom.assignment.dto.UserRegistrationData;
import com.codesoom.assignment.exception.UserEmailDuplicationException;
import com.codesoom.assignment.exception.UserNotFoundException;
import com.codesoom.assignment.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * id 값과 일치하는 User를 리턴합니다.
     *
     * @param id 조회할 User의 id
     * @return User의 id와 일치하는 User
     * @throws UserNotFoundException User의 id와 일치하는 User가 없을 경우
     */
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * userData로 User를 생성하고, 생성된 User를 리턴합니다.
     *
     * @param userRegistrationData 생성할 User 데이터
     * @return 생성된 User
     */
    public User createUser(UserRegistrationData userRegistrationData) {
        String email = userRegistrationData.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new UserEmailDuplicationException(email);
        }

        User user = User.builder()
                .name(userRegistrationData.getName())
                .password(userRegistrationData.getPassword())
                .email(userRegistrationData.getEmail())
                .build();

        return userRepository.save(user);
    }

    /**
     * targetId 값과 일치하는 User를 찾아 source 데이터로 변경하고, 변경된 User를 리턴합니다.
     *
     * @param targetId 변경할 User의 id
     * @param source 변경될 User 데이터
     * @return 변경된 User
     * @throws UserNotFoundException User의 targetId와 일치하는 User가 없을 경우
     */
    public User updateUser(Long targetId, UserModificationData source) {
        User user = getUser(targetId);

        user.change(source.getName(),
                source.getPassword());

        return userRepository.save(user);
    }

    /**
     * targetId 값과 일치하는 User를 삭제합니다.
     *
     * @param targetId 삭제할 User의 id
     * @throws UserNotFoundException User의 targetId와 일치하는 User가 없을 경우
     */
    public void deleteUser(Long targetId) {
        try {
            userRepository.deleteById(targetId);
        } catch (Exception e) {
            throw new UserNotFoundException(targetId);
        }
    }
}
