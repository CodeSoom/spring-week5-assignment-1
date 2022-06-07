package com.codesoom.assignment.application.interfaces;

/**
 * Product 타입 삭제에 대한 비지니스 로직을 처리한다
 * <p>
 * All Known Implementing Classes:
 * ToyCrudService
 * </p>
 */
public interface ToyDeleteService {
    /**
     * 매개변수로 전달 받은 id에 해당하는 Toy 삭제한다
     * <p>
     * @param id Toy 엔티티의 Id에 해당
     * </p>
     */
    void deleteBy(Long id);
}
