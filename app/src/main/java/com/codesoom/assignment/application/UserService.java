package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.exception.UserNotFoundException;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class UserService {

    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserService(
            Mapper dozerMapper,
            UserRepository userRepository
    ){
        this.mapper = dozerMapper;
        this.userRepository = userRepository;
    }


    /**
     * 사용자 정보를 받아 생성하고 리턴합니다.
     *
     * @param source 생성할 사용자 정보
     * @return 생성된 사용자 정보
     */
    public UserData createUser(UserData source) {

        User mappedSource = mapper.map(source, User.class);

        User createdUser = userRepository.save(mappedSource);

        return mapper.map(createdUser, UserData.class);
    }


    /**
     * id에 해당하는 사용자를 찾아 리턴한다.
     *
     * @param id 사용자의 식별자
     * @return 찾은 사용자
     * @throws UserNotFoundException 사용자를 찾지 못한 경우
     */
    public User getUser(Long id) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return foundUser;
    }

    /**
     * 식별자(id)에 해당하는 사용자를 찾아 수정하고 리턴합니다.
     *
     * @param id 사용자의 식별자
     * @param updateParam 업데이트 정보
     * @return 수정된 사용자 정보
     */
    public UserData updateUser(Long id, UserData updateParam) {
        User targetUser = this.getUser(id);

        targetUser.update(mapper.map(updateParam, User.class));

        return mapper.map(targetUser, UserData.class);
    }

    /**
     * 식별자(id)에 해당하는 사용자를 찾아 삭제하고 리턴합니다.
     *
     * @param id 사용자의 식별자
     * @return 삭제된 사용자 정보
     */
    public UserData deleteUser(Long id) {
        User targetUser = this.getUser(id);

        userRepository.delete(targetUser);

        return mapper.map(targetUser, UserData.class);
    }
}
