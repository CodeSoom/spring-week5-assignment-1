package com.codesoom.assignment.application;

import com.codesoom.assignment.DuplicateUserException;
import com.codesoom.assignment.InvalidEmailException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 * 사용자 비즈니스 로직을 담당한다.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private Predicate<String> isEmailForm = email -> email.contains("@");

    /**
     * 주어진 식별자에 해당하는 사용자를 리턴한다.
     *
     * @param id - 조회하려는 사용자의 식별자
     * @return 주어진 {@code id}에 해당하는 사용자
     * @throws UserNotFoundException {@Code in}에 해당하는 사용자가 저장되어 있지 않은 경우
     */
    public User getUser(Long id) throws UserNotFoundException {
        return findUser(id);
    }

    /**
     * 주어진 사용자를 저장하고 해당사용자를 리턴한다.
     *
     * @param user - 저장하려는 사용자
     * @return 저장된 사용자
     * @throws InvalidEmailException 이메일 주소에 @이 없을 경우
     * @throws DuplicateUserException 이미 저장된 이메일이 주어졌을 경우
     */
    public User createUser(User user) throws InvalidEmailException, DuplicateUserException {
        if (!isEmailForm.test(user.getEmail())) {
            throw new InvalidEmailException();
        }

        if (userRepository.isExistsEmail(user.getEmail())) {
            throw new DuplicateUserException("이미 저장된 이메일이 주어져 사용자를 저장할 수 없습니다.");
        }

        return userRepository.save(user);
    }

    /**
     * 주어진 식별자에 해당하는 사용자를 찾아 수정하고, 수정된 사용자를 리턴한다.
     *
     * @param id - 수정하려는 사용자의 식별자
     * @param user - 수정할 사용자
     * @return 수정한 사용자
     * @throws UserNotFoundException - 주어진 식별자에 해당하는 사용자를 찾지 못했을 경우
     */
    public User updateUser(Long id, User user) {
        User foundUser = getUser(id);
        return foundUser.changeWith(user);
    }

    /**
     * 주어진 식별자에 해당하는 사용자를 찾아 삭제한다.
     *
     * @param id - 삭제하려는 사용자의 식별자
     * @throws UserNotFoundException - 주어진 식별자에 해당하는 사용자를 찾지 못했을 경우
     */
    public void deleteUser(Long id) {
        User user = getUser(id);
        userRepository.delete(user);
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
