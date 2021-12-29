package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 유저를 관리합니다.
 */
@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 유저 목록을 반환합니다.
     *
     * @return 유저 목록
     */
    List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 주어진 id와 일치하는 유저를 반환합니다.
     *
     * @param id 유저 id
     * @return 주어진 id와 일치하는 유저
     * @throws '유저를 찾지 못했다'는 예외
     */
    User findUserById(Long id) {
        // TODO : null이 아닌 예외를 던지도록 변경 필요!
        return userRepository.findById(id).orElse(null);
    }

    /**
     * 주어진 유저와 동일한 유저를 생성하고 반환합니다.
     *
     * @param user 생성할 유저
     * @return 생성한 유저
     */
    User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 주어진 id와 일치하는 유저를 주어진 source 유저와 동일하도록 변경합니다.
     *
     * @param id     유저 id
     * @param source 변경할 유저의 source
     * @return 변경한 유저
     */
    User updateUser(Long id, User source) {
        // TODO : null이 아닌 예외를 던지도록 변경 필요!
        User user = userRepository.findById(id).orElse(null);
        user.setName(source.getName());
        user.setEmail(source.getEmail());
        user.setPassword(source.getPassword());

        return userRepository.save(user);
    }

    /**
     * 주어진 id와 일치하는 유저를 삭제합니다.
     *
     * @param id 유저 id
     */
    void deleteUser(Long id) {
        // TODO : null이 아닌 예외를 던지도록 변경 필요!
        User user = userRepository.findById(id).orElse(null);

        userRepository.delete(user);
    }
}
