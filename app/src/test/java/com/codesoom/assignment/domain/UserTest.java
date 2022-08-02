package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    /**
     * User 생성 Test
     * 이름, 이메일 ,비밀번호 (필수입력조건)
     *
     */
    @Test
    void creation(){
        User user = new User("메이커회사1", "maker1@gmail.com", "maker11");

        assertThat(user.getName()).isEqualTo("메이커회사1");
        assertThat(user.getEmail()).isEqualTo("maker1@gmail.com");
        assertThat(user.getPassword()).isEqualTo("maker1");
    }
    
}