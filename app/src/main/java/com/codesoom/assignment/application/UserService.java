package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 서비스를 이용하는 사용자들의 정보를 전달받아 처리한다.
 */
@Service
@Transactional
public class UserService {
    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserService(Mapper dozerMapper, UserRepository userRepository) {
        this.mapper = dozerMapper;
        this.userRepository = userRepository;
    }

    /**
     * 유저 리스트를 반환한다.
     *
     * @return List<User>, 저장된 유저 리스트
     */
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * 저장된 유저정보를 반환한다.
     *
     * @param id 저장된 유저의 id
     * @return User, 저장된 유저정보
     */
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * 유저정보를 저장하고, 저장한 유저정보를 반환한다.
     *
     * @param userData 저장할 유저의 데이터
     * @return User, 저장한 유저정보
     */
    public User createUser(UserData userData) {
        User user = mapper.map(userData, User.class);
        return userRepository.save(user);
    }

    /**
     * 저장된 유저정보를 수정하고, 수정한 유저정보를 반환한다.
     *
     * @param userData 수정할 유저정보
     * @return User, 수정한 유저정보
     */
    public User updateUser(UserData userData) {
        User user = getUser(userData.getId());

        user.changeWith(mapper.map(userData, User.class));

        return user;
    }

    /**
     * 저장된 유저정보를 삭제하고, 삭제한 유저정보를 반환한다.
     *
     * @param id 저장된 유저의 id
     * @return User, 삭제된 유저정보
     */
    public User deleteUser(Long id) {
        final User user = getUser(id);

        userRepository.delete(user);

        return user;
    }
}
