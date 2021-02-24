package com.codesoom.assignment.user.application;

import com.codesoom.assignment.common.exception.UserNotFoundException;
import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.domain.UserRepository;
import com.codesoom.assignment.user.dto.UserData;
import com.codesoom.assignment.user.dto.UserUpdate;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 사용자 정보와 관련된 비즈니스 로직을 처리합니다.
 */
@Service
@Transactional
public class UserService {
    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserService(
            Mapper dozerMapper,
            UserRepository userRepository
    ) {
        this.mapper = dozerMapper;
        this.userRepository = userRepository;
    }

    /**
     * 새롭게 주어진 회원 정보를 저장하고, 그 회원을 리턴합니다.
     *
     * @param userData 새롭게 저장할 회원
     * @return 저장된 회원
     */
    public User createUser(UserData userData) {
        User user = mapper.map(userData, User.class);

        return userRepository.save(user);
    }

    /**
     * 주어진 id에 해당하는 회원을 리턴합니다.
     *
     * @param id 회원의 식별자
     * @return 주어진 id에 해당하는 회원
     * @throws UserNotFoundException 주어진 id가 회원 목록에 없는 경우
     */
    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

    }

    /**
     * 주어진 id에 해당하는 회원을 찾은 후 수정합니다.
     *
     * @param id       회원의 식별자
     * @param userUpdate 수정하려는 회원
     * @return 수정된 회원
     * @throws UserNotFoundException 주어진 id가 회원 목록에 없는 경우
     */
    public User updateUser(Long id, UserUpdate userUpdate) {
        User user = findUser(id);
        user.changeWith(mapper.map(userUpdate, User.class));

        return user;
    }

    /**
     * 주어진 id에 해당하는 회원을 찾아 삭제합니다.
     *
     * @param id 회원의 식별자
     * @throws UserNotFoundException 주어진 id가 회원 목록에 없는 경우
     */
    public void deleteUser(Long id) {
        User user = findUser(id);
        userRepository.delete(user);

    }
}
