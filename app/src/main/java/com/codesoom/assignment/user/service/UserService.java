package com.codesoom.assignment.user.service;

import com.codesoom.assignment.common.exceptions.UserNotFoundException;
import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 회원과 관련된 비즈니스 로직을 담당합니다.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 모든 회원을 리턴합니다.
     */
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * 주어진 id에 해당하는 회원을 찾아 리턴합니다.
     *
     * @param id 찾고자 하는 회원의 식별자
     * @return 찾은 회원
     */
    public User getUser(Long id) throws UserNotFoundException {
        return findUser(id);
    }

    /**
     * 주어진 회원을 저장한 뒤, 저장된 회원을 리턴합니다.
     *
     * @param user 저장하고자 하는 회원
     * @return 저장된 회원
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 주어진 id에 해당하는 회원을 찾아 수정하고, 수정된 회원을 리턴합니다.
     *
     * @param id 수정하고자 하는 회원의 식별자
     * @param user 수정하고자 하는 회원
     * @return 수정된 회원
     * @throws UserNotFoundException 주어진 식별자에 해당하는 회원을 찾지 못했을 경우
     */
    public User updateUser(Long id, User user)
            throws UserNotFoundException {
        User foundUser = findUser(id);

        return foundUser.changeWith(user);
    }

    /**
     * 주어진 id에 해당하는 회원을 찾아 삭제합니다.
     *
     * @param id 삭제하고자 하는 회원의 식별자
     * @throws UserNotFoundException 주어진 식별자에 해당하는 회원을 찾지 못했을 경우
     */
    public void deleteUser(Long id) throws UserNotFoundException {
        User user = findUser(id);

        userRepository.delete(user);
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("존재하지 않는 회원 id가 주어졌으므로 회원을 찾을 수 없습니다. 문제의 id = " + id));
    }

}
