package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 사용자 관련 비즈니스 로직을 담당합니다.
 */

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final Mapper mapper;
    private final UserRepository userRepository;

    /**
     * 주어진 id에 해당하는 사용자를 반환합니다.
     * @param id
     * @return 해당 id를 갖는 사용자
     */

    public User getUser(Long id) {
        return findUser(id);
    }

    /**
     * 새로운 사용자를 등록합니다.
     *
     * @param userData
     * @return 등록된 사용자
     */

    public User createUser(UserData userData) {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        User user = mapper.map(userData, User.class);

        return userRepository.save(user);
    }

    /**
     * 주어진 id에 해당하는 사용자의 정보를 수정합니다.
     *
     * @param id
     * @param userData
     * @return 수정된 사용자
     */

    public User updateUser(Long id, UserData userData) {
        User user = findUser(id);

        user.updateWith(mapper.map(userData, User.class));

        return user;
    }

    /**
     * 주어진 id에 해당하는 사용자를 삭제합니다.
     *
     * @param id
     * @return 삭제된 사용자
     */

    public User deleteUser(Long id) {
        User user = findUser(id);

        userRepository.delete(user);

        return user;
    }

    /**
     * 주어진 id에 해당하는 사용자를 반환합니다.
     *
     * @param id
     * @return 해당 id를 갖는 사용자
     */

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
