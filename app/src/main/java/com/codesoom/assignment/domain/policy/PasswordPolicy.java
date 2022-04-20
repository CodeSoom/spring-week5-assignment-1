package com.codesoom.assignment.domain.policy;

import org.springframework.util.Assert;


public class PasswordPolicy implements PolicyStrategy {
    private final String password;

    public PasswordPolicy(String password) {
        this.password = password;
    }

    private void checkPasswordEmpty(String email) {
        Assert.hasText(email, "비밀번호는 공백 허용이 되지 않습니다");
    }

    @Override
    public boolean isSatisfiedBy() {
        checkPasswordEmpty(password);
        return true;
    }
}
