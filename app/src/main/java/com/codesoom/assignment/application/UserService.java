package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import org.springframework.stereotype.Service;

/**
 * 유저의 생성, 수정, 삭제를 수행한다.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 주어진 아이디에 해당하는 유저를 리턴한다.
     *
     * @param id - 조회하려는 유저 아이디.
     * @return 주어진 {@code id}에 해당하는 유저.
     * @throws UserNotFoundException 만약
     *         {@code id}가 저장되어 있지 않은 경우
     */
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * 주어진 유저를 저장하고 해당 유저를 리턴한다.
     *
     * @param userData - 새로 저장하고자 하는 유저
     * @return 새로 저장된 유저
     */
    public User createUser(UserData userData) {
        User user = userData.toEntity();
        return userRepository.save(user);
    }

    /**
     * 주어진 아이디에 해당하는 유저를 수정하고 해당 유저를 리턴한다.
     *
     * @param id - 수정하고자 하는 유저 아이디
     * @param userData - 수정 할 새로운 유저
     * @return 수정된 유저
     * @throws UserNotFoundException 만약
     *         {@code id}가 저장되어 있지 않은 경우
     */
    public User updateUser(Long id, UserData userData) {
        User user = getUser(id);

        user.update(
                userData.getName(),
                userData.getEmail(),
                userData.getPassword()
        );

        return user;
    }

    /**
     * 주어진 아이디에 해당하는 유저를 삭제하고 해당 유저를 리턴한다.
     *
     * @param id - 삭제하고자 하는 유저 아이디
     * @return 삭제된 유저
     * @throws UserNotFoundException 만약 주어진
     *         {@code id}가 저장되어 있지 않은 경우
     */
    public User deleteUser(Long id) {
        User user = getUser(id);

        userRepository.delete(user);

        return user;
    }
}
