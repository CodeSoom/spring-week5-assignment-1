package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * User에 대한 비즈니스 로직을 담당.
 */
@Service
@Transactional
public class UserService {
    /**
     * User 데이터 저장소
     */
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 주어진 user를 저장소에 저장한 뒤 반환합니다.
     *
     * @param user 저장하고자 하는 user
     * @return 저장된 user
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 주어진 id와 일치하는 user를 저장소에서 찾아 반환합니다.
     *
     * @param id user의 식별자
     * @return 주어진 id와 일치하는 user
     */
    public User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * 주어진 id와 일치하는 user를 저장소에서 찾아 수정한 뒤 반환합니다.
     *
     * @param id     user의 식별자
     * @param source 수정하고자 하는 user
     * @return 수정된 user
     */
    public User updateUser(Long id, User source) {
        User user = findUser(id);

        user.update(source);

        return user;
    }

    /**
     * 주어진 id와 일치하는 user를 저장소에서 삭제합니다.
     *
     * @param id user의 식별자
     * @return 삭제된 user
     */
    public User deleteUser(Long id) {
        User user = findUser(id);

        userRepository.delete(user);

        return user;
    }
}
