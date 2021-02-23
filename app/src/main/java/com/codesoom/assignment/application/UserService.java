package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserUpdateRequest;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final Mapper mapper;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return findUser(id);
    }

    public User updateUser(Long id, UserUpdateRequest updateRequest) {
        User user = findUser(id);

        return user.changeWith(mapper.map(updateRequest, User.class));
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("존재하지 않는 회원 id가 주어졌으므로 회원을 찾을 수 없습니다. 문제의 id = " + id));
    }

    public void deleteUser(Long id) {
        
    }
}
