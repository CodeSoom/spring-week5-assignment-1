package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.MemberRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

}
