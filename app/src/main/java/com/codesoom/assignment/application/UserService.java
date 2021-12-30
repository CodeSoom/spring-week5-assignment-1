package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.UserNotFoundException;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserService(Mapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }


    /**
     * user을 생성하고 리턴한다
     *
     * @param userData 저장될 user
     * @return 생성된 user
     */

    public User createUser(UserData userData) {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        User user = mapper.map(userData, User.class);

        return userRepository.save(user);
    }

    /**
     *  id에 해당하는 user를 수정하고 리턴한다.
     *
     * @param id user의 id
     * @param userData 수정할 userData
     * @return 수정된 user
     */

    public User updateUser(Long id, UserData userData) {
        User user = findUser(id);

        user.changeWith(mapper.map(userData, User.class));

        return user;
    }

    /**
     * id에 해당하는 user을 삭제하고 리턴한다.
     *
     * @param id user의 id
     */

    public User deleteUser(Long id) {
        User user = findUser(id);

        userRepository.delete(user);

        return user;
    }

    /**
     * id에 해당하는 user을 찾고 없다면 예외를 던진다.
     *
     * @param id user의 id
     * @return user의 정보
     * @throws UserNotFoundException 예외를 던진다.
     */

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
