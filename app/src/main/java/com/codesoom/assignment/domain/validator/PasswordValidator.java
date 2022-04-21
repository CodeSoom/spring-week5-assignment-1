package com.codesoom.assignment.domain.validator;

import org.springframework.util.Assert;


public class PasswordValidator implements Validator {
    private final String password;

    public PasswordValidator(String password) {
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
