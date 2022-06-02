package com.codesoom.assignment.domain;

import com.codesoom.assignment.domain.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * '사용자' 저장소 인터페이스
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * 모든 사용자를 반환한다
     * <p>
     *
     * @return 사용자를 내부 요소로 하는 List 콜렉션
     * </p>
     */
    List<User> findAll();

    /**
     * id에 해당하는 사용자를 반환한다
     * <p>
     *
     * @param id 사용자 id
     * @return Optional<User> 사용자
     * </p>
     */
    Optional<User> findById(Long id);

    /**
     * 사용자를 저장한다
     * <p>
     *
     * @param user 사용자
     * @return 사용자
     * </p>
     */
    User save(User user);

    /**
     * 사용자를 삭제한다
     * <p>
     *
     * @param user 사용자 엔티티
     * </p>
     */
    void delete(User user);

    /**
     * id에 해당하는 사용자 존재여부를 반환한다
     * <p>
     *
     * @param id 사용자 id
     * @return 사용자 존재여부
     * </p>
     */
    boolean existsById(Long id);
}
