package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 사용자 도메인의 서비스 레이어.
 */
@Service
@Transactional
public class UserService {
    private final Mapper mapper;
    private final UserRepository userRepository;

    /**
     * UserService 생성자.
     *
     * @param userRepository 사용자 도메인의 퍼시스턴스 레이어.
     * @param mapper         객체 매퍼.
     */
    public UserService(UserRepository userRepository,
                       Mapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    /**
     * 사용자를 조회한다.
     *
     * @param id 식별자.
     * @return 사용자.
     * @throws UserNotFoundException
     */
    public User get(Long id) {
        return userRepository.findById(id)
                             .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * 사용자를 생성한다.
     *
     * @param userData 사용자 데이터.
     * @return 사용자.
     */
    public User create(UserData userData) {
        User user = mapper.map(userData, User.class);
        return userRepository.save(user);
    }

    /**
     * 사용자를 수정한다.
     *
     * @param id       식별자.
     * @param userData 사용자 데이터.
     * @return 수정된 사용자.
     */
    public User patch(Long id, UserData userData) {
        User user = get(id);

        user.changeWith(mapper.map(userData, User.class));

        return user;
    }

    /**
     * 사용자를 제거한다.
     *
     * @param id 식별자.
     */
    public void delete(Long id) {
        User user = get(id);
        userRepository.delete(user);
    }
}
