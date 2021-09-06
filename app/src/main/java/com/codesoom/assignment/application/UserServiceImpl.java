package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * 사용자 생성, 수정, 삭제 기능을 담당하는 클래스
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 사용자를 생성하여 데이터베이스에 저장하는 메소드
     * @param source 사용자 객체에 매핑하기 위한 객체
     * @return 사용자 객체
     */
    @Override
    public User createUser(UserData source) {

        User user = User.builder()
                .name(source.getName())
                .email(source.getEmail())
                .password(source.getPassword())
                .build();

        return userRepository.save(user);

    }

    /**
     * 사용자 정보를 수정하는 메소드
     * @param id 사용자 id
     * @param source 수정할 정보가 들어있는 객체
     * @return 수정한 사용자 객체 반환
     */
    @Override
    public User updateUser(Long id, UserData source) {

        User findUser = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        findUser.userUpdate(source.getName(), source.getEmail(), source.getPassword());

        return findUser;

    }

    /**
     * 등록된 사용자들 삭제하는 메소드
     * @param id 삭제할 사용자 id
     */
    @Override
    public void deleteUser(Long id) {

        userRepository.deleteById(id);

    }

}
