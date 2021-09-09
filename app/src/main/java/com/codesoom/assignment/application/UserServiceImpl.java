package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.dto.UserEmailDuplicateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(UserData source) throws Exception {

        if( emailCheck(source.getEmail()) ) {
            throw new UserEmailDuplicateException();
        }

        User user = User.builder()
                .name(source.getName())
                .email(source.getEmail())
                .password(source.getPassword())
                .build();

        return userRepository.save(user);

    }

    @Override
    public User updateUser(Long id, UserData source) {

        User findUser = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        findUser.userUpdate(source.getName(), source.getEmail(), source.getPassword());

        return findUser;

    }

    @Override
    public void deleteUser(Long id) {

        userRepository.deleteById(id);

    }

    @Override
    public boolean emailCheck(String mail) throws Exception {

        List<User> users = userRepository.findAll();

        for (User user : users) {

            if(user.getEmail().equals(mail)) {
                return true;
            }

        }

        return false;

    }

}
