package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserCreateRequest;
import com.codesoom.assignment.dto.UserUpdateRequest;
import com.codesoom.assignment.exception.UserNotFoundException;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserServiceImpl(Mapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public User create(UserCreateRequest source) {
        User user = mapper.map(source, User.class);
        return userRepository.save(user);
    }

    /**
     * 회원을 수정해 반환합니다.
     *
     * @param id 수정할 회원 아이디
     * @param source 수정할 회원
     * @throws UserNotFoundException 수정할 회원을 찾지 못한 경우
     * @return 수정된 회원
     */
    @Override
    public User update(Long id, UserUpdateRequest source) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id + "에 해당하는 회원을 찾지 못해 수정할 수 없습니다."));

        user.update(source.getName(),
                source.getEmail(),
                source.getPassword());

        return user;
    }

    /**
     * 회원을 삭제합니다.
     *
     * @param id 삭제할 회원 아이디
     * @throws UserNotFoundException 삭제할 회원을 찾지 못한 경우
     * @return 삭제된 회원
     */
    @Override
    public User delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id + "에 해당하는 회원을 찾지 못해 삭제할 수 없습니다."));

        userRepository.delete(user);

        return user;
    }
}
