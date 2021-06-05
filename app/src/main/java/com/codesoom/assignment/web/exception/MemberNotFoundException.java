package com.codesoom.assignment.web.exception;

/**
 * 회원 ID가 유효하지 않거나 찾을 수 없음을 나타냅니다.
 */
public class MemberNotFoundException extends RuntimeException {
    /**
     * {@code MemberNotFoundException} 생성자. 상세 메시지를 함께 출력합니다.
     *
     * @param id - 찾을 수 없는 회원 ID
     */
    public MemberNotFoundException(Long id) {
        super("Member not found: " + id);
    }
}
