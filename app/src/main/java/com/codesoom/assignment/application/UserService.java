package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 사용자 정보 관리를 담당합니다.
 */
@Service
@Transactional
public class UserService {
    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository,
                       Mapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    /**
     * 식별자로 사용자를 조회한 후 리턴합니다.
     *
     * @param id 식별자
     * @return 사용자
     * @throws UserNotFoundException 사용자를 찾을 수 없는 경우
     */
    public User get(Long id) {
        return userRepository.findById(id)
                             .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * 사용자를 생성한 후 생성된 사용자를 리턴합니다.
     *
     * @param userData 사용자 데이터
     * @return 사용자
     */
    public User create(UserData userData) {
        User user = mapper.map(userData, User.class);
        return userRepository.save(user);
    }

    /**
     * 사용자를 수정한 후 수정된 사용자를 리턴합니다.
     *
     * @param id       식별자
     * @param userData 사용자 데이터
     * @return 수정된 사용자
     */
    public User patch(Long id, UserData userData) {
        User user = get(id);

        user.changeWith(mapper.map(userData, User.class));

        return user;
    }

    /**
     * 사용자를 제거합니다.
     *
     * @param id 식별자
     */
    public void delete(Long id) {
        User user = get(id);
        userRepository.delete(user);
    }
}
