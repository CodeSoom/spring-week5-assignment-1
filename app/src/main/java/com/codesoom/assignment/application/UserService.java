package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.errors.UserEmailDuplicationException;
import com.codesoom.assignment.errors.UserNotFoundException;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 사용자를 관리합니다.
 */
@Service
@Transactional
public class UserService {
    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserService(
            Mapper dozerMapper,
            UserRepository userRepository) {
        this.mapper = dozerMapper;
        this.userRepository = userRepository;
    }

    /**
     * 사용자 목록을 반환합니다.
     *
     * @return 사용자 목록
     */
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * id에 해당하는 사용자를 반환합니다
     *
     * @param id 사용자 id
     * @return 해당하는 사용자
     */
    public User getUser(Long id) {
        return findUser(id);
    }

    /**
     * 사용자를 저장하고 반환합니다.
     *
     * @param userData 사용자 정보
     * @return 저장된 사용자
     */
    public User createUser(UserData userData) {
        String email = userData.getEmail();
        User found = userRepository.findByEmail(email);
        if (found != null) {
            throw new UserEmailDuplicationException(email);
        }
        User user = mapper.map(userData, User.class);

        return userRepository.save(user);
    }

    /**
     * id에 해당하는 사용자를 수정하고 반환합니다.
     *
     * @param userData 사용자 정보
     * @return 저장된 사용자
     */
    public User updateUser(Long id, UserData userData) {
        User user = findUser(id);

        user.changeWith(mapper.map(userData, User.class));

        return user;
    }

    /**
     * id에 해당하는 사용자를 삭제하고 반환합니다.
     *
     * @param id 사용자 id
     * @return 저장된 사용자
     */
    public User deleteUser(Long id) {
        User user = findUser(id);
        userRepository.delete(user);

        return user;
    }

    /**
     * id에 해당하는 사용자를 찾고 없으면 예외를 던집니다.
     *
     * @param id 사용자 id
     * @return 사용자
     * @throws UserNotFoundException (사용자를 찾을 수 없다는 예외)
     */
    public User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
