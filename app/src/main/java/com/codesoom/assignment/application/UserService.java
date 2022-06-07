package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserCreateData;
import com.codesoom.assignment.dto.UserUpdateData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * User 에 대한 비즈니스 로직
 */
@Service
@Transactional
public class UserService {

    /**
     * User 데이터 저장 장소
     */
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 주어진 User 를 저장하고 반환
     *
     * @param userCreateData 저장할 user
     * @return 저장된 user
     */
    public User createUser(UserCreateData userCreateData) {
        User user = User.builder()
                .name(userCreateData.getName())
                .email(userCreateData.getEmail())
                .password(userCreateData.getPassword())
                .build();

        return userRepository.save(user);
    }

    /**
     * 주어진 id 와 일치하는 user 를 찾아서 수정한 후 반환
     *
     * @param id 식별자
     * @param userUpdateData 수정할 user
     * @return 수정된 user
     */
    public User updateUser(Long id, UserUpdateData userUpdateData) {
        User user = getUser(id);

        user.update(
                userUpdateData.getName(),
                userUpdateData.getEmail(),
                userUpdateData.getPassword()
        );

        return user;
    }

    /**
     * 주어진 id 와 일치하는 user 를 삭제
     *
     * @param id 식별자
     * @return 삭제된 user
     */
    public User deleteUser(Long id) {
        User user = getUser(id);

        userRepository.delete(user);

        return user;
    }

    /**
     * 주어진 id 와 일치하는 user 를 찾아 수정한 후 반환
     *
     * @param id 식별자
     * @return 주어진 id 와 일치하는 user
     */
    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
