// 1. createUser -> 회원 생성
// 2. updateUser -> 회원 수정
// 3. deleteUser -> 회원 삭제

package com.codesoom.assignment.application;

import com.codesoom.assignment.errors.UserEmailAlreadyExistedException;
import com.codesoom.assignment.errors.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserModificationData;
import com.codesoom.assignment.dto.UserRegistrationData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 회원을 생성, 수정, 삭제하는 service 입니다.
 * */
@Service
@Transactional
public class UserService {
    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserService(Mapper dozerMapper, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = dozerMapper;
    }

    /**
     * userData를 받아 새로운 user를 생성하여 리턴합니다.
     *
     * @param registrationData 회원가입 정보
     * @return 새로 생성된 회원
     * */
    public User create(UserRegistrationData registrationData) {
        String email = registrationData.getEmail();
        if(userRepository.existsByEmail(email)) {
            throw new UserEmailAlreadyExistedException(email);
        }

        User user = mapper.map(registrationData, User.class);
        return userRepository.save(user);
    }

    /**
     * 기존 회원 목록 중 id가 일치하는 회원을 찾아 userData 회원 정보로 수정하여 변경된 회원을 리턴합니다.
     *
     * @param id               회원 id
     * @param modificationData 변경하려는 정보
     * @return 변경된 회원
     */
    public User update(Long id, UserModificationData modificationData) {
        User user = findUser(id);

        User source = mapper.map(modificationData, User.class);

        user.changeWith(source);

        return user;
    }

    /**
     * 기존 회원 목록 중 id가 일치하는 회원을 찾아 회원을 삭제하고 삭제된 회원을 리턴합니다.
     *
     * @param targetId 회원 id
     * @return 삭제된 회원
     * */
    public User deleteUserById(Long targetId) {
        User user = findUser(targetId);

        userRepository.delete(user);

        return user;
    }

    /**
     * id를 받아서 일치하는 회원을 찾으면 회원을 리턴하고, 못찾았으면 예외를 던집니다.
     *
     * @param id 회원 id
     * @throws UserNotFoundException id가 일치하는 회원이 없는 경우
     * */
    public User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
