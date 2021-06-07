package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserDto;
import com.codesoom.assignment.exception.UserNotFoundException;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description 유 관련 CRUD
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

     /**
     * 등록된 모든 사용자 정보를 가져온다.
     *
     * @return 사용자 전부
     */
    public List<User> findAll() {
        return userRepository.findAll()
                .stream()
                .map(User::of)
                .collect(Collectors.toList());
    }

    /**
     * 식별자로 사용자를 조회한 후 리턴합니다.
     *
     * @param id 식별자
     * @return 사용자
     * @throws UserNotFoundException 사용자를 찾을 수 없는 경우
     */
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * 사용자를 생성한 후 생성된 사용자를 리턴합니다.
     *
     * @param dto 사용자 데이터
     * @return 사용자
     */
    public User createUser(UserDto dto) {
        User user = mapper.map(dto, User.class);
        return userRepository.save(user);
    }


    /**
     * 사용자를 수정한 후 수정된 사용자를 리턴합니다.
     *
     * @param id       식별자
     * @param dto 사용자 데이터
     * @return 수정된 사용자
     */
    public User updateUser(Long id, UserDto dto) {
        User user = findById(id);

        user.update(mapper.map(dto, User.class));

        return user;
    }

    /**
     * 사용자를 제거합니다.
     *
     * @param id 식별자
     */
    public void deleteUser(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }
}
