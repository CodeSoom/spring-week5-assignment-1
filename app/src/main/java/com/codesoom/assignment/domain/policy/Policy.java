package com.codesoom.assignment.domain.policy;

import java.util.Arrays;
import java.util.List;

public class Policy {
    private final List<PolicyStrategy> policyStrategies;

    public Policy(PolicyStrategy... policyStrategy) {
        this.policyStrategies = Arrays.asList(policyStrategy);
    }

    public boolean executePolicyStrategy() {
        for (PolicyStrategy policyStrategy : policyStrategies) {
            policyStrategy.isSatisfiedBy();
        }
        return true;
    }
}
