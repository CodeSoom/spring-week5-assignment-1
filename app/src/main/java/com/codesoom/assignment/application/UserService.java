package com.codesoom.assignment.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.github.dozermapper.core.Mapper;

@Service
public class UserService {

    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserService(Mapper dozerMapper, UserRepository userRepository) {
        this.mapper = dozerMapper;
        this.userRepository = userRepository;
    }

    /**
     * 새로운 유저를 생성해 반환합니다.
     * @param user
     * @return
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 유저 정보를 변경하고 반환합니다.
     * @param id
     * @param user
     * @return
     */
    public User updateUser(Long id, User user) {
        return userRepository.save(user);
    }

    /**
     * 유저를 삭제하고 반환합니다.
     * @param id
     * @return
     */
    public User deleteUser(Long id) {
        return userRepository.delete(id);
    }
}
