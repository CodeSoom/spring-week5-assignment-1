package com.codesoom.assignment.domain.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    /**
     * 회원 전체 리스트을 조회하고 리턴한다.
     * @return 회원 전체 리스트
     */
    List<Member> findAll();

    /**
     * 회원 ID로 회원을 조회하고 회원정보를 리턴한다.
     * @param id 회원 ID
     * @return 회원정보
     */
    Optional<Member> findById(Long id);

    /**
     * 새로운 회원을 등록하고 리턴한다.
     * @param product 새로운 회원 정보
     * @return 등록된 회원 정보
     */
    Member save(Member product);

    /**
     * 회원정보를 삭제한다.
     * @param product 삭제할 회원정보
     */
    void delete(Member product);
}
