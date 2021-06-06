package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.userdata.UserCreateData;
import com.codesoom.assignment.dto.userdata.UserUpdateData;
import com.codesoom.assignment.exception.UserNotFoundException;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    @Autowired
    public UserService(UserRepository userRepository, Mapper dozerMapper) {
        this.userRepository = userRepository;
        this.mapper = dozerMapper;
    }

    /**
     * 유저 목록을 반환합니다.
     *
     * @return 유저 목록
     */
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    /**
     * 식별자를 가진 유저를 반환합니다.
     *
     * @param id 유저 식별자
     * @return 식별자를 가진 유저
     */
    public User getUser(Long id) {
        return findUser(id);
    }

    /**
     * 유저를 생성하고 반환합니다.
     *
     * @param userData 유저의 정보
     * @return 생성된 유저
     */
    public User createUser(UserCreateData userData) {
        User user = this.mapper.map(userData, User.class);
        return this.userRepository.save(user);
    }

    /**
     * 유저의 정보를 수정하고 반환합니다.
     *
     * @param id 유저 식별자
     * @param userData 수정에 필요한 유저의 정보
     * @return 수정된 정보를 가진 유저
     */
    public User updateUser(Long id, UserUpdateData userData) {
        User user = findUser(id);
        this.mapper.map(userData, user);
        return userRepository.save(user);
    }

    /**
     * 식별자를 가진 유저를 삭제하고 반환합니다.
     *
     * @param id 유저 식별자
     * @return 삭제된 유저
     */
    public User deleteUser(Long id) {
        User user = findUser(id);
        this.userRepository.delete(user);
        return user;
    }

    private User findUser(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
