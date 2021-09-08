package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserCreateDTO;
import com.codesoom.assignment.dto.UserUpdateDTO;
import com.codesoom.assignment.exception.UserNotFoundException;
import com.github.dozermapper.core.Mapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserService(Mapper dozerMapper, UserRepository userRepository) {
        this.mapper = dozerMapper;
        this.userRepository = userRepository;
    }

    public User create(UserCreateDTO userCreateDTO) {
        User user = this.mapper.map(userCreateDTO, User.class);
        return userRepository.save(user);
    }

    public User update(Long id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        User updateUser = this.mapper.map(userUpdateDTO, User.class);
        user.update(updateUser);
        return userRepository.save(user);
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new UserNotFoundException(id);
        }
    }
}
