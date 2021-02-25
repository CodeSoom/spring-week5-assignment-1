package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapper;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

/**
 * 사용자의 생성, 수정, 삭제를 수행한다.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 주어진 식별자 해당하는 사용자를 리턴한다.
     *
     * @param id - 조회하려는 사용자의 식별자
     * @return 주어진 {@code id}에 해당하는 식별자
     * @throws UserNotFoundException 만약
     *         {@code id}가 저장되어 있지 않은 경우
     */
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * 주어진 사용자 저장하고 해당 사용자를 리턴한다.
     *
     * @param userData - 새로 저장하고자 하는 사용자
     * @return 저장 된 사용자
     */
    public User createUser(UserData userData) {
        User user = mapper.map(userData, User.class);
        return userRepository.save(user);
    }

    /**
     * 주어진 식별자에 해당하는 서용자를 수정하고 해당 사용자를 리턴한다.
     *
     * @param id - 수정하고자 하는 사용자의 식별자
     * @param userData - 수정 할 새로운 사용자
     * @return 수정 된 사용자
     * @throws UserNotFoundException 만약
     *         {@code id}가 저장되어 있지 않은 경우
     */
    public User updateUser(Long id, UserData userData) {
        User user = getUser(id);

        mapper.map(userData, user);

        return user;
    }

    /**
     * 주어진 식별자에 해당하는 사용자를 삭제하고 해당 사용자를 리턴한다.
     *
     * @param id - 삭제하고자 하는 사용자의 식별자
     * @return 삭제 된 사용자
     * @throws UserNotFoundException 만약 주어진
     *         {@code id}가 저장되어 있지 않은 경우
     */
    public User deleteUser(Long id) {
        User user = getUser(id);

        userRepository.delete(user);

        return user;
    }
}
