package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 관련 비즈니스 로직을 처리합니다.
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
     * 사용자를 새로 등록하고 리턴합니다.
     * @param userData 사용자 정보
     * @return 등록한 사용자
     */
    public User signInUser(UserData userData) {
        User user = mapper.map(userData, User.class);
        return userRepository.save(user);
    }

    /**
     * 사용자를 찾아 업데이트하고 리턴합니다.
     * @param id 찾을 사용자 id
     * @param userData 업데이트 할 사용자 정보
     * @return 업데이트 한 사용자
     */
    public User updateUser(Long id, UserData userData) {
        User user = findUser(id);
        modifyUser(userData, user);
        return userRepository.save(user);
    }

    /**
     * 사용자를 찾아 삭제합니다.
     * @param id 삭제 할 사용자 id
     */
    public void deleteUser(Long id) {
        userRepository.delete(findUser(id));
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                             .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void modifyUser(UserData userDataForModify, User destinationUser) {
        mapper.map(userDataForModify, destinationUser);
    }
}
