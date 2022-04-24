package com.codesoom.assignment.domain.validator;

import java.util.Arrays;
import java.util.List;

public class ValidatorExecutor {
    private final List<Validator> validators;

    public ValidatorExecutor(Validator... validators) {
        this.validators = Arrays.asList(validators);
    }

    public boolean executePolicyStrategy() {
        for (Validator policyStrategy : validators) {
            policyStrategy.isSatisfiedBy();
        }
        return true;
    }
}
