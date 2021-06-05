package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserDto;
import com.codesoom.assignment.exception.NotFoundUserException;
import com.github.dozermapper.core.Mapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 * 유저 데이터에 대한 요청을 처리합니다.
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private Mapper mapper;

    /**
     * 생성자.
     *
     * @param mapper Object 맵퍼
     * @param userRepository 유저 저장소
     */
    public UserService(Mapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    /**
     * 유저를 생성하고, 생성한 유저의 정보를 반환합니다.
     *
     * @param source 생성할 유저 정보
     * @return 생성된 유저 정보
     */
    public User createUser(UserDto source) {
        User user = mapper.map(source, User.class);
        return userRepository.save(user);
    }

    /**
     * 유저를 갱신하고, 갱신한 유저의 정보를 반환합니다.
     *
     * @param id 갱신할 유저 id
     * @param source 갱신할 내용
     * @return 갱신한 유저 정보
     * @throws NotFoundUserException 유저를 찾지 못한 경우
     */
    public User updateUser(Long id, UserDto source) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundUserException(id));
        user.change(mapper.map(source, User.class));
        return userRepository.save(user);
    }

    /**
     * 유저를 삭제합니다.
     *
     * @param id 삭제할 유저 id
     * @throws NotFoundUserException 유저를 찾지 못한 경우
     */
    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new NotFoundUserException(id);
        }
    }
}
