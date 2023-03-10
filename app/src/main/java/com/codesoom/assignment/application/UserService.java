package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserCreateDto;
import com.codesoom.assignment.dto.UserRequest;
import com.codesoom.assignment.exception.NotFoundIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void create(UserCreateDto userCreateDto) {
        userRepository.save(User.builder()
                .name(userCreateDto.getName())
                .email(userCreateDto.getEmail())
                .password(userCreateDto.getPassword())
                .build());
    }

    public void update(Long id, UserRequest userRequest) {
        User user = findById(id);
        user.change(userRequest.getName(),user.getEmail(),user.getPassword());
    }


    public void delete(Long id) {
        User user = findById(id);
        this.userRepository.delete(user);
    }


    private User findById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(()->new NotFoundIdException(id));
    }


}
