package com.codesoom.assignment.controllers.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements
        ConstraintValidator<Password, String> {

    @Override
    public void initialize(Password password) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext cxt) {
        return password != null && !password.isBlank() &&(password.length() > 8) && (password.length() < 24);
    }

}
