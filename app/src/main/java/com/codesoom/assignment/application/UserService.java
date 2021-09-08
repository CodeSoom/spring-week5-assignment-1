package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserPostDto;
import com.codesoom.assignment.dto.UserUpdateDto;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = DozerBeanMapperBuilder.buildDefault();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createUser(UserPostDto userPostDto) {
        User user = mapper.map(userPostDto, User.class);

        userRepository.save(user);

        return user;
    }

    public void deleteUser(Long id) {
        findUserById(id);

        this.userRepository.deleteById(id);
    }

    public User updateUser(Long id, UserUpdateDto userDto) {
        User user = findUserById(id);

        user.update(
                userDto.getEmail(),
                userDto.getName(),
                userDto.getPassword()
        );

        return user;
    }
}
