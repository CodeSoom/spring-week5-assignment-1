package com.codesoom.assignment.core.domain;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findById(Long id);

    void delete(Member member);

    Member save(Member member);
}
