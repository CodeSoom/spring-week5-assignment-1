package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserRequest;
import com.codesoom.assignment.dto.UserResponse;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserCommandService {
    private final UserRepository userRepository;
    private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public UserCommandService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest userRequest) {
        User user = mapper.map(userRequest, User.class);
        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .build();
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id + "에 해당하는 user를 찾지 못했으므로 업데이트에 실패했습니다."));

        User user = mapper.map(userRequest, User.class);

        findUser.update(user.getEmail(), user.getName(), user.getPassword());
        User updatedUser = userRepository.save(findUser);

        return UserResponse.builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .email(updatedUser.getEmail())
                .password(updatedUser.getPassword())
                .build();
    }

    public Long deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id + "에 해당하는 user를 찾지 못했으므로 삭제에 실패했습니다."));

        userRepository.deleteById(id);
        return id;
    }

    public Set<Long> deleteUsers(Set<Long> ids) {
        Iterable<User> users = userRepository.findAllById(ids);

        Set<Long> userIdSet = new HashSet<>();
        users.forEach(user -> userIdSet.add(user.getId()));

        ids.forEach(id -> {
            if (!userIdSet.contains(id)) {
                throw new UserNotFoundException(id + "에 해당하는 user를 찾지 못했으므로 요청한 모든 user삭제에 실패했습니다.");
            }
        });

        userRepository.deleteAll(users);
        return userIdSet;
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
