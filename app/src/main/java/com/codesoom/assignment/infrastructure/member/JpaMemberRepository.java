package com.codesoom.assignment.infrastructure.member;

import com.codesoom.assignment.domain.member.Member;
import com.codesoom.assignment.domain.member.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMemberRepository extends MemberRepository, JpaRepository<Member, Long> {
}
