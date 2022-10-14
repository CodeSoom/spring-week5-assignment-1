package com.codesoom.assignment.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class UserDeleteReport {
    private final Set<Long> deletedSuccessIds;
    private final Set<Long> deletedFailIds;

    public UserDeleteReport(Set<Long> deletedSuccessIds, Set<Long> deletedFailIds) {
        this.deletedSuccessIds = deletedSuccessIds;
        this.deletedFailIds = deletedFailIds;
    }
}
