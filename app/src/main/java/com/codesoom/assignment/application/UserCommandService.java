package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserCreateRequest;
import com.codesoom.assignment.dto.UserDeleteReport;
import com.codesoom.assignment.dto.UserResponse;
import com.codesoom.assignment.dto.UserUpdateRequest;
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

    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        User user = mapper.map(userCreateRequest, User.class);
        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .build();
    }

    public UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id + "에 해당하는 user를 찾지 못했으므로 업데이트에 실패했습니다."));

        User user = mapper.map(userUpdateRequest, User.class);

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

    public UserDeleteReport deleteUsers(Set<Long> ids) {
        Iterable<User> findUsers = userRepository.findAllById(ids);
        userRepository.deleteAll(findUsers);

        Set<Long> findUserIds = getFindUserIds(findUsers);
        Set<Long> notFindUserIds = getNotFindUserIds(ids, findUserIds);

        return new UserDeleteReport(findUserIds, notFindUserIds);
    }

    private Set<Long> getFindUserIds(Iterable<User> findUsers) {
        Set<Long> userIdSet = new HashSet<>();
        findUsers.forEach(user -> userIdSet.add(user.getId()));
        return userIdSet;
    }

    private Set<Long> getNotFindUserIds(Set<Long> ids, Set<Long> findUserIds) {
        Set<Long> notFoundIds = new HashSet<>();
        ids.forEach(id -> {
            if (!findUserIds.contains(id)) {
                notFoundIds.add(id);
            }
        });
        return notFoundIds;
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
