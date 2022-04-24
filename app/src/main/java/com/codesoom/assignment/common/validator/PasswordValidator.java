package com.codesoom.assignment.common.validator;

import com.codesoom.assignment.common.Password;
import com.codesoom.assignment.domain.validator.ValidatorExecutor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        ValidatorExecutor validatorExecutor =new ValidatorExecutor(
                new com.codesoom.assignment.domain.validator.PasswordValidator(value)
        );
        boolean isValid = validatorExecutor.executePolicyStrategy();
        return isValid;
    }
}
