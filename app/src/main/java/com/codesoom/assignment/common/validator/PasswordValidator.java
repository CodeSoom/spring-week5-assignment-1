package com.codesoom.assignment.common.validator;

import com.codesoom.assignment.common.Password;
import com.codesoom.assignment.domain.policy.PasswordPolicy;
import com.codesoom.assignment.domain.policy.Policy;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Policy policy =new Policy(
                new PasswordPolicy(value)
        );
        boolean isValid = policy.executePolicyStrategy();
        return isValid;
    }
}
