package com.codesoom.assignment.user.application;

import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.domain.UserRepository;
import com.codesoom.assignment.user.dto.UserData;
import com.codesoom.assignment.user.dto.UserSaveRequestDto;
import com.codesoom.assignment.user.dto.UserUpdateRequestDto;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 사용자 정보를 다룬다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    /**
     * 등록된 모든 사용자 정보를 가져온다.
     */
    public List<UserData> getUsersInformation() {
        return userRepository.findAll()
                .stream()
                .map(UserData::of)
                .collect(Collectors.toList());
    }

    /**
     * 등록된 사용자 id를 가진 사용자 정보를 리턴한다.
     * @param userId 등록된 사용자 id
     * @return 등록된 사용자
     */
    public UserData getUserInformation(Long userId) throws UserNotFoundException {
        final User user = findUser(userId);

        return UserData.of(user);
    }

    /**
     * 등록된 사용자 정보를 갱신하고, 생신된 정보를 리턴합니다.
     * @param userId 등록된 사용자 id
     * @param requestDto 갱신할 사용자 정보
     * @return 갱신된 사용자 정보
     */
    @Transactional
    public UserData updateUser(long userId,
                               UserUpdateRequestDto requestDto) throws UserNotFoundException {
        User user = findUser(userId);

        user.changeWith(mapper.map(requestDto, User.class));

        return UserData.of(user);
    }

    /**
     * 사용자를 등록하고, 등록된 정보를 리턴한다.
     * @param requestDto 등록할 사용자 정보
     * @return 등록된 사용자 정보
     */
    @Transactional
    public UserData createUser(UserSaveRequestDto requestDto) {
        User user = mapper.map(requestDto, User.class);
        User saved = userRepository.save(user);

        return UserData.of(saved);
    }

    /**
     * 등록된 사용자를 삭제한다.
     * @param userId 등록된 사용자 id
     * @return 삭제된 사용자 id
     */
    @Transactional
    public Long deleteUser(Long userId) throws UserNotFoundException {
        User user = findUser(userId);
        userRepository.delete(user);
        return user.getId();
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
