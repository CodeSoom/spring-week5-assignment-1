package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 유저 정보를 관리하는 서비스.
 */
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 주어진 유저를 생성합니다.
     *
     * @param user 생성할 정보가 담긴 유저 객체.
     */
    public User create(User user) {
        return userRepository.save(user);
    }

    /**
     * 주어진 id에 해당하는 유저를 변경합니다.
     *
     * @param id   대상 유저 id.
     * @param user 변경할 정보가 담긴 유저 객체.
     * @throws UserNotFoundException 대상 유저 id를 찾지 못했을 때.
     */
    public void modify(long id, User user) throws UserNotFoundException {
    }

    /**
     * 주어진 id에 해당하는 유저를 삭제합니다.
     *
     * @param id 대상 유저 id.
     * @throws UserNotFoundException 대상 유저 id를 찾지 못했을 때.
     */
    public void delete(Long id) throws UserNotFoundException {
    }
}
