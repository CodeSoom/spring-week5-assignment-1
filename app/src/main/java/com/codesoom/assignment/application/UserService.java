package com.codesoom.assignment.application;

import javax.transaction.Transactional;

import com.codesoom.assignment.NotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;

import org.springframework.stereotype.Service;

/**
 * User에 대한 생성, 수정, 삭제를 담당한다.
 */
@Service
@Transactional
public class UserService {
    private final Mapper dozerMapper;
    private final UserRepository userRepository;

    public UserService(
        final Mapper dozerMapper, final UserRepository userRepository
    ) {
        this.dozerMapper = dozerMapper;
        this.userRepository = userRepository;
    }

    private User mappingUser(final UserData userData) {
        return dozerMapper.map(userData, User.class);
    }

    /**
     * User를 생성하고 리턴한다.
     *
     * @param userData 생성할 User 데이터
     * @return 생성한 User
     */
    public User createUser(final UserData userData) {
        User user = mappingUser(userData);
        return userRepository.save(user);
    }

    private User findUser(final Long id) {
        return userRepository.findById(id)
            .orElseThrow(
                () -> new NotFoundException(id, User.class.getSimpleName())
            );
    }

    /**
     * User를 삭제한다.
     *
     * @param id 삭제할 User의 id
     * @throws NotFoundException User를 찾을 수 없는 경우
     */
    public void deleteUser(final Long id) {
        userRepository.delete(findUser(id));
    }

    /**
     * User 정보를 수정한다.
     *
     * @param id 수정할 User의 id
     * @param userData 수정할 User 데이터
     * @return 수정한 User
     * @throws NotFoundException User를 찾을 수 없는 경우
     */
    public User updateUser(final Long id, final UserData userData) {
        final User user = findUser(id);
        final User source = mappingUser(userData);
        user.update(source);
        return userRepository.save(user);
    }
}
