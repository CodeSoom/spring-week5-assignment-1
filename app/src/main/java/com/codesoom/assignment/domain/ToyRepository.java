package com.codesoom.assignment.domain;

import com.codesoom.assignment.domain.entities.Toy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * '장난감' 저장소 인터페이스
 */
@Repository
public interface ToyRepository extends CrudRepository<Toy, Long> {
    /**
     * 모든 장난감을 반환한다
     * <p>
     *
     * @return 장난감을 내부 요소로 하는 List 콜렉션
     * </p>
     */
    List<Toy> findAll();

    /**
     * id에 해당하는 장난감을 반환한다
     * <p>
     *
     * @param id 장난감의 id
     * @return Optional<Product> 장난감
     * </p>
     */
    Optional<Toy> findById(Long id);

    /**
     * 장난감을 저장한다
     * <p>
     *
     * @param toy 장난감
     * @return 장난감
     * </p>
     */
    Toy save(Toy toy);

    /**
     * 장난감을 삭제한다
     * <p>
     *
     * @param toy 장난감 엔티티
     *            </p>
     */
    void delete(Toy toy);

    /**
     * id에 해당하는 장난감을 삭제한다
     * <p>
     *
     * @param id 장난감 id
     * </p>
     */
    void deleteById(Long id);

    /**
     * id에 해당하는 장난감의 존재여부를 반환한다
     * <p>
     *
     * @param id 장난감의 id
     * @return 장난감의 존재여부
     * </p>
     */
    boolean existsById(Long id);
}
