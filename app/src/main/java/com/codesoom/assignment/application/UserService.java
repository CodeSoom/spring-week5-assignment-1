package com.codesoom.assignment.application;

import com.codesoom.assignment.DuplicateUserException;
import com.codesoom.assignment.InvalidEmailException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 * 사용자 비즈니스 로직을 담당한다.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    private Predicate<UserData> isEmailForm = user -> user.getEmail().contains("@");

    /**
     * 주어진 식별자에 해당하는 사용자를 리턴한다.
     *
     * @param id - 조회하려는 사용자의 식별자
     * @return 주어진 {@code id}에 해당하는 사용자
     * @throws UserNotFoundException {@Code in}에 해당하는 사용자가 저장되어 있지 않은 경우
     */
    public User getUser(Long id) throws UserNotFoundException {
        return findById(id);
    }

    /**
     * 주어진 사용자를 저장하고 해당사용자를 리턴한다.
     *
     * @param userData
     * @return 저장된 사용자
     * @throws InvalidEmailException 이메일 주소에 @이 없을 경우
     * @throws DuplicateUserException 이미 저장된 이메일이 주어졌을 경우
     */
    public User createUser(UserData userData) {
        if (!isEmailForm.test(userData)) {
            throw new InvalidEmailException();
        }

        if (userRepository.isExistsEmail(userData.getEmail())) {
            throw new DuplicateUserException("이미 저장된 이메일이 주어져 사용자를 저장할 수 없습니다.");
        }

        User user = mapper.map(userData, User.class);

        return userRepository.save(user);
    }

    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
